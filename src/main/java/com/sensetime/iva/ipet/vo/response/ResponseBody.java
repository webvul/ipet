package com.sensetime.iva.ipet.vo.response;

import java.io.Serializable;

/**
 * @author yore
 */
public class ResponseBody<T> implements Serializable {


    private Integer code;
    private String msg;
    private String desc;
    private T data;

    public ResponseBody(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseBody(Integer code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public ResponseBody(Integer code, String msg, String desc, T data) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{\"code\":" + code +
                ", \"msg\":\"" + msg + '\"' +
                ", \"desc\":\"" + desc + '\"' +
                ", \"data\":" + data +
                '}';
    }
}
