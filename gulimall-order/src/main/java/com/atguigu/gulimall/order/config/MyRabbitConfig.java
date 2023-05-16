package com.atguigu.gulimall.order.config;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class MyRabbitConfig {
    private RabbitTemplate rabbitTemplate;
    /*
    JSON序列化
     */
    @Bean
    public MessageConverter messageConverter(){

        return new Jackson2JsonMessageConverter();
    }
    /*
    定制RabbitTemplate
     */
//    @PostConstruct
    public void initRabbitTemplate(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
                /**
                 *
                 * 1、只要消息抵达Broker就ack=true
                 * @param correlationData 当前消息的唯一关联数据（这个是消息的唯一id）
                 * @param ack  消息是否成功收到
                 * @param cause 失败的原因
                 */
                @Override
                public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                    System.out.println("confirm...correlationData["+correlationData+"]==>ack["+ack+"]==>cause["+cause+"]");
                }
            });
    }
}
