package com.sensetime.iva.ipet.util;

import com.sensetime.iva.ipet.common.IConstant;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: ChaiLongLong
 * @date : 2018/7/31 17:48
 */
public class Md5Util {

    public static String encode(String password) {
        String result = "";
        try {
            result = encoderByMd5(encoderByMd5(password)+IConstant.SALT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;

    }

    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException 不支持编码
     */
    private static String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(md5.digest(str.getBytes("utf-8")));
    }

    public static void main(String[] args) {

        System.out.println(encode("admin"));
    }

}
