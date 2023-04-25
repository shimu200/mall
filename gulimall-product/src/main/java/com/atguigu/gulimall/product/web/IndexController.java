package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import org.redisson.RedissonWriteLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    RedissonClient redisson;
    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        //TODO 1.查出所有的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categorys",categoryEntities);
        return "index";
    }
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson(){
        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();
        return catalogJson;
    }
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        //1. 获取一把锁
        RLock lock = redisson.getLock("my-lock");
        lock.lock(10, TimeUnit.SECONDS);
        //2. 加锁,默认加30s
//        lock.lock(); //阻塞式等待
        //1)锁的自动续期,如果运行时间超长,运行期间自动给锁续上新的30s
        //2)加锁的业务只要运行完成,就不会给当前续期,即使不删锁,30s后自动删锁
//        lock.lock(10, TimeUnit.SECONDS); //10s自动解锁要大于业务时间
        //如果设置了锁的超过时间,就会方式redis脚本,进行占锁,默认超时就是指定的时间
        //如果未指定锁的超过时间,就会使用30*1000
        // 只要占锁成功,就会启动一个定时任务
        //internalLockLeaseTime/3 ,10s

        // 最佳实战
        //1). lock.lock(10, TimeUnit.SECONDS); 省掉了整个续期操作,手动解锁
        try {
            System.out.println("加锁成功,执行业务......"+Thread.currentThread().getId());
            Thread.sleep(30000);
        } catch (Exception e) {

        }finally {
            System.out.println("释放锁....");
            lock.unlock();
        }
        return "hello";
    }

    //保证一定能读到最新数据,修改期间,写锁是一个互斥锁。读锁是一个共享锁
    //读 + 读 相当于无锁,并发读只会在redis中记录好,当前的读锁。他们都会同时加锁成功
    //写 + 读 等待写锁释放
    //写 + 写 阻塞方式
    //读 + 写 有读锁,写锁也必须等待
    @GetMapping("/write")
    @ResponseBody
    public String writeValue(){
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        RLock rLock = lock.writeLock();
        try {
            //1. 改数据加写锁,读数据加读锁
            rLock.lock();
            System.out.println("写锁加锁成功..."+Thread.currentThread().getId());
            s = UUID.randomUUID().toString();
            Thread.sleep(30000);
            redisTemplate.opsForValue().set("writeValue", s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
            System.out.println("写锁释放..."+Thread.currentThread().getId());
        }
        return  s;
    }

    @GetMapping("/read")
    @ResponseBody
    public String readValue(){
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        //加读锁
        RLock rLock = lock.readLock();
        rLock.lock();
        try {
            System.out.println("读锁加锁成功..."+Thread.currentThread().getId());
            Thread.sleep(30000);
            s = (String) redisTemplate.opsForValue().get("writeValue");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
            System.out.println("读锁释放..."+Thread.currentThread().getId());
        }
        return  s;
    }
}
