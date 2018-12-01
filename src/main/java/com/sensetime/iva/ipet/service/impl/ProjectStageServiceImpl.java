package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.common.CellNum;
import com.sensetime.iva.ipet.common.ExceptionMsg;
import com.sensetime.iva.ipet.entity.BusinessSystem;
import com.sensetime.iva.ipet.entity.BusinessSystemPlatform;
import com.sensetime.iva.ipet.entity.ProjectStage;
import com.sensetime.iva.ipet.mapper.ProjectStageMapper;
import com.sensetime.iva.ipet.service.BusinessSystemPlatformService;
import com.sensetime.iva.ipet.service.BusinessSystemService;
import com.sensetime.iva.ipet.service.ProjectStageService;
import com.sensetime.iva.ipet.util.ChooseUtil;
import com.sensetime.iva.ipet.util.ExcelImportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  gongchao
 */
@Component
public class ProjectStageServiceImpl implements ProjectStageService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectStageServiceImpl.class);

    @Autowired
    ProjectStageMapper projectStageMapper;
    @Autowired
    BusinessSystemPlatformService businessSystemPlatformService;
    @Autowired
    BusinessSystemService businessSystemService;
    @Override
    public List<ProjectStage> selectAll() {
        logger.info("selectAll ProjectStage");
        return projectStageMapper.selectAll();
    }

    @Override
    public ProjectStage selectByPrimaryKey(Integer id) {
        logger.info("selectByPrimaryKey id= "+id);
        return projectStageMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(ProjectStage projectStage) {
        logger.info("updateByPrimaryKey ProjectStage= "+projectStage);
        return projectStageMapper.updateByPrimaryKey(projectStage);
    }

    @Override
    public int insert(ProjectStage projectStage) {
        logger.info("insert ProjectStage= "+projectStage);
        return projectStageMapper.insert(projectStage);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        logger.info("deleteByPrimaryKey ProjectStage id= "+id);
        return projectStageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ProjectStage> getByProjectId(Integer projectId) {
        logger.info("getByProjectId projectId = "+projectId);
        return projectStageMapper.getByProjectId(projectId);
    }

    @Override
    public List<ProjectStage> setProjectStage(List<String> valuesList) throws Exception{
        logger.info("解析项目阶段信息为:"+valuesList.toString());
        List<ProjectStage> stageList= new ArrayList<>();
        //去除标题占用的10个位置 从项目目标到定制化需求
        int countStage= (valuesList.size()-10)/9;
        //至少需要一个项目阶段
        if(countStage>=1){
              for(int j = CellNum.TENTH; j<valuesList.size(); j++) {
                  //阶段
                  String step = valuesList.get(j);
                  j++;
                  //目标
                  String target = valuesList.get(j);
                  j++;
                  //交付日期
                  String deliveryDate = valuesList.get(j);
                  //时间转换
                  deliveryDate = ExcelImportUtil.changeCellDate(deliveryDate);
                  j++;
                  //业务系统名称
                  String businessSystemName = valuesList.get(j);
                  j++;
                  //平台名称
                  String platformName = valuesList.get(j);
                  j++;
                  //阶段规模
                  String stageScale = valuesList.get(j);
                  j++;
                  //预期规模
                  String expectedScale = valuesList.get(j);
                  j++;
                  //产品要求
                  String productRequire = valuesList.get(j);
                  j++;
                  //定制化需求
                  String customization = valuesList.get(j);
                  logger.info("开始设置项目阶段信息");
                  ProjectStage stage = new ProjectStage();
                  int step1 = ChooseUtil.getProjectStep(step);
                  if(step1==-1){
                      throw  new Exception(ExceptionMsg.STEP_NOT_FOUND);
                  }
                  stage.setStep(step1);
                  stage.setTarget(target);
                  stage.setDeliveryDate(deliveryDate);
                  //系统
                  if(!StringUtils.isEmpty(businessSystemName)){
                      BusinessSystem businessSystem = businessSystemService.selectByName(businessSystemName);
                      if(businessSystem==null){
                          logger.error(ExceptionMsg.SYSTEM_NOT_FOUND);
                          throw  new Exception(ExceptionMsg.SYSTEM_NOT_FOUND);
                      }
                      stage.setBusinessSystemId(businessSystem.getId());
                      //平台
                      if(!StringUtils.isEmpty(platformName)){
                          BusinessSystemPlatform systemPlatform = businessSystemPlatformService.selectByNameAndSystemId(platformName,businessSystem.getId());
                          if(systemPlatform==null){
                              logger.error(businessSystemName+"系统下"+ ExceptionMsg.SYSTEM_PLATFORM_NOT_FOUND);
                              throw  new Exception(businessSystemName+"系统下"+ ExceptionMsg.SYSTEM_PLATFORM_NOT_FOUND);
                          }
                          stage.setPlatformId(systemPlatform.getId());
                      }
                  }
                 //客户需求
                 if(!StringUtils.isEmpty(productRequire)){
                     int require = ChooseUtil.getProjectRequire(productRequire);
                     if(require==-1){
                         logger.error(ExceptionMsg.PRODUCT_REQUIRE_NOT_FOUND);
                         throw  new Exception(ExceptionMsg.PRODUCT_REQUIRE_NOT_FOUND);
                     }
                     stage.setProductRequire(require);
                 }
                  stage.setStageScale(stageScale);
                  stage.setExpectedScale(expectedScale);
                  //定制化
                  if(!StringUtils.isEmpty(customization)){
                      int customizationRequire = ChooseUtil.getProjectRequire(customization);
                      if(customizationRequire==-1){
                          logger.error(ExceptionMsg.PRODUCT_CUSTOMIZATION_NOT_FOUND);
                          throw  new Exception(ExceptionMsg.PRODUCT_CUSTOMIZATION_NOT_FOUND);
                      }
                      stage.setCustomization(customizationRequire);
                  }
                  stageList.add(stage);
              }
        }else{
            //项目阶段为空不做处理
            return null;
        }
        logger.info("返回设置后的项目阶段信息");
        return stageList;
    }
}
