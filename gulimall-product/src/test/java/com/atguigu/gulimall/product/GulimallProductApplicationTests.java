package com.atguigu.gulimall.product;





import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
class GulimallProductApplicationTests {
    @Autowired
    BrandService brandService;

    @Test
    public void testUpload()throws FileNotFoundException {
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tQhWGP5NtcMLSAjghUN";
        String accessKeySecret = "a20VqufSscT5E0dKI69hMs2FmltxC6";
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        InputStream inputStream = new FileInputStream("E:\\硅谷课堂\\课件和文档\\基础篇\\资料\\pics\\apple.png");
        ossClient.putObject("gulimall-hello303","apple.png",inputStream);
        ossClient.shutdown();
        System.out.println("上传成功");
    }
    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setBrandId(1L);
//        brandEntity.setDescript("华为1");
//        brandService.updateById(brandEntity);
//        System.out.println("保存成功");

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        list.forEach((item) ->{
            System.out.println(item);
        });
    }
    @Test
    public void test(){
        String s1 = "http://localhost:88/api/product/category/list/tree";
        String s2 = "http://localhost:88/api/product/category/list/tree";
        System.out.println(s1==s2);
    }
}
