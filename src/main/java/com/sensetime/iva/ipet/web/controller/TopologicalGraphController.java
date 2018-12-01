package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.entity.TopologicalGraph;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.TopologicalGraphService;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.form.FileForm;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import com.sensetime.iva.ipet.web.annotation.MyResources;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.util.List;

/**
 * @author  gongchao
 */
@Api(description  = "拓扑图（增、删、查）")
@RestController
public class TopologicalGraphController {
    private static final Logger logger = LoggerFactory.getLogger(TopologicalGraphController.class);
    @Autowired
    TopologicalGraphService topologicalGraphService;


    @MyResources(name ="上传拓扑图的文件",code="import_topological_graph",type=ResourceType.URL,path="/topologicalGraph/doc",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 12,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping("/topologicalGraph/doc")
    @ApiOperation(value="上传拓扑图的文件")
    public ResponseBody<FileForm> importTopologicalGraph(@ApiParam(name="file",value="拓扑图文件",required=true) @RequestParam("file") MultipartFile file, @ApiParam(name="projectId",value="项目id",required=true) @RequestParam Integer projectId) throws Exception{
        logger.info("[start]importTopologicalGraph/doc  load filename is "+file.getOriginalFilename());
        List<TopologicalGraph> topologicalGraphs;
        if(!ExcelImportUtil.isImage(file.getOriginalFilename())){
            logger.error(ExceptionMsg.NOT_IMAGE);
            return ReturnUtil.error(Code.ERROR, ExceptionMsg.NOT_IMAGE);
        }
        topologicalGraphService.importTopologicalGraph(file, projectId);
        topologicalGraphs = topologicalGraphService.selectByProjectId(projectId);
        FileForm filePath = topologicalGraphService.getFilePath(topologicalGraphs);
        logger.info("[end] get TopologicalGraph path success {}",filePath);
        return ReturnUtil.success(ReturnMsg.SUCCESS,filePath);
    }

    @MyResources(name ="删除拓扑图",code="delete_topological_graph",type=ResourceType.URL,path="/topologicalGraph/{id}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 13,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @ApiOperation(value="删除拓扑图")
    @DeleteMapping(value = "/topologicalGraph/{id}",produces="application/json;charset=UTF-8")
    public ResponseBody<FileForm> deleteTopologicalGraph(@ApiParam(name="id",value="拓扑图id",required=true) @PathVariable Integer id) throws Exception{
        logger.info("/deleteTopologicalGraph id"+id);
        List<TopologicalGraph> topologicalGraphs= topologicalGraphService.deleteTopologicalGraph(id);
        FileForm filePath = topologicalGraphService.getFilePath(topologicalGraphs);
        return ReturnUtil.success(ReturnMsg.SUCCESS,filePath);
    }

    @MyResources(name ="获取拓扑图",code="get_topological_graph",type=ResourceType.URL,path="/topologicalGraph/{projectId}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 14,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = "/topologicalGraph/{projectId}",produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取拓扑图")
    public ResponseBody<FileForm> getTopologicalGraph(@ApiParam(name="projectId",value="项目id",required=true) @PathVariable Integer projectId) {
        logger.info("getTopologicalGraph by projectId ="+projectId);
        List<TopologicalGraph> topologicalGraphs = topologicalGraphService.selectByProjectId(projectId);
        FileForm filePath = topologicalGraphService.getFilePath(topologicalGraphs);
        return ReturnUtil.success(ReturnMsg.SUCCESS,filePath);
    }
}
