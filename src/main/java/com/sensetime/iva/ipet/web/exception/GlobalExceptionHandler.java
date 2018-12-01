package com.sensetime.iva.ipet.web.exception;

import com.sensetime.iva.ipet.common.GlobalCode;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.entity.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/4 15:04
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public com.sensetime.iva.ipet.vo.response.ResponseBody jsonErrorHandler(HttpServletRequest req, Exception e) {

        logger.error("捕获异常",e);
        com.sensetime.iva.ipet.vo.response.ResponseBody responseBody;
        String exceptionMessage = ExceptionUtils.getRootCauseMessage(e);
        Object bestMatchingPattern = req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String method = req.getMethod().toUpperCase();
        Map<String,Resource> allResourcesAndRolesMap = (Map<String,Resource>) redisTemplate.boundValueOps(IConstant.ALL_RESOURCES_AND_ROLES).get();
        Resource resource = allResourcesAndRolesMap.get(bestMatchingPattern.toString()+"-"+method);

        //当导致异常的请求不在权限资源范围内，则进行基本错误响应
        if(resource == null){
            // 在这里判断异常，根据不同的异常返回错误。
            if (e.getClass().equals(DataAccessException.class)) {
                e.printStackTrace();
                responseBody = new com.sensetime.iva.ipet.vo.response.ResponseBody(GlobalCode.DATA_ACCESS_EXCEPTION,"数据库操作失败!",exceptionMessage);
            } else if (e.getClass().toString()
                    .equals(NullPointerException.class.toString())) {
                e.printStackTrace();
                responseBody = new com.sensetime.iva.ipet.vo.response.ResponseBody(GlobalCode.NULL_POINTER_EXCEPTION,"调用非法对象!",exceptionMessage);
            } else if (e.getClass().equals(IOException.class)) {
                e.printStackTrace();
                responseBody = new com.sensetime.iva.ipet.vo.response.ResponseBody(GlobalCode.IO_EXCEPTION,"文件处理异常!",exceptionMessage);
            } else if (e.getClass().equals(IllegalArgumentException.class)) {
                e.printStackTrace();
                responseBody = new com.sensetime.iva.ipet.vo.response.ResponseBody(GlobalCode.ILLEGAL_ARGUMENT_EXCEPTION,"参数错误!",exceptionMessage);
            } else if (e.getClass().equals(SecurityException.class)) {
                e.printStackTrace();
                responseBody = new com.sensetime.iva.ipet.vo.response.ResponseBody(GlobalCode.SECURITY_EXCEPTION,"违背安全原则!",exceptionMessage);
            } else if (e.getClass().equals(SQLException.class)) {
                e.printStackTrace();
                responseBody = new com.sensetime.iva.ipet.vo.response.ResponseBody(GlobalCode.SQL_EXCEPTION,"操作数据库异常!",exceptionMessage);
            } else if (e instanceof SocketTimeoutException
                    || e instanceof ConnectException) {
                e.printStackTrace();
                responseBody = new com.sensetime.iva.ipet.vo.response.ResponseBody(GlobalCode.CONNECTION_EXCEPTION,"网络异常!",exceptionMessage);
            } else {
                e.printStackTrace();
                responseBody = new com.sensetime.iva.ipet.vo.response.ResponseBody(GlobalCode.ERROR,"系统内部错误，操作失败!",exceptionMessage);
            }
        }else {
            responseBody = new com.sensetime.iva.ipet.vo.response.ResponseBody(GlobalCode.ERROR,resource.getName()+"失败",exceptionMessage);
        }

        return responseBody;
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public com.sensetime.iva.ipet.vo.response.ResponseBody jsonErrorHandler(HttpServletRequest req, BusinessException e)  {
        logger.error("捕获异常",e);
        Object bestMatchingPattern = req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String method = req.getMethod().toUpperCase();
        Map<String,Resource> allResourcesAndRolesMap = (Map<String,Resource>) redisTemplate.boundValueOps(IConstant.ALL_RESOURCES_AND_ROLES).get();
        Resource resource = allResourcesAndRolesMap.get(bestMatchingPattern.toString()+"-"+method);

        com.sensetime.iva.ipet.vo.response.ResponseBody responseBody = new com.sensetime.iva.ipet.vo.response.ResponseBody(GlobalCode.ERROR,resource.getName()+"失败",e.getMessage() );
        return responseBody;
    }


}
