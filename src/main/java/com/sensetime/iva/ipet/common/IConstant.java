package com.sensetime.iva.ipet.common;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/3 22:35
 */
public class IConstant {

    /**
     * 管理员role_name
     */
    public static final String ROLE_MANAGER="ROLE_manager";
    /**
     * 项目经理role_pm
     */
    public static final String ROLE_PM = "ROLE_pm";

    /**
     * redis中存储所有的资源以及资源对应的角色 的 key
     */
    public static final String ALL_RESOURCES_AND_ROLES = "all_resources_and_roles";
    /**
     * 普通用户密码加盐
     */
    public static final String SALT = "senseTime";
    /**
     * 权限校验开关
     */
    public static final boolean AUTHENTICATE = true;
    /**
     * 未登录 principal 值
     */
    public static final String UNLOGINPRINCIPAL = "anonymousUser";

    public static final String LOGINSIGN = "/login_p";

    public static final int LDAPACCOUNTTYPE = 2;

    /**
     * 消息类型
     */
    /**结项申请*/
    public static final int PROJECT_CONCLUSION_REPLY=1;
    public static final String PROJECT_CONCLUSION_REPLY_TITLE="结项申请";
    /**项目审批结果*/
    public static final int PROJECT_CONCLUSION_RESULT_ADOPT=2;
    public static final String PROJECT_CONCLUSION_RESULT_ADOPT_TITLE ="通过--结项申请";
    public static final int PROJECT_CONCLUSION_RESULT_REPULSE=3;
    public static final String PROJECT_CONCLUSION_RESULT_REPULSE_TITLE ="打回--结项申请";
    /**项目激活*/
    public static final int PROJECT_CONCLUSION_ACTIVE=4;
    public static final String PROJECT_CONCLUSION_ACTIVE_TITLE ="激活项目";
    /**刷新消息事件*/
    public static final int REFRESH_MESSAGE=5;
    public static final String REFRESH_MESSAGE_TITLE ="刷新未读消息";

    /**
     * 权限资源
     */
    public static final String PROJECT_CONCLUSION_APPROVAL_RESOURCE_CODE ="approval_conclusion";

    /**
     * 登陆记录类型
     */
    public static final int LOGIN_INFO_TYPE_LOGIN = 1;
    public static final int LOGIN_INFO_TYPE_REMEMBER_ME = 2;


}
