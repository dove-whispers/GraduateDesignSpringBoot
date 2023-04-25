package com.dove.util;

import cn.hutool.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 图片识别工具类
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
public class IdentifyUtil {
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                + "grant_type=client_credentials"
                + "&client_id=" + ak
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            return new JSONObject(result.toString()).getStr("access_token");
        } catch (Exception e) {
            System.err.print("获取token失败!");
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static String identifyInvoice(String base64, String ak, String sk) {
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/multiple_invoice";
        try {
            String imgParam = URLEncoder.encode(base64, StandardCharsets.UTF_8);
            String param = "image=" + imgParam;
            String accessToken = getAuth(ak, sk);
            return HttpUtil.post(url, accessToken, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
