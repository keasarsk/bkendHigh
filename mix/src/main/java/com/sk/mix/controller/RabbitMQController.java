package com.sk.mix.controller;

import com.sk.mix.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: bkendHigh
 * @description: rebbitMQ的 生产者
 * @author: kaesar
 * @create: 2023-05-03 21:40
 **/

@RestController
public class RabbitMQController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/rabbitMQTest")
    public void rabbitMQTest(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "boot.haha", "booooo");
        System.out.println("rabbitMQTest API");
    }
}
