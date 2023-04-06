package com.atguigu.guliamll.thirdparty;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest

class GulimallThirdPartyApplicationTests {

    @Test
    void contextLoads() {
    }
    @Resource
    OSSClient ossClient;
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
