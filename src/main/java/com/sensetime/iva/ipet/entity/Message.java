package com.sensetime.iva.ipet.entity;


/**
 * @author : ChaiLongLong
 * @date : 2018/8/16 18:48
 */
public class Message extends BaseEntity{

    private Integer id;
    private boolean handle;
    private String content;
    private String send;
    private int receiverId;
    private int event;
    private String title;
    /**
     * 事件相关参数1
     */
    private int param1;
    /**
     * 事件相关参数2
     */
    private String param2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isHandle() {
        return handle;
    }

    public void setHandle(boolean handle) {
        this.handle = handle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParam1() {
        return param1;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", handle=" + handle +
                ", content='" + content + '\'' +
                ", send='" + send + '\'' +
                ", receiverId=" + receiverId +
                ", event='" + event + '\'' +
                ", title='" + title + '\'' +
                ", param1=" + param1 +
                ", param2='" + param2 + '\'' +
                "} " + super.toString();
    }
}
