package com.atguigu.guliamll.thirdparty;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;

import com.atguigu.guliamll.thirdparty.component.SmsComponent;
import com.atguigu.guliamll.thirdparty.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@SpringBootTest

class GulimallThirdPartyApplicationTests {
    @Autowired
    SmsComponent smsComponent;

    @Resource
    OSSClient ossClient;
    @Test
    public  void testSend2(){
//        smsComponent.sendSmsCode("18674187828","55556");
        String code[] = new String[10];
        for (int i = 0; i < 10; i++) {
            code[i] = UUID.randomUUID().toString().replaceAll("[^0-9]","").substring(0, 6);
            System.out.println(code[i]);
        }
    }
    public static void main(String[] args) {
        String host = "https://jmsms.market.alicloudapi.com";
        String path = "/sms/send";
        String method = "POST";
        String appcode = "2a6b28e506f84982b8d842620300eeb9";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", "18674187828");
        querys.put("templateId", "JM1000372");
        querys.put("value", "5555");
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
    @Test
    public void testUpload()throws FileNotFoundException {
//        String endpoint = "oss-cn-beijing.aliyuncs.com";
//        String accessKeyId = "LTAI5tQhWGP5NtcMLSAjghUN";
//        String accessKeySecret = "a20VqufSscT5E0dKI69hMs2FmltxC6";
//        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        InputStream inputStream = new FileInputStream("E:\\硅谷课堂\\课件和文档\\基础篇\\资料\\pics\\apple.png");
        ossClient.putObject("gulimall-hello303","apple1.png",inputStream);
        ossClient.shutdown();
        System.out.println("上传成功");
    }

}
