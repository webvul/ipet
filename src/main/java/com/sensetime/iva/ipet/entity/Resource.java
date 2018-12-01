package com.sensetime.iva.ipet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @Author: ChaiLongLong
 * @date : 2018/7/31 15:51
 *
 * resource作为资源类  目前规划为3层子菜单
 * 所有--
 *      |----菜单1
 *              |--子菜单1
 *              |--子菜单2
 *      |----菜单2
 *      |----菜单3
 *              |--子菜单1
 *              |--子菜单2
 *
 *
 * type类型包含FOLDER（代表左侧菜单栏） URL（代表frame中具体按钮请求）
 * type作用  可根据FOLDER设置权限 不同用户左侧菜单栏不同
 *           可根据URL设置权限 不同用户在同一个菜单frame页面中操作权限不同（只读/编辑）
 * icon作用  可用于动态加载菜单栏时显示菜单图标
 * seq作用   可用于动态加载菜单栏时菜单排序
 * show作用  可用于禁用模块
 *
 * resource_role  资源用户映射表中   role只持有 type= URL  的资源   不持有FLODER资源
 *
 */
public class Resource implements Serializable {

    private int id;
    /**
     * 资源名
     */
    private String name;
    /**
     * 资源代码
     */
    private String code;
    /**
     * 资源类型
     */
    private ResourceType type;
    /**
     * 资源值
     */
    private String path;
    /**
     * 父资源
     */
    private String parent;
    /**
     * 子资源
     */
    private List<Resource> children;
    /**
     * 资源资源所属角色
     */
    @ApiModelProperty(hidden = true)
    private List<RoleEntity> roles;
    /**
     * 用于排序的序号
     */
    private int seq;
    /**
     * 资源请求类型
     */
    private RequestMethod method;

    public Resource() {
    }

    public Resource(String name, String code, ResourceType type, String path, String parent, int seq, RequestMethod method) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.path = path;
        this.parent = parent;
        this.seq = seq;
        this.method = method;
    }

    public Resource(String name, String code, ResourceType type, String path, String parent, int seq) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.path = path;
        this.parent = parent;
        this.seq = seq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonIgnore
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }

//    @JsonIgnore

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resource resource = (Resource) o;
        return seq == resource.seq &&
                Objects.equals(name, resource.name) &&
                Objects.equals(code, resource.code) &&
                type == resource.type &&
                Objects.equals(path, resource.path) &&
                Objects.equals(parent, resource.parent) &&
                method == resource.method;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, code, type, path, parent, children, roles, seq, method);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", path='" + path + '\'' +
                ", parent='" + parent + '\'' +
                ", children=" + children +
                ", roles=" + roles +
                ", seq=" + seq +
                ", method=" + method +
                '}';
    }
}
