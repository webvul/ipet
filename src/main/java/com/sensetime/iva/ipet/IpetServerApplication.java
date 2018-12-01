package com.sensetime.iva.ipet;

import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.config.security.RequestMappingHandlerConfig;
import com.sensetime.iva.ipet.entity.Resource;
import com.sensetime.iva.ipet.entity.RoleEntity;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.ResourceService;
import com.sensetime.iva.ipet.service.RoleService;
import com.sensetime.iva.ipet.web.annotation.MyResources;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yore
 */
@SpringBootApplication
@MapperScan("com.sensetime.iva.ipet.mapper")
@EnableCaching
@EnableScheduling
public class IpetServerApplication  extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(IpetServerApplication.class);

	@Autowired
	private RequestMappingHandlerConfig requestMappingHandlerConfig;
	@Autowired
    ResourceService resourceService;
	@Autowired
    RoleService roleService;
    @Autowired
    RedisTemplate redisTemplate;

	@Value("${isOpenScanResource}")
	private boolean isOpenScanResource;

    public static void main(String[] args) {
        SpringApplication.run(IpetServerApplication.class, args);
    }
    private static List<Resource> loadPMResource = new ArrayList<>();
    private static List<Resource> loadManagerResource = new ArrayList<>();


    /**
     * 这个注解很重要，可以在每次启动的时候检查是否有URL更新，RequestMappingHandlerMapping只能在controller层用。这里我们放在主类中
     */
	@PostConstruct
	public void detectHandlerMethods(){
	    if(isOpenScanResource){
            //初始化固定权限资源
            initPredefineResource();
            //动态加载资源
            dynamicLoadResource();
            //给超级管理员初始化资源
            postAllResourceToSuperManager();
            //给项目经理和管理员初始化资源
            //TODO 目前只能根据MyResources中role标识在动态增加，调用接口删除的仍然会恢复
            initManagerAndPmResource();
        }

        //将所有资源以及访问资源需要的角色放入redis中，以便权限校验时使用
        initRedisAllResourcesAndRoles();

	}

    private void initManagerAndPmResource() {
	    //初始化项目经理角色
	    RoleEntity pmRoleEntity = roleService.queryRoleByRoleName(IConstant.ROLE_PM);
	    if(pmRoleEntity == null){
            pmRoleEntity.setName("项目经理");
            pmRoleEntity.setRoleName(IConstant.ROLE_PM);
            roleService.addRole(pmRoleEntity);
        }
        //初始化管理员角色
        RoleEntity managerRoleEntity = roleService.queryRoleByRoleName(IConstant.ROLE_MANAGER);
        if(managerRoleEntity == null){
            managerRoleEntity.setName("管理员");
            managerRoleEntity.setRoleName(IConstant.ROLE_MANAGER);
            roleService.addRole(managerRoleEntity);
        }

	    RoleEntity pmRole = roleService.queryRoleByRoleName(IConstant.ROLE_PM);
        List<Resource> pmResources = resourceService.getResourcesByRoleId(pmRole.getId());
        RoleEntity managerRole = roleService.queryRoleByRoleName(IConstant.ROLE_MANAGER);
        List<Resource> managerResources = resourceService.getResourcesByRoleId(managerRole.getId());

        //获取初始化资源中pm的资源与库中pm资源的差集，标识为新增资源
        loadPMResource.removeAll(pmResources);
        if(loadPMResource.size() > 0){
            logger.warn("发现"+pmRole.getRoleName()+ " "+loadPMResource.size()+" 条新资源");
            for (Resource resource :loadPMResource ) {
                logger.warn("为"+pmRole.getRoleName()+" 新增资源 "+resource.toString());
                resourceService.addResourceForRole(pmRole.getId(),resource.getId());
            }
        }

        //获取初始化资源中manager的资源与库中manager资源的差集，标识为新增资源
        loadManagerResource.removeAll(managerResources);
        if(loadManagerResource.size() > 0){
            logger.warn("发现"+managerRole.getRoleName()+ " "+loadManagerResource.size()+" 条新资源");
            for (Resource resource :loadManagerResource ) {
                logger.warn("给"+managerRole.getRoleName()+" 新增资源 "+resource.toString());
                resourceService.addResourceForRole(managerRole.getId(),resource.getId());
            }
        }

    }

    private void initRedisAllResourcesAndRoles() {

	    List<Resource> resourceList = resourceService.getAllAndRoles();
	    Map<String,Resource> resourceMap = new HashMap<>(resourceList.size());
        for (Resource resource : resourceList ) {
            resourceMap.put(resource.getPath()+"-"+resource.getMethod(),resource);
        }
        redisTemplate.opsForValue().set(IConstant.ALL_RESOURCES_AND_ROLES,resourceMap);
    }

    private void postAllResourceToSuperManager(){
	    RoleEntity roleEntity = roleService.queryRoleByRoleName("ROLE_admin");
	    //获取所有url资源
        List<Resource> allResource = resourceService.getAllUrlResource();
        //获取角色对应资源
        List<Resource> roleResources = resourceService.getResourcesByRoleId(roleEntity.getId());
        //算出所有资源和超级管理员持有资源的差集，表示新增资源，添加到超级管理员的权限中
        allResource.removeAll(roleResources);
        if(allResource.size() > 0){
			for (Resource resource : allResource) {
				resourceService.addResourceForRole(roleEntity.getId(),resource.getId());
			}
		}
    }

	private void dynamicLoadResource(){

        final RequestMappingHandlerMapping requestMappingHandlerMapping = requestMappingHandlerConfig.requestMappingHandlerMapping ();
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for(Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            MyResources annotation = hasValidAnnotation(handlerMethod);
            if(annotation != null){

                Resource resource = resourceService.queryByCode(annotation.code());
                Resource annotationResource = new Resource();
                annotationResource.setCode(annotation.code());
                annotationResource.setMethod(annotation.method());
                annotationResource.setName(annotation.name());
                annotationResource.setParent(annotation.parent());
                annotationResource.setPath(annotation.path());
                annotationResource.setSeq(annotation.seq());
                annotationResource.setType(annotation.type());
                if(resource != null ){
                    //注意Resource hash 和equals不包含id属性
                    if (!resource.equals(annotationResource)){
                        annotationResource.setId(resource.getId());
                        logger.warn("发现更新资源:" + annotationResource.toString());
                        resourceService.updateResource(annotationResource);
                    }
                }else{
                    logger.warn("发现新增资源:" + annotationResource.toString());
                    resourceService.addResource(annotationResource);
                }
                if(StringUtils.isNotBlank(annotation.role())){
                    Resource roleResource = resourceService.queryByCode(annotation.code());
                    String[] roles = annotation.role().split(",");
                    for (String roleName : roles) {
                        if(IConstant.ROLE_PM.equals(roleName)){
                            loadPMResource.add(roleResource);
                        }else if(IConstant.ROLE_MANAGER.equals(roleName)){
                            loadManagerResource.add(roleResource);
                        }
                    }
                }
            }
        }

        //此处不可和上方for循环合并，因上方涉及到新增资源，会响应相关逻辑
        //获取库中所有type=URL的资源值并放入MultiKeyMap
        MultiKeyMap mkmap = new MultiKeyMap<>();
        List<Resource> resourceList = resourceService.getAllUrlResource();
        resourceList.forEach(resource -> mkmap.put(resource.getPath(), resource.getMethod(), resource.getCode(), resource.getId()));

        for(Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            MyResources annotation = hasValidAnnotation(handlerMethod);
            if(annotation != null){
                //与扫描的资源比较若包含则从MultiKeyMap中移除资源
                if (mkmap.containsKey(annotation.path(), annotation.method(), annotation.code())){
                    mkmap.removeMultiKey(annotation.path(), annotation.method(), annotation.code());
                }
            }
        }

        //MultiKeyMap剩下的就是无效的url，删除
        if (mkmap.size() == 0) {
            logger.info("未发现多余资源");
        } else {
            logger.warn("发现多余资源:" + mkmap.values().size() + "条");

            for (Object obj : mkmap.values()) {
                if (obj instanceof Integer) {
                    Integer id = (Integer) obj;
                    Resource resource = resourceService.queryById(id);
                    logger.warn("多余资源:" + resource.toString());
                    resourceService.deleteResourceForRole(id);
                    resourceService.deleteResourceById(id);
                }
            }
        }

    }

	MyResources hasValidAnnotation(HandlerMethod handlerMethod) {
		//获取controller中方法上的注解
		Annotation[] annotationArray = handlerMethod.getMethod().getDeclaredAnnotations();
		for (Annotation annotationResource : annotationArray) {
			if (annotationResource instanceof MyResources) {
				MyResources current = (MyResources) annotationResource;
				String name = current.name();
				String parent = current.parent();
				String code = current.code();
				ResourceType type = current.type();
				//判断是否有效
				//有资源名 并且 有父节点 视为有效注解
				//意义：必然会形成树状结构，开发人员仅需维护粗粒度的自定义节点即可
				//防止 URL 资源散落为离散的资源节点
				if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(parent) && StringUtils.isNotBlank(code) && !"URL".equals(type)) {
					return current;
				} else {
				    logger.error("资源数据异常 "+name+" "+parent+" " +code+" "+type);
					return null;
				}
			}
		}
		//获取controller类上的注解
		Annotation[] typeAnnotationArray = handlerMethod.getBeanType().getDeclaredAnnotations();
		for (Annotation annotationResource : typeAnnotationArray) {
			if (annotationResource instanceof MyResources) {
				MyResources current = (MyResources) annotationResource;
				String parent = current.parent();
				//判断是否有效
				//有父节点 即视为有效注解
				if (StringUtils.isNotBlank(parent)) {
					return current;
				} else {
					return null;
				}
			}
		}
		return null;
	}

    /**
     * 初始化菜单资源（无效菜单资源不会自动删除，需手动删除，无效URL资源会自动清除）
     */
	private void initPredefineResource(){
        //账号管理
        editResource(new Resource("账号管理",PredefineResource.ACCOUNT_MANAGEMENT_NODE_CODE, ResourceType.NODE, "", null,1));

        //项目列表
        editResource(new Resource("项目列表",PredefineResource.PROJECT_LIST_NODE_CODE, ResourceType.NODE, "", null,2));

        //消息
        editResource(new Resource("消息",PredefineResource.MESSAGE_NODE_CODE, ResourceType.NODE, "", null,3));

		//编辑项目
		editResource(new Resource("编辑项目",PredefineResource.EDIT_PROJECT_NODE_CODE, ResourceType.NODE, "", null, 4 ));
		editResource(new Resource("立项",PredefineResource.PROJECT_ESTABLISHMENT_NODE_CODE, ResourceType.NODE, "", PredefineResource.EDIT_PROJECT_NODE_CODE, 1 ));
		editResource(new Resource("项目计划",PredefineResource.PROJECT_PLAN_NODE_CODE, ResourceType.NODE, "", PredefineResource.EDIT_PROJECT_NODE_CODE,2));
		editResource(new Resource("项目验收",PredefineResource.PROJECT_ACCEPTANCE_NODE_CODE, ResourceType.NODE, "", PredefineResource.EDIT_PROJECT_NODE_CODE, 3));
		editResource(new Resource("项目结项",PredefineResource.PROJECT_CONCLUSION_NODE_CODE, ResourceType.NODE, "", PredefineResource.EDIT_PROJECT_NODE_CODE, 4));

        //数据图表
        editResource(new Resource("数据图表",PredefineResource.PROJECT_CHART_NODE_CODE, ResourceType.NODE, "", null,5));
        editResource(new Resource("看板",PredefineResource.PROJECT_CHART_BOARD_NODE_CODE, ResourceType.NODE, "", PredefineResource.PROJECT_CHART_NODE_CODE,1));
        editResource(new Resource("活跃项目",PredefineResource.PROJECT_CHART_ACTIVE_NODE_CODE, ResourceType.NODE, "", PredefineResource.PROJECT_CHART_NODE_CODE,2));
        editResource(new Resource("结项项目",PredefineResource.PROJECT_CHART_CONCLUSION_NODE_CODE, ResourceType.NODE, "", PredefineResource.PROJECT_CHART_NODE_CODE,3));

        //资源权限
        editResource(new Resource("权限资源",PredefineResource.RESOURCE_NODE_CODE, ResourceType.NODE, "", null,6));
        //看板报表
        editResource(new Resource("报表",PredefineResource.REPORT_NODE_CODE, ResourceType.NODE, "", null,6));
        editResource(new Resource("报表首页",PredefineResource.REPORT_INDEX_NODE_CODE, ResourceType.NODE, "", PredefineResource.REPORT_NODE_CODE,1));
        editResource(new Resource("PM看板",PredefineResource.REPORT_PM_BOARD_CODE, ResourceType.NODE, "", PredefineResource.REPORT_NODE_CODE,2));
        editResource(new Resource("管理员看板",PredefineResource.REPORT_ADMIN_BOARD_CODE, ResourceType.NODE, "", PredefineResource.REPORT_NODE_CODE,3));
        editResource(new Resource("项目延期看板",PredefineResource.REPORT_RISK_CODE, ResourceType.NODE, "", PredefineResource.REPORT_NODE_CODE,4));
        editResource(new Resource("项目结项信息",PredefineResource.REPORT_JUNCTIONS_CODE, ResourceType.NODE, "", PredefineResource.REPORT_NODE_CODE,5));

    }

	private void editResource(Resource resource){
		Resource res = resourceService.queryByCode(resource.getCode());
		if (res == null) {
			logger.info("增加资源 "+resource.toString());
			resourceService.addResource(resource);
		}
	}
}
