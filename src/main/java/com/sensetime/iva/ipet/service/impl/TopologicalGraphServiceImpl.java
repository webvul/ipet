package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.config.LoadFileConfig;
import com.sensetime.iva.ipet.entity.PhysicalMap;
import com.sensetime.iva.ipet.entity.Project;
import com.sensetime.iva.ipet.entity.TopologicalGraph;
import com.sensetime.iva.ipet.mapper.TopologicalGraphMapper;
import com.sensetime.iva.ipet.service.ProjectService;
import com.sensetime.iva.ipet.service.TopologicalGraphService;
import com.sensetime.iva.ipet.util.DateUtil;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.vo.form.FileForm;
import com.sensetime.iva.ipet.web.exception.BusinessException;
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author  gongchao
 */
@Component
public class TopologicalGraphServiceImpl implements TopologicalGraphService {
    private static final Logger logger = LoggerFactory.getLogger(TopologicalGraphServiceImpl.class);
    @Autowired
    ProjectService projectService;
    @Autowired
    LoadFileConfig loadFileConfig;
    @Autowired
    ServerProperties serverProperties;
    @Autowired
    TopologicalGraphMapper topologicalGraphMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey id="+id);
        return topologicalGraphMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TopologicalGraph topologicalGraph) {
        logger.info("insert topologicalGraph="+topologicalGraph);
        return topologicalGraphMapper.insert(topologicalGraph);
    }

    @Override
    public TopologicalGraph selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id="+id);
        return topologicalGraphMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TopologicalGraph> selectAll() {
        logger.info("selectAll TopologicalGraph");
        return topologicalGraphMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(TopologicalGraph topologicalGraph) {
        logger.info("updateByPrimaryKey topologicalGraph="+topologicalGraph);
        return topologicalGraphMapper.updateByPrimaryKey(topologicalGraph);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public TopologicalGraph uploadTopologicalGraph(MultipartFile file, String pathPrefix,Integer projectId) throws Exception {
        logger.info("读取文件流");
        InputStream input = file.getInputStream();
        //获取文件名称及后缀名
        String originalFilename = file.getOriginalFilename();
        //每天创建一个文件夹
        String yyyyMMdd = DateUtil.dateToString(new Date(), DatePattern.yyyyMMdd);
        //保存目录
        String loadPath=pathPrefix+yyyyMMdd;
        String fileName=System.currentTimeMillis()+originalFilename;

        TopologicalGraph topologicalGraph = new TopologicalGraph();
        topologicalGraph.setName(fileName);
        topologicalGraph.setPath(loadPath);
        topologicalGraph.setProjectId(projectId);
        topologicalGraph.setUploadTime(new Timestamp(System.currentTimeMillis()));
        this.insert(topologicalGraph);

        logger.info("保存的文件信息:"+topologicalGraph);
        //保存之后的文件
        File filePath = new File(loadPath);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        File saveFile = new File(filePath,fileName);
        logger.info("创建服务器上的文件");
        saveFile.createNewFile();
        OutputStream out = null;
        try {
            out = new FileOutputStream(saveFile);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
        } catch (IOException e){
            logger.error("上传文件失败:"+e.getMessage());
            throw new IOException(e);
        }
        finally {
            logger.info("关闭流");
            if(input!=null){
                input.close();
            }
            if(out!=null){
                out.close();
            }
        }
       logger.info("上传文件结束");
        return  topologicalGraph;
    }

    @Override
    public int selectCountByProjectId(Integer projectId) {
        logger.info("获取当前项目的总的上传数量");
        return topologicalGraphMapper.selectCountByProjectId(projectId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TopologicalGraph importTopologicalGraph(MultipartFile file, Integer projectId) throws Exception {
        //验证文件大小
        logger.info("验证上传文件大小");
        long size = file.getSize();
        ExcelImportUtil.volidateFileSize(size, CellNum.MAX_MAP_OR_GRAPH_LOAD);
        logger.info("大小验证通过");
        //判断累计上传文件数量
        int counts = this.selectCountByProjectId(projectId);
        if (counts >= ProjectArgs.MAP_OR_GRAPH_MAX_COUNT) {
            logger.error("累计上传数量已经最大,数量为:" + counts);
            throw new BusinessException("累计上传数量已经最大");
        }
        logger.info("验证项目是否存在");
        Project plan = projectService.selectByPrimaryKey(projectId);
        if (plan == null) {
            logger.error(ExceptionMsg.EMPTY_PROJECT);
            throw new BusinessException(ExceptionMsg.EMPTY_PROJECT);
        }
        //上传文件
        TopologicalGraph afterFile = this.uploadTopologicalGraph(file, loadFileConfig.getTopologicalGraphLocation(),projectId);
        return afterFile;
    }

    @Override
    public List<TopologicalGraph> deleteTopologicalGraph(Integer id) throws Exception {
        TopologicalGraph topologicalGraph = this.selectByPrimaryKey(id);
        logger.info("获取当前拓扑图信息:"+topologicalGraph);
        if(topologicalGraph!=null){
            logger.info("删除当前拓扑图信息");
            this.deleteByPrimaryKey(id);
            logger.info("删除当前服务器拓扑图的文件");
            ExcelImportUtil.delFile(topologicalGraph.getPath(),topologicalGraph.getName());
            logger.info("返回当前项目的拓扑图信息");
            if(topologicalGraph.getProjectId()!=null){
                return this.selectByProjectId(topologicalGraph.getProjectId());
            }else{
                return  null;
            }
        }else{
            logger.error(ExceptionMsg.DELETE_FILE_NOT_FOUND);
            throw new BusinessException(ExceptionMsg.DELETE_FILE_NOT_FOUND);
        }
    }

    @Override
    public List<TopologicalGraph> selectByProjectId(Integer projectId) {
        return topologicalGraphMapper.selectByProjectId(projectId);
    }

    @Override
    public void exportTopological(HSSFWorkbook wb, Integer projectId) throws Exception {
            logger.info("获取拓扑图清单");
            List<TopologicalGraph> topologicalGraphs = this.selectByProjectId(projectId);
            //设置sheet的名字
            logger.info("创建拓扑图sheet");
            HSSFSheet sheet = wb.createSheet(ExcelTitleToClass.EXPORT_PROJECT_CHECK_PHYSICAL);
            if(topologicalGraphs!=null&&topologicalGraphs.size()>0){
                logger.info("给拓扑图的excel插入图片"+topologicalGraphs.toString());
                this.setImages(wb,sheet,topologicalGraphs);
            }else{
                logger.warn("没有查到拓扑图的图片");
            }
    }

    @Override
    public void setImages(HSSFWorkbook wb, HSSFSheet sheet, List<TopologicalGraph> topologicalGraphs) throws Exception {
            //图片插入的位置
            int insertRow=0;
            int insertColumn=0;
            for (int i=0;i<topologicalGraphs.size();i++){
                TopologicalGraph map = topologicalGraphs.get(i);
                if(i%2==0){
                    //获取图片名称
                    String fileName = map.getPath().concat(File.separator).concat(map.getName());
                    File file = new File(fileName);
                    InputStream in = new FileInputStream(file);
                    ExcelImportUtil.insertPicture(wb,sheet,in,insertRow,insertColumn);
                    BufferedImage image = ImageIO.read(file);
                    int width = image.getWidth();
                    insertColumn=width/60+1;
                }else{
                    //获取图片名称
                    String fileName = map.getPath().concat(File.separator).concat(map.getName());
                    File file = new File(fileName);
                    //获取图片大小
                    BufferedImage image = ImageIO.read(file);
                    InputStream in = new FileInputStream(file);
                    int height = image.getHeight();
                    ExcelImportUtil.insertPicture(wb,sheet,in,insertRow,insertColumn);
                    insertRow+=height/16+1;
                    insertColumn=0;
                }
            }
    }

    @Override
    public FileForm getFilePath(List<TopologicalGraph> topologicalGraphs) {
        logger.debug("getFilePath  {}",topologicalGraphs);
        FileForm fileForm = new FileForm();
        HashMap<Integer, String> fileMap = new HashMap<>(16);
        if(topologicalGraphs!=null&&topologicalGraphs.size()>0){
            for (TopologicalGraph topo: topologicalGraphs) {
                String[] pathArray = topo.getPath().split("/");
                String url="http://"+loadFileConfig.getDownloadIp()+":"+loadFileConfig.getDownloadPort()+serverProperties.getContextPath()+"/"+pathArray[pathArray.length-3]+"/"+pathArray[pathArray.length-2]+"/"+pathArray[pathArray.length-1]+"/"+topo.getName();
                fileMap.put(topo.getId(),url);
            }
        }
        fileForm.setFileMap(fileMap);
        logger.info("getFilePath success FilePath {}",fileForm);
        return fileForm;
    }
}
