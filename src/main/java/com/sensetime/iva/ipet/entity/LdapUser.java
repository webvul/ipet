package com.sensetime.iva.ipet.entity;

/**
 * @author yore
 */
public class LdapUser {

    /**
     * 名称
     */
    private String name;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 展现名
     */
    private String displayName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "LdapUser{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
