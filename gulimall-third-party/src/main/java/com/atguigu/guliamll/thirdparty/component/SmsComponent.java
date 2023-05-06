package com.atguigu.guliamll.thirdparty.component;

import com.atguigu.guliamll.thirdparty.utils.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import java.io.UnsupportedEncodingException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
@ConfigurationProperties(prefix = "spring.cloud.alicloud.sms")
@Data
@Component
public class SmsComponent {
    private String secretId;
    private String secretKey;
    private String templateId;
//    public static String calcAuthorization(String source, String secretId, String secretKey, String datetime)
//            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
//        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
//        Mac mac = Mac.getInstance("HmacSHA1");
//        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
//        mac.init(sKey);
//        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));
//        String sig = new BASE64Encoder().encode(hash);
//
//        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
//        return auth;
//    }

//    public static String urlencode(Map<?, ?> map) throws UnsupportedEncodingException {
//        StringBuilder sb = new StringBuilder();
//        for (Map.Entry<?, ?> entry : map.entrySet()) {
//            if (sb.length() > 0) {
//                sb.append("&");
//            }
//            sb.append(String.format("%s=%s",
//                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
//                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
//            ));
//        }
//        return sb.toString();
//    }
    public void sendSmsCode(String phone,String code){
        String host = "https://jmsms.market.alicloudapi.com";
        String path = "/sms/send";
        String method = "POST";
        String appcode = "2a6b28e506f84982b8d842620300eeb9";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("templateId", "JM1000372");
        querys.put("value", code);
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

