package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.config.LoadFileConfig;
import com.sensetime.iva.ipet.entity.DeliverList;
import com.sensetime.iva.ipet.entity.PhysicalMap;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.mapper.PhysicalMapMapper;
import com.sensetime.iva.ipet.service.PhysicalMapService;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.util.DateUtil;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.vo.form.FileForm;
import com.sensetime.iva.ipet.web.exception.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.acl.LastOwnerException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author  gongchao
 */
@Component
public class PhysicalMapServiceImpl implements PhysicalMapService {
    private static final Logger logger = LoggerFactory.getLogger(PhysicalMapServiceImpl.class);

    @Autowired
    PhysicalMapMapper physicalMapMapper;
    @Autowired
    ProjectService projectService;
    @Autowired
    LoadFileConfig loadFileConfig;
    @Autowired
    ServerProperties serverProperties;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+id);
        return physicalMapMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PhysicalMap physicalMap) {
        logger.info("insert physicalMap="+physicalMap);
        return physicalMapMapper.insert(physicalMap);
    }

    @Override
    public PhysicalMap selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id="+id);
        return physicalMapMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PhysicalMap> selectAll() {
        logger.info("selectAll PhysicalMap=");
        return physicalMapMapper.selectAll();
    }
    @Override
    public int updateByPrimaryKey(PhysicalMap physicalMap) {
        logger.info("updateByPrimaryKey physicalMap="+physicalMap);
        return physicalMapMapper.updateByPrimaryKey(physicalMap);
    }

    @Override
    public PhysicalMap uploadPhysicalMap(MultipartFile file, String pathPrefix,Integer projectId) throws Exception {
        logger.info("读取文件流");
        InputStream input = file.getInputStream();
        //获取文件名称及后缀名
        String originalFilename = file.getOriginalFilename();
        logger.info("load originalFilename [{}]",originalFilename);
        //每天创建一个文件夹
        String yyyyMMdd = DateUtil.dateToString(new Date(), DatePattern.yyyyMMdd);
        //保存目录
        String loadPath=pathPrefix+yyyyMMdd;

        String fileName=System.currentTimeMillis()+originalFilename;
        PhysicalMap physicalMap = new PhysicalMap();
        physicalMap.setName(fileName);
        physicalMap.setPath(loadPath);
        physicalMap.setProjectId(projectId);
        physicalMap.setUploadTime(new Timestamp(System.currentTimeMillis()));
        logger.info("insert physicalMap [{}]",physicalMap);
        this.insert(physicalMap);
        logger.info("创建服务器上物理图的文件"+physicalMap);
        logger.info("设置保存的而文件路径");
        //保存之后的文件
        File filePath = new File(loadPath);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        File saveFile = new File(filePath,fileName);
        saveFile.createNewFile();
        OutputStream out = null;
        try {
            logger.info("开始写入数据");
            out = new FileOutputStream(saveFile);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
            logger.info("写入数据结束");
        }catch (IOException e){
            logger.error("上传服务器失败"+e.getMessage());
            throw  new IOException(e);
        }finally {
            logger.info("关闭流");
            if(input!=null){
                input.close();
            }
            if(out!=null){
                out.close();
            }
        }
        logger.info("上传文件结束");
        return  physicalMap;
    }

    @Override
    public int selectCountByProjectId(Integer projectId) {
        logger.info("获取当前项目的总的上传数量");
        return physicalMapMapper.selectCountByProjectId(projectId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PhysicalMap importPhysicalMap(MultipartFile file, Integer projectId) throws Exception {
        //验证文件大小
        logger.info("验证上传文件大小");
        long size = file.getSize();
        ExcelImportUtil.volidateFileSize(size, CellNum.MAX_MAP_OR_GRAPH_LOAD);
        logger.info("大小验证通过");
        //判断累计上传文件数量
        int counts = this.selectCountByProjectId(projectId);
        if (counts >= ProjectArgs.MAP_OR_GRAPH_MAX_COUNT) {
            logger.info("累计上传数量已经最大,数量为:" + counts);
            throw new BusinessException("累计上传数量已经最大");
        }
        logger.info("验证项目是否存在");
        Project plan = projectService.selectByPrimaryKey(projectId);
        if (plan == null) {
            logger.info(ExceptionMsg.EMPTY_PROJECT);
            throw new BusinessException(ExceptionMsg.EMPTY_PROJECT);
        }
        //上传文件
        PhysicalMap afterFile = this.uploadPhysicalMap(file, loadFileConfig.getPhysicalMapLocation(),projectId);
        return afterFile;
    }

    @Override
    public List<PhysicalMap> deletePhysicalMap(Integer id) throws Exception {
        logger.info("获取当前物理图信息");
        PhysicalMap physicalMap = this.selectByPrimaryKey(id);
        if(physicalMap!=null){
            logger.info("删除当前物理图信息");
            this.deleteByPrimaryKey(id);
            logger.info("删除当前服务器物理图的文件");
            ExcelImportUtil.delFile(physicalMap.getPath(),physicalMap.getName());
            logger.info("返回当前项目的物理图信息");
            if(physicalMap.getProjectId()!=null){
                return this.selectByProjectId(physicalMap.getProjectId());
            }else{
                return  null;
            }
        }else{
            logger.error(ExceptionMsg.DELETE_FILE_NOT_FOUND);
            throw new BusinessException(ExceptionMsg.DELETE_FILE_NOT_FOUND);
        }
    }

    @Override
    public List<PhysicalMap> selectByProjectId(Integer projectId) {
        logger.info("selectByProjectId projectId="+projectId);
        return physicalMapMapper.selectByProjectId(projectId);
    }

    @Override
    public void exportPhysical(HSSFWorkbook wb, Integer projectId) throws Exception {
            logger.info("获取物理图清单");
            List<PhysicalMap> physicalMaps = this.selectByProjectId(projectId);
            //设置sheet的名字
            logger.info("创建物理图sheet");
            HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT_CHECK_TOPOLOGICAL);
            if(physicalMaps!=null&&physicalMaps.size()>0){
                logger.info("给物理图的excel插入图片"+physicalMaps.toString());
                this.setImages(wb,sheet,physicalMaps);
            }else{
                logger.warn("没有查到物理图的图片");
            }

    }

    @Override
    public void setImages(HSSFWorkbook wb, HSSFSheet sheet, List<PhysicalMap> physicalMaps) throws Exception {
        //图片插入的位置
        int insertRow=0;
        int insertColumn=0;
        for (int i=0;i<physicalMaps.size();i++){
            PhysicalMap map = physicalMaps.get(i);
            if(i%2==0){
                //获取文件名称
                String fileName = map.getPath().concat(File.separator).concat(map.getName());
                File file = new File(fileName);
                InputStream in = new FileInputStream(file);
                logger.debug("插入物理图片");
                ExcelImportUtil.insertPicture(wb,sheet,in,insertRow,insertColumn);
                BufferedImage image = ImageIO.read(file);
                int width = image.getWidth();
                insertColumn=width/60+1;
            }else{
                //获取文件名称
                String fileName = map.getPath().concat(File.separator).concat(map.getName());
                File file = new File(fileName);
                BufferedImage image = ImageIO.read(file);
                InputStream in = new FileInputStream(file);
                int height = image.getHeight();
                logger.debug("插入物理图片");
                ExcelImportUtil.insertPicture(wb,sheet,in,insertRow,insertColumn);
                insertRow+=height/16+1;
                insertColumn=0;
            }
        }
    }

    @Override
    public FileForm getFilePath(List<PhysicalMap> physicalMaps) {
        FileForm fileForm = new FileForm();
        HashMap<Integer, String> fileMap = new HashMap<>(16);
        if(physicalMaps!=null&&physicalMaps.size()>0){
            for (PhysicalMap map: physicalMaps) {
                //path=D:/ipet/load/project/20180827
                String[] pathArray = map.getPath().split("/");
                String url="http://"+loadFileConfig.getDownloadIp()+":"+loadFileConfig.getDownloadPort()+serverProperties.getContextPath()+"/"+pathArray[pathArray.length-3]+"/"+pathArray[pathArray.length-2]+"/"+pathArray[pathArray.length-1]+"/"+map.getName();
                fileMap.put(map.getId(),url);
            }
        }
        fileForm.setFileMap(fileMap);
        logger.info("getFilePath success FilePath {}",fileForm);
        return fileForm;
    }
}
