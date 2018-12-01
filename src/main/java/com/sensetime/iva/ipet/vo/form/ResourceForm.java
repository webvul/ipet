package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/9/3 10:14
 */
public class ResourceForm {

    @ApiModelProperty(value="角色id")
    private Integer roleId;
    @ApiModelProperty(value="资源id集合，为空时默认为清空角色权限")
    private List<Integer> resources;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getResources() {
        return resources;
    }

    public void setResources(List<Integer> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "ResourceForm{" +
                "roleId=" + roleId +
                ", resources=" + resources +
                '}';
    }
}
