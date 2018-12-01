package com.sensetime.iva.ipet.config.dozer;

import java.util.List;
import java.util.Set;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/7 15:39
 */
public interface IGenerator {

    /**
     *  单个对象的深度复制及类型转换，vo/domain , po
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return 转换后的数据
     */
    <T, S> T convert(S s, Class<T> clz);

    /**
     *  list深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return 转换后的数据
     */
    <T, S> List<T> convert(List<S> s, Class<T> clz);

    /**
     * set深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return 转换后的数据
     */
    <T, S> Set<T> convert(Set<S> s, Class<T> clz);

    /**
     *  数组深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return 转换后的数据
     */
    <T, S> T[] convert(S[] s, Class<T> clz);
}
