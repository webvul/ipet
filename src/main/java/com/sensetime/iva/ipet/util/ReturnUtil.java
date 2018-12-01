package com.sensetime.iva.ipet.util;

import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.vo.response.ResponseBody;

/**
 * TODO 待优化
 * @author yore
 */
public class ReturnUtil {

    public static <T> ResponseBody success(String desc, T data) {
        return new ResponseBody(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(), desc, data);
    }

    public static ResponseBody success() {
        return new ResponseBody(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(), "", null);
    }

    public static ResponseBody success(String desc) {
        return new ResponseBody(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(), desc, null);
    }

    public static <T> ResponseBody error(Code code, String desc, T data) {
        return new ResponseBody(code.getCode(), code.getMsg(), desc, data);
    }

    public static ResponseBody error(Code code, String desc) {
        return new ResponseBody(code.getCode(), code.getMsg(), desc, null);
    }

}
