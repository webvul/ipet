package com.sensetime.iva.ipet.vo.form;

import com.sensetime.iva.ipet.entity.AreaEntity;
import com.sensetime.iva.ipet.entity.Resource;
import com.sensetime.iva.ipet.entity.RoleEntity;
import com.sensetime.iva.ipet.entity.TreeNode;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: ChaiLongLong
 * @date : 2018/8/9 15:34
 */
public class AccountForm {
    private int id;
    private String username;
    private String name;
    private String email;
    private AreaEntity area;
    private List<RoleEntity> roles;
    private List<TreeNode> resources;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public List<TreeNode> getResources() {
        return resources;
    }

    public void setResources(List<TreeNode> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "AccountForm{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", area=" + area +
                ", roles=" + roles +
                ", resources=" + resources +
                '}';
    }
}
