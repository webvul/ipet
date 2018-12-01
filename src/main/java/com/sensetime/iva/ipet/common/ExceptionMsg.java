package com.sensetime.iva.ipet.common;

/**
 * @author  gongchao
 */
public class ExceptionMsg {
    /**错误信息*/
    public static final String EXCEL_TYPE_ERROR = "模板格式不对";
    public static final String EXCEL_LOAD_OVER_MAX = "文件上传最大不能超过";
    public static final String EXCEL_EMPTY_OR_TYPE_ERROR = "上传文件为空或不是xls或xlsx格式";
    public static final String EXCEL_ANALYSIS_ERROR = "上传的文件标题与解析设置的标题冲突";
    public static final String SYSTEM_NOT_FOUND = "表格中业务系统对应的名称没有找到";
    public static final String SYSTEM_PLATFORM_NOT_FOUND = "对应的平台名称没有找到";
    public static final String STEP_NOT_FOUND = "项目阶段对应名称没有找到";
    public static final String TYPE_NOT_FOUND = "项目类型对应名称没有找到";
    public static final String EMPTY_PROJECT = "项目信息为空";
    public static final String EXCEL_GET_SHEET_ERROR = "的sheet不存在";


    public static final String LEVEL_NOT_FOUND = "项目级别对应名称没有找到";
    public static final String PRODUCT_REQUIRE_NOT_FOUND = "产品要求对应名称没有找到";
    public static final String PRODUCT_CUSTOMIZATION_NOT_FOUND = "产品要求对应名称没有找到";
    public static final String LOAD_OVER_MAX = "当前项目上传文件数量已经最大";
    public static final String DELETE_FILE_NOT_FOUND = "文件不存在或已经被删除";
    public static final String NOT_IMAGE = "上传的图片格式不符合,仅支持:jpg|png|JPG|PNG|jpeg|bmp|gif";
}
