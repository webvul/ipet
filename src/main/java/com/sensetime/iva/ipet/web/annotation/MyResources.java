package com.sensetime.iva.ipet.web.annotation;

import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;

import java.lang.annotation.*;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/24 12:43
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface MyResources {

    /**
     * 资源名
     * 可读性要强，格式要规范，统一
     */
    String name() default "";

    /**
     * 资源代号
     * 具有唯一约束，不要和已有的任何资源代号发生冲突
     */
    String code() default "";

    /**
     * 资源类型
     * @return
     */
    ResourceType type();

    /**
     * 资源地址
     * @return
     */
    String path() default "";


    /**
     * 从属的模块 code（父资源 code）- 必须提供
     * 有利于形成树状结构
     */
    String parent();


    /**
     * 排序
     */
    int seq() default 0;

    /**
     * 请求
     */
    RequestMethod method();

    /**
     * 用于手动初始化权限，后期前端动态设置权限后清除
     * 分配给多个角色以逗号分隔
     */
    String role() default "";

}
