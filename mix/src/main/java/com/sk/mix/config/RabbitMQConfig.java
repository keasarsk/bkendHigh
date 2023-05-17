package com.sk.mix.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: bkendHigh
 * @description: rabbitMQ 生产者的交换机, 队列, 绑定关系
 * @author: kaesar
 * @create: 2023-05-01 12:28
 **/

@Configuration
public class RabbitMQConfig {
    // 1. 定义交换机
    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    @Bean("bootExchange")
    public Exchange bootExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    // 2. 定义队列
    public static final String QUEUE_NAME = "boot_queue";
    @Bean("bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }
    // 3. 定义绑定关系 binding 对象
    /*
    * 1. 知道队列
    * 2. 知道交换机
    * 3. routing key*/
    @Bean
    public Binding bootQueueExchange(@Qualifier("bootQueue") Queue qu, @Qualifier("bootExchange") Exchange ex){
        return BindingBuilder.bind(qu).to(ex).with("boot.#").noargs();
    }

}
