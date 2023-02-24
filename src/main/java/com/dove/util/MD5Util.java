package com.dove.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密工具
 *
 * @author dove_whispers
 * @date 2023-02-24
 */
public class MD5Util {
    public static String getMD5(String origin) {
        //自定义数组,相当于盐
        char[] hexArray = {
                '5', 'a', '4', 'b', '9', '6', '8', 'f', 'e', '2', '2', '7', 'c', 'd', 'a', '5'
        };
        try {
            byte[] originBytes = origin.getBytes(StandardCharsets.UTF_8);
            //确定md5加密算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(originBytes);
            //加密后的数组
            byte[] digest = md.digest();
            //定义返加盐后的数组
            char[] str = new char[digest.length * 2];
            int k = 0;
            //对加密后的数组加盐
            //首先判断加密后的数组长度,遍历数组,对每个元素进行移位运算(二进制的位运算)
            for (byte b : digest) {
                str[k++] = hexArray[b >>> 4 & 0xf];
                str[k++] = hexArray[b & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
