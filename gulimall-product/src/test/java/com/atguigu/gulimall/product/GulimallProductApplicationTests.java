package com.atguigu.gulimall.product;





import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.dao.SkuSaleAttrValueDao;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.atguigu.gulimall.product.vo.SkuItemSaleAttrVo;
import com.atguigu.gulimall.product.vo.SpuItemAttrGroupVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class GulimallProductApplicationTests {
    @Autowired
    BrandService brandService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    SkuSaleAttrValueDao skuSaleAttrValueDao;
    @Test
    public void test3(){
        List<SkuItemSaleAttrVo> attrGroupVos = skuSaleAttrValueDao.getSaleAttrsBySpuId(2L);
        System.out.println(attrGroupVos);
    }
    @Test
    public void test2(){
        List<SpuItemAttrGroupVo> attrGroupVos = attrGroupDao.getAttrGroupWithAttrsBySpuId(1L,225L);
        System.out.println(attrGroupVos);
    }
    @Test
    public void redisson(){
        System.out.println(redissonClient);
    }
    @Test
    public void test1(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //保存
        ops.set("hello","world"+ UUID.randomUUID().toString());
        //查询
        String hello = ops.get("hello");
        System.out.println("之前保存的数据"+hello);
    }
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
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("hello", "world"+UUID.randomUUID().toString());
        ops.set("hello2", "world"+UUID.randomUUID().toString());
        ops.set("hello3", "world"+UUID.randomUUID().toString());
        System.out.println(ops.get("hello"));
        System.out.println(ops.get("hello2"));
        System.out.println(ops.get("hello3"));
    }
}
