package com.sk.routeplan.utils;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: bkendHigh
 * @description: rabbitMQ的消费者
 * @author: kaesar
 * @create: 2023-05-03 21:49
 **/

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "boot_queue")
    public void rabbitMQConsumer(Message message){
        System.out.println("这是接收到 boot_queue 队列的消息:  ");
        System.out.println(message);
    }
}
