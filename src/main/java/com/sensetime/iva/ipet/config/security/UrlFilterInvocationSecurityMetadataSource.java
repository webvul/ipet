package com.sensetime.iva.ipet.config.security;

import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.entity.Resource;
import com.sensetime.iva.ipet.entity.RoleEntity;
import com.sensetime.iva.ipet.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author : ChaiLongLong
 * @date : 2018/7/31 15:51
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger logger = LoggerFactory.getLogger(UrlFilterInvocationSecurityMetadataSource.class);

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ResourceService resourceService;
    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Value("${isOpenSecurity}")
    private boolean isOpenSecurity;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if(isOpenSecurity){
            FilterInvocation filterInvocation = ((FilterInvocation) o);
            Collection<ConfigAttribute> requiredRoles = getRequiredRoles(filterInvocation);
            logger.debug("requiredRoles:"+requiredRoles);
            if(requiredRoles.size() == 0){
                return SecurityConfig.createList("ROLE_NOT_MATCH");
            }
            return requiredRoles;
        }else{
            return SecurityConfig.createList("ROLE_NOT_MATCH");
        }
    }

    /**
     * 根据请求信息，获得所需角色权限集合
     */
    @SuppressWarnings("unchecked")
    private Collection<ConfigAttribute> getRequiredRoles(FilterInvocation filterInvocation){
        logger.debug("getRequiredRoles() run...");
        String requestUrl = filterInvocation.getRequestUrl();
        String method = filterInvocation.getRequest().getMethod().toUpperCase();
        logger.debug("request url:" + requestUrl+", method:"+method);

        //获得请求对象
        HttpServletRequest request = filterInvocation.getRequest();
        try {
            //根据 请求，初始化 bestMatchingPattern
            requestMappingHandlerMapping.getHandler(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //从请求中获得 bestMatchingPattern
        Object bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        //初始化 角色代码集合
        Collection<ConfigAttribute> configAttributes = new HashSet<>();
        if(bestMatchingPattern != null){
            String strBestMatchingPattern = bestMatchingPattern.toString();
            //如果有 bestMatchingPattern，且不为空
            if(StringUtils.isNotBlank(strBestMatchingPattern)){

                Map<String,Resource> allResourcesAndRolesMap = (Map<String,Resource>) redisTemplate.boundValueOps(IConstant.ALL_RESOURCES_AND_ROLES).get();
                Resource resource = allResourcesAndRolesMap.get(strBestMatchingPattern+"-"+method);
                logger.info("request resource "+resource.toString());
                if(resource != null && resource.getRoles()!= null &&resource.getRoles().size()>0){
                    for (RoleEntity role: resource.getRoles()) {
                        ConfigAttribute configAttribute = new SecurityConfig(role.getRoleName());
                        configAttributes.add(configAttribute);
                    }
                }

//                List<Resource> allResource = resourceService.queryResourceAndRolesByPathAndMethod(strBestMatchingPattern, method);
//                if (allResource.size() == 1){
//                    for (RoleEntity role: allResource.get(0).getRoles()) {
//                        ConfigAttribute configAttribute = new SecurityConfig(role.getRoleName());
//                        configAttributes.add(configAttribute);
//                    }
//                }else if(allResource.size() > 1){
//                    logger.warn("发现"+allResource.size()+" 条资源重复");
//                    for (Resource resource : allResource) {
//                        logger.warn("重复资源"+resource.toString());
//                        for (RoleEntity role: resource.getRoles()) {
//                            ConfigAttribute configAttribute = new SecurityConfig(role.getRoleName());
//                            configAttributes.add(configAttribute);
//                        }
//                    }
//                }
            }
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
