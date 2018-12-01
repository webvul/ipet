package com.sensetime.iva.ipet.util;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.DatePattern;
import com.sensetime.iva.ipet.common.ExceptionMsg;
import com.sensetime.iva.ipet.service.impl.ProjectServiceImpl;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author  gongchao
 */
public class ExcelImportUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);

    /**
     * @param filePath  文件名称
     * @return 是否是2003的excel，返回true是2003
     */
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     *
     * @param filePath  文件名称
     * @return 是否是2007的excel，返回true是2007
     */
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     * @param filePath
     * @return 验证EXCEL文件结果
     */
    public static boolean validateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            logger.error("上传文件后缀不是2003或2007");
            return false;
        }
        logger.info("文件后缀验证通过");
        return true;
    }
    /**
     * 读取单一的excel文件类型
     * @param  sheet：sheet
     * @param startReadLine 开始读取的行
     * @param  maxColumn 由于模板冗余，设置最对解析到多少列从0开始计算，全部解析则传 -1
     */
    public static List<String> readExcelOneTpyeSheet(Sheet sheet, int startReadLine,int maxColumn) throws  Exception{
        logger.info("开始解析excel的数据");
        try{ List<String> contextList=new ArrayList<>();
            for(int i=startReadLine; i<=sheet.getLastRowNum(); i++) {
                Row   row = sheet.getRow(i);
                //合并单元格中空字符串是否被List添加过
                boolean isAdd =false;
                for(Cell c : row) {
                    c.setCellType(Cell.CELL_TYPE_STRING);
                    if(maxColumn!=-1&&c.getColumnIndex()>maxColumn){
                        logger.debug("当前最多解析到单元格的下标是第{[}]列，从0开始计算的",maxColumn);
                        break;
                    }
                    boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
                    //判断是否具有合并单元格
                    if(isMerge) {
                        //添加‘-’区分不同行出现同一数值
                        String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex())+"__"+ String.valueOf(i);
                        //判断是否是空字符串
                        if(StringUtils.isEmpty(rs)){
                            //是否被List添加过
                            if(!isAdd){
                                contextList.add(rs);
                                isAdd=true;
                            }
                        }else{
                            //判断是否重复添加数据
                            if(!contains(contextList,rs)){
                                contextList.add(rs);
                            }
                        }
                    }else {
                        String rs = c.getRichStringCellValue().toString();
                        contextList.add(rs);
                    }
                }
            }
            logger.info("获取excel的数据结束");
            return dealSheet(contextList);
        }catch (Exception e){
            logger.error("解析上传文档失败:"+e.getMessage());
            throw  new Exception("解析上传文档失败");
        }
    }

    /**
     * 对于内容存在'-'的解析取前一部分
     * @param contextList 初始解析值
     * @return
     */
    public static List<String> dealSheet(List<String> contextList){
        List<String> list=new ArrayList<>();
       if(contextList!=null&&contextList.size()>0){
           for (String context: contextList) {
               if(context.indexOf("__")!=-1){
                   String[] split = context.split("__");
                   list.add(split[0]);
               }else{
                   list.add(context);
               }
           }
       }
        return list;
    }
    /**
     * 读取涉及多个表的excel文件类型
     * @param  sheet：sheet
     * @param  keyWords excel中合并表的表名
     */
    public static Map<String,List<String>> readExcelManyTypeSheet(Sheet sheet,List<String> keyWords,int maxColumn) throws Exception{
        //保存返回结果根据传入表名进行分组
        Map<String,List<String>> contextsMap =new HashMap<>(16);
        //循环keyWords并进行按照表名分组 从第一行开始遍历
        List<String> contextsList = readExcelOneTpyeSheet(sheet, 0,maxColumn);
        //遍历结果判断
        if(contextsList!=null&&contextsList.size()>0){
            //对传入关键字进行判断
            if(keyWords.size()==1){
                contextsMap.put(keyWords.get(0),contextsList);
                return contextsMap;
            }else if(keyWords.size()>1){
                //记录遍历到整张sheet的节点
                int k=0;
                //从第二个表的节点开始，之前的都是第一个表的数据
                for( int i=1; i<keyWords.size();i++){
                    String key = keyWords.get(i);
                    //记录每个截断表中的数据
                    List<String> tempList= new ArrayList<>();
                    for( int j=k;j<contextsList.size();j++){
                        String context = contextsList.get(j);
                        if(key.equals(contextsList.get(j))){
                            //最后的表
                            if (i==keyWords.size()-1){
                                contextsMap.put(keyWords.get(i-1),tempList);
                                //将最后的表放进map
                                getLastList(contextsMap,contextsList,j,keyWords.get(i));
                                return contextsMap;
                            }//处理中间的表
                            else{
                                contextsMap.put(keyWords.get(i-1),tempList);
                                break;
                            }
                        }
                        tempList.add(context);
                        k++;
                    }
                }
                return contextsMap;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     *
     * @param map   返回结果
     * @param contextsList  解析的数据集合
     * @param index   从哪一个下表开始
     * @param key  分组用到的excel表名
     * @return 返回结果
     */
    public static Map<String,List<String>> getLastList(Map<String,List<String>> map,List<String> contextsList,int index,String key){
       //最后截断表中的数据
        List<String> tempList= new ArrayList<>();
        for (int i=index;i<contextsList.size();i++){
            tempList.add(contextsList.get(i));
        }
         map.put(key,tempList);
        return map;
    }
    /**
     * 获取合并单元格的值
     * @param sheet  sheet
     * @param row  行下标
     * @param column  列下标
     * @return  单元格的值
     */

    public static String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }
        return null ;
    }

    /**
     * 获取单元格的值
     * @param cell 单元格
     * @return  单元格的值
     */

    public static String getCellValue(Cell cell) {
        if (cell == null){
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }


    /**
     * 判断合并了行
     * @param sheet sheet
     * @param row 行下标
     * @param column 列下标
     * @return 是否合并了行
     */

    @SuppressWarnings("unused")
    public static boolean isMergedRow(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row == firstRow && row == lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return 是否含有合并单元格
     */
    @SuppressWarnings("unused")
    public static  boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断sheet页中是否含有合并单元格
     * @param sheet sheet
     * @return 是否含有合并单元格
     */
    @SuppressWarnings("unused")
    public boolean hasMerged(Sheet sheet) {
        return sheet.getNumMergedRegions() > 0 ? true : false;
    }

    /**
     * 合并单元格
     *
     * @param sheet sheet
     * @param firstRow 开始行
     * @param lastRow 结束行
     * @param firstCol 开始列
     * @param lastCol 结束列
     */
    @SuppressWarnings("unused")
    public void mergeRegion(Sheet sheet, int firstRow, int lastRow,
                            int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol,
                lastCol));
    }

    /**
     *  判断某个字符串是否存在于数组中
     *  @param tempList 保存数据的集合
     *  @param source 查找的字符串
     *  @return 是否找到
     */
    public static boolean contains(List<String> tempList, String source) {
        // 利用list的包含方法,进行判断
        if (tempList.contains(source)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param file 上传文件
     * @return Workbook
     * @throws IOException
     */
    public static Workbook getWorkbook(MultipartFile file) throws IOException {
        //验证空文件
        if(file==null){
            logger.error("empty file","上传文件为空");
            return null;
        }
        String fileName=file.getOriginalFilename();
        //验证文件名是否合格
        logger.debug("验证文件名是否合格");
        if(!ExcelImportUtil.validateExcel(fileName)){
            return null;
        }
        //判断2007或2003
        Workbook workbook;
        logger.debug("判断2007或2003的excel");
        if(ExcelImportUtil.isExcel2007(fileName)){
            logger.debug("创建2007的excel");
            workbook = new XSSFWorkbook(file.getInputStream());
        }else{
            logger.debug("创建2003的excel");
            workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
        }
        logger.debug("workbook info {}",workbook);
        return workbook;
    }

    public static HSSFWorkbook createExportWorkbook(String fileName, HttpServletResponse response, HttpServletRequest request)  throws UnsupportedEncodingException{
        logger.info("开始设置导出excel内容的编码");
        String format = new SimpleDateFormat(DatePattern.yyyyMMdd).format(new Date());
        //导出文件名称
         fileName=fileName+format;
        // 清空输出流
        response.reset();
        final String userAgent = request.getHeader("USER-AGENT");
            String finalFileName = null;
            //IE浏览器
            if(userAgent.indexOf("MSIE") !=-1){
                finalFileName = URLEncoder.encode(fileName,"UTF8");
            }//google,火狐浏览器
            else if(userAgent.indexOf("Mozilla") !=-1 ){
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            }//其他浏览器
            else{
                finalFileName = URLEncoder.encode(fileName,"UTF8");
            }
        //下面三行是关键代码，处理乱码问题
        response.setContentType("application/msexcel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+finalFileName+".xls");
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        logger.info("设置导出excel内容的编码结束");
        return wb;
    }

    public static com.sensetime.iva.ipet.entity.File uploadFile(MultipartFile file, String pathPrefix) throws  Exception{
        logger.debug("读取文件流");
        InputStream input = file.getInputStream();
        //获取文件名称及后缀名
        String originalFilename = file.getOriginalFilename();
        //每天创建一个文件夹
        String yyyyMMdd = DateUtil.dateToString(new Date(), DatePattern.yyyyMMdd);
        //保存目录
        String loadPath=pathPrefix+yyyyMMdd;
        String fileName=System.currentTimeMillis()+originalFilename;
        logger.debug("设置保存的而文件路径");
        //保存之后的文件
        File filePath = new File(loadPath);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        File saveFile = new File(filePath,fileName);
        logger.debug("创建服务器上的文件:"+filePath+fileName);
        saveFile.createNewFile();
        OutputStream output = null;
        try {
                output = new FileOutputStream(saveFile);
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buf)) != -1) {
                    output.write(buf, 0, bytesRead);
                }
        } catch (IOException e){
            logger.error("upload to service error [{}] detail error [{}]","上传文件到服务器失败",e.getMessage());
        }finally {
               if(input!=null){
                   input.close();
                }
               if(output!=null){
                   output.close();
               }
            logger.debug("IO closed");
        }
        com.sensetime.iva.ipet.entity.File file1 = new com.sensetime.iva.ipet.entity.File();
        file1.setName(fileName);
        file1.setPath(loadPath);
        file1.setUploadTime(new Timestamp(System.currentTimeMillis()));
        logger.debug("file1 info [{}]",file1);
        logger.debug("上传文件结束");
        return  file1;
    }

    /**
     * 设置excel样式
     * @param workbook excel
     * @param fontsize 大小
     * @param bold 是否加粗
     * @param isCenter  是否居中
     * @return 返回样式
     */
    public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontsize,boolean bold,boolean isCenter) {
       logger.info("开始设置导出的HSSFWorkbook的样式");
        HSSFCellStyle style = workbook.createCellStyle();
        //是否水平居中
        if(isCenter){
            //水平居中
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        }
        //垂直居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //创建字体
        HSSFFont font = workbook.createFont();
        //是否加粗字体
        if(bold){
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        font.setFontHeightInPoints(fontsize);
        //加载字体
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);

        logger.info("设置导出的HSSFWorkbook的样式结束");
        return style;
    }

    /**
     * 转化excel里面的时间格式
     * @param date 时间数字
     * @return 符合类型的时间格式类型
     */
    public static String changeCellDate(String date) throws Exception{
        logger.info("将日期转化成yyyy/MM/dd的字符串");
        if(StringUtils.isEmpty(date)){
            return null;
        }else{
            int longDate=1;
            try {
                longDate=Integer.parseInt(date);
            }catch (Exception e){
                logger.error("error date format" ,date);
                throw new BusinessException("交付日期请填写yyyy/MM/dd格式的时间");
            }
            return DateUtil.dateToString(new Date(0,0,longDate-1),DatePattern.yyyy_MM_dd);
        }
    }

    /**
     * 转化excel里面的时间格式
     * @param fileSize 文件大小
     * @param acceptSize 最大上传大小单位M
     */
    public static void volidateFileSize(long fileSize,int acceptSize) throws Exception{
        logger.info("开始验证上传文件大小");
        if(fileSize/(CellNum.CHANGE_SIZE*CellNum.CHANGE_SIZE)>acceptSize){
            String errorMsg= ExceptionMsg.EXCEL_LOAD_OVER_MAX+ acceptSize+"M";
            logger.error("over max load amount {}",errorMsg);
            throw  new Exception(errorMsg);
        }
    }

    public static void delFile(String path,String filename)throws  Exception{
        logger.info("得到要删除的文件路径及名称:"+path+filename);
        File file=new File(path+"/"+filename);
        if(file.exists()&&file.isFile()){
            logger.debug("开始删除");
            file.delete();
        }
    }

    /**
     * 插入图片
     * @param workbook  excel
     * @param sheet sheet
     * @param is  输入流
     * @param row 图片插入的行位置
     * @param col 图片插入的列位置
     * @throws IOException
     */
    public static void insertPicture(HSSFWorkbook workbook, HSSFSheet sheet, InputStream is, int row, int col) throws IOException {
        byte[] bytes = IOUtils.toByteArray(is);
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        CreationHelper helper = workbook.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        // 图片插入坐标
        anchor.setCol1(col);
        anchor.setRow1(row);
        // 插入图片
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        pict.resize();
    }

    /**
     *
     * @param fileName  文件名称
     * @return 是否是图片
     */
    public static boolean isImage(String fileName)  {
        return fileName.matches(".+(.jpg|.png|.JPG|.PNG|.jpeg|.bmp|.gif)$");
    }

}
