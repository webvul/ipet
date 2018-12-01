package com.sensetime.iva.ipet.web.exception;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/4 16:25
 * 自定义异常 用于业务中抛出既定异常
 */
public class BusinessException extends Exception {

    public BusinessException(String message) {
        super(message);
    }
}
