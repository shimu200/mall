package com.atguigu.seckill.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
//@EnableAsync
//@EnableScheduling
public class HelloSchedule {
    /*
         1.spring种不能出现第七位(年）
         2.在周几，1-7代表周一到周日
         3.定时任务不应该阻塞,默认时阻塞的
            1)异步运行业务
            2）设置
            3)@EnableAsync开启异步任务@Async
     */
//    @Async
//    @Scheduled(cron = " * * * * * ?")
//    public void hello() throws InterruptedException {
//        log.info("hello");
//        Thread.sleep(3000);
//    }
}
