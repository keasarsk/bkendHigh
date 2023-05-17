package com.sk.mix.controller;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: bkendHigh
 * @description: 学习使用 redission
 * @author: kaesar
 * @create: 2023-04-29 16:15
 **/
@RestController
public class redissionController {
    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("/redissonTest")
    public String Test(){
        // redissonClient.getKeys()
        return "redissonTest";
    }

}
