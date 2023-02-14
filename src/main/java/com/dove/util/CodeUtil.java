package com.dove.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码工具类
 *
 * @author dove_whispers
 * @date 2023-02-12
 */
public class CodeUtil {

    /**
     * 检查验证代码
     *
     * @param request          通过request获取实际验证码
     * @param verifyCodeActual 用户输入验证代码
     * @return boolean
     */
    public static boolean checkVerifyCode(HttpServletRequest request, String verifyCodeActual) {
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        return verifyCodeExpected.equalsIgnoreCase(verifyCodeActual);
    }
}
