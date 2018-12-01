package com.sensetime.iva.ipet.entity;

/**
 * @Author: ChaiLongLong
 *
 * 默认一个存在一个name为  全部区域   的数据存在
 */
public class AreaEntity extends BaseEntity{

    private static final long serialVersionUID = 5009422647894521462L;

    private int id;
    private String name;
    private String note;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "AreaEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                "} " + super.toString();
    }
}
