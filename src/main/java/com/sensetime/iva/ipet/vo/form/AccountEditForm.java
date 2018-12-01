package com.sensetime.iva.ipet.vo.form;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/9 16:49
 */
public class AccountEditForm {

    private Integer id;
    @ApiModelProperty(value="账号名")
    private String username;
    private String password;
    @ApiModelProperty(value="用户名")
    private String name;
    @ApiModelProperty(value="邮箱")
    private String email;
    @ApiModelProperty(value="用户类型： 1：普通用户，2：LDAP账户")
    private int type;
    @ApiModelProperty(value = "区域id")
    private int areaId;
    private List<RoleForm> roles;
    @ApiModelProperty(value = "角色名称")
    private boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public List<RoleForm> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleForm> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "AccountEditForm{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", areaId=" + areaId +
                ", roles=" + roles +
                ", enabled=" + enabled +
                '}';
    }
}
