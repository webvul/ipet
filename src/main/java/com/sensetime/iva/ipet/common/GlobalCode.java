package com.sensetime.iva.ipet.common;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/4 17:40
 * 全局返回码
 * 有通用的可复用，无通用的按照规则添加
 */
public class GlobalCode {

    /** 成功 */
    public static final int SUCCESS_CODE = 200;
    /** 通用错误码 */
    public static final int ERROR = 10001;
    /** 未登录 */
    public static final int NOT_LOGIN = 10002;
    /** 登陆失败 */
    public static final int LOGIN_FAIL = 10003;
    /** 未授权、权限不足 */
    public static final int UNAUTHORIZED = 10004;
    /** 参数异常 */
    public static final int PARAM_ERROR = 10005;
    /** 数据库操作失败 */
    public static final int DATA_ACCESS_EXCEPTION = 10006;
    /** 空指针异常 */
    public static final int NULL_POINTER_EXCEPTION = 10007;
    /** IO异常 */
    public static final int IO_EXCEPTION = 10008;
    /** 非法参数异常异常 */
    public static final int ILLEGAL_ARGUMENT_EXCEPTION = 10009;
    /** security安全策略异常 */
    public static final int SECURITY_EXCEPTION = 10010;
    /** 数据库操作异常 */
    public static final int SQL_EXCEPTION = 10011;
    /** socket 或 connection异常 */
    public static final int CONNECTION_EXCEPTION = 10012;



}
