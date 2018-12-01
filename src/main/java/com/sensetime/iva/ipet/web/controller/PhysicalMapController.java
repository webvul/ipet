package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.*;
import com.sensetime.iva.ipet.entity.PhysicalMap;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.PhysicalMapService;
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

import java.util.List;

/**
 * @author  gongchao
 */
@RestController
@Api(description  = "物理图（增、删、查、上传）")
public class PhysicalMapController {
    private static final Logger logger = LoggerFactory.getLogger(TopologicalGraphController.class);
    @Autowired
    PhysicalMapService physicalMapService;

    @MyResources(name ="上传物理图的文件",code="import_physical_map",type=ResourceType.URL,path="/physicalMap/doc",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 15,method = RequestMethod.POST,role = IConstant.ROLE_PM)
    @PostMapping("/physicalMap/doc")
    @ApiOperation(value="上传物理图的文件")
    public ResponseBody<FileForm> importPhysicalMap(@ApiParam(name="file",value="物理图",required=true) @RequestParam("file") MultipartFile file, @ApiParam(name="projectId",value="项目id",required=true) @RequestParam Integer projectId) throws Exception{
        logger.info("[start]/importPhysicalMap/doc  load filename is "+file.getOriginalFilename());
        if(!ExcelImportUtil.isImage(file.getOriginalFilename())){
            logger.error("[end]importPhysicalMap error {}",ExceptionMsg.NOT_IMAGE);
            return ReturnUtil.error(Code.ERROR, ExceptionMsg.NOT_IMAGE);
        }
        physicalMapService.importPhysicalMap(file, projectId);
        List<PhysicalMap> physicalMaps = physicalMapService.selectByProjectId(projectId);
        FileForm  fileForm = physicalMapService.getFilePath(physicalMaps);
        return ReturnUtil.success(ReturnMsg.SUCCESS,fileForm);
    }

    @MyResources(name ="删除物理图",code="delete_physical_map",type=ResourceType.URL,path="/physicalMap/{id}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 21,method = RequestMethod.DELETE,role = IConstant.ROLE_PM)
    @ApiOperation(value="删除物理图")
    @DeleteMapping(value = "/physicalMap/{id}",produces="application/json;charset=UTF-8")
    public ResponseBody<FileForm> deletePhysicalMap(@ApiParam(name="id",value="物理图id",required=true) @PathVariable Integer id) throws Exception{
        logger.info("[start]deletePhysicalMap id"+id);
        List<PhysicalMap> physicalMaps = physicalMapService.deletePhysicalMap(id);
        FileForm   fileForm = physicalMapService.getFilePath(physicalMaps);
        return ReturnUtil.success(ReturnMsg.SUCCESS,fileForm);
    }

    @MyResources(name ="获取物理图",code="get_physical_map",type=ResourceType.URL,path="/physicalMap/{projectId}",parent = PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE,seq = 20,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = "/physicalMap/{projectId}",produces="application/json;charset=UTF-8")
    @ApiOperation(value="获取物理图")
    public ResponseBody<FileForm> getPhysicalMap(@ApiParam(name="projectId",value="项目id",required=true) @PathVariable Integer projectId) throws Exception{
        logger.info("[start]getPhysicalMap by projectId {}",projectId);
        List<PhysicalMap> physicalMaps = physicalMapService.selectByProjectId(projectId);
        FileForm fileForm = physicalMapService.getFilePath(physicalMaps);
        return ReturnUtil.success(ReturnMsg.SUCCESS,fileForm);
    }
}
