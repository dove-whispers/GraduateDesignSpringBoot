package com.dove.util;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 测试
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
public class MyTest {
    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
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

    public static String multipleInvoice(String filePath, String ak, String sk) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/multiple_invoice";
        try {
            // 本地文件路径
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, StandardCharsets.UTF_8);
            String param = "image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth(ak, sk);
            return HttpUtil.post(url, accessToken, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        String ak = "mwTV73Dv0iqysmNw0nWdHQ98";
//        String sk = "tL98K2pEYFCqu9PAxgPI1dthwj8LjLkO";
        String jsonString = "{\"words_result\":[{\"result\":{\"AmountInWords\":[{\"word\":\"壹仟壹佰叁拾圆整\"}],\"InvoiceNumConfirm\":[{\"word\":\"01574706\"}],\"CommodityEndDate\":[],\"CommodityStartDate\":[],\"CommodityVehicleType\":[],\"CommodityPrice\":[{\"row\":\"1\",\"word\":\"1000\"}],\"InvoiceTag\":[{\"word\":\"其他\"}],\"NoteDrawer\":[{\"word\":\"段金凤\"}],\"SellerAddress\":[{\"word\":\"包头市青山区兴胜镇顶独龙贵村平安路一号1号院13904726266\"}],\"CommodityNum\":[{\"row\":\"1\",\"word\":\"1\"}],\"SellerRegisterNum\":[{\"word\":\"91150204329040147F\"}],\"MachineCode\":[{\"word\":\"499905440840\"}],\"Remarks\":[],\"SellerBank\":[{\"word\":\"交通银行包头青山支行152001410018010078631\"}],\"CommodityTaxRate\":[{\"row\":\"1\",\"word\":\"13%\"}],\"ServiceType\":[{\"word\":\"电器设备\"}],\"TotalTax\":[{\"word\":\"130.00\"}],\"InvoiceCodeConfirm\":[{\"word\":\"1500184130\"}],\"CheckCode\":[],\"InvoiceCode\":[{\"word\":\"1500184130\"}],\"InvoiceDate\":[{\"word\":\"2019年04月01日\"}],\"PurchaserRegisterNum\":[{\"word\":\"91150291MA0NCNH475\"}],\"InvoiceTypeOrg\":[{\"word\":\"内蒙古增值税专用发票\"}],\"Password\":[{\"word\":\"0369883<-*467-31420-*27/-021*05987><23</-3///40>7+0<9>>00<>16232+14-19<9505>42/+36825-458<4/90013+1303/1*97379-<\"}],\"OnlinePay\":[],\"Agent\":[{\"word\":\"否\"}],\"AmountInFiguers\":[{\"word\":\"1130.00\"}],\"PurchaserBank\":[{\"word\":\"中国农业银行股份有限公司包头燕赵支行05633401040002102\"}],\"Checker\":[{\"word\":\"王耀霞\"}],\"City\":[],\"TotalAmount\":[{\"word\":\"1000.00\"}],\"CommodityAmount\":[{\"row\":\"1\",\"word\":\"1000.00\"}],\"PurchaserName\":[{\"word\":\"内蒙古星元建设监理有限公司包头分公司\"}],\"CommodityType\":[],\"Province\":[{\"word\":\"内蒙古自治区\"}],\"InvoiceType\":[{\"word\":\"专用发票\"}],\"SheetNum\":[{\"word\":\"第一联：记账联\"}],\"PurchaserAddress\":[{\"word\":\"内蒙古包头锦土高新区清河新区大学科技园区同德创业楼404-415326925775\"}],\"CommodityTax\":[{\"row\":\"1\",\"word\":\"130.00\"}],\"CommodityPlateNum\":[],\"CommodityUnit\":[{\"row\":\"1\",\"word\":\"个\"}],\"Payee\":[{\"word\":\"周蒙财\"}],\"CommodityName\":[{\"row\":\"1\",\"word\":\"*交通运输设备*刹车片\"}],\"SellerName\":[{\"word\":\"包头市盛世嘉阳汽车服务有限公司\"}],\"InvoiceNum\":[{\"word\":\"01574706\"}]},\"top\":4,\"left\":9,\"probability\":0.9999856949,\"width\":1052,\"type\":\"vat_invoice\",\"height\":615}],\"words_result_num\":1,\"log_id\":1631217748717711758}";
        String word = JSONUtil.parseObj(jsonString).getByPath("words_result.result.AmountInWords.word", JSONArray.class).get(0, JSONArray.class).get(0, String.class);
        System.out.println(word);
//        System.out.println(getAuth(ak, sk));
//        String filePath = "C:\\Users\\cjm\\Desktop\\增值税发票1.jpeg";
//        System.out.println(multipleInvoice(filePath,ak,sk));
    }
}
