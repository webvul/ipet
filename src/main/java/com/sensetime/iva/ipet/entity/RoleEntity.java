package com.sensetime.iva.ipet.entity;

/**
 * @Author: ChaiLongLong
 */
public class RoleEntity extends BaseEntity {

    private static final long serialVersionUID = -5686098960651253743L;

    private int id;
    private String name;
    private String roleName;

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }
}
