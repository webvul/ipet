package com.sensetime.iva.ipet.common;

/**
 * @author yore
 */

public enum Code {

    /**
     * 请求成功
     */
    SUCCESS(GlobalCode.SUCCESS_CODE,"success"),
    /**
     * 通用error提示
     */
    ERROR(GlobalCode.ERROR,"error"),
    /**
     * 未登录
     */
    NOT_LOGIN(GlobalCode.NOT_LOGIN,"not login"),
    /**
     * 登陆失败
     */
    LOGIN_FAIL(GlobalCode.LOGIN_FAIL,"login fail"),
    /**
     *  未发现相关资源
     */
    UNAUTHORIZED(GlobalCode.UNAUTHORIZED,"not authorized"),
    /**
     * 请求参数异常
     */
    PARAM_ERROR(GlobalCode.PARAM_ERROR,"param error");


    private Integer code;
    private String msg;

    Code(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Code{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}

