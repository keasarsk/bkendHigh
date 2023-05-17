package com.sk.mix.controller;

import com.sk.mix.pojo.RedisTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: bkendHigh
 * @description: SpringBoot 整合 redis , 使用redistemplate
 * @author: kaesar
 * @create: 2023-04-28 23:03
 **/


@RestController
public class redisController {
    @Resource
    StringRedisTemplate stringRedisTemplate; // 操作 k-v 都是字符串的

    @Resource
    RedisTemplate redisTemplate;// 操作 k-v 都是对象的

    // 测试使用 stringRedisTemplate
    @RequestMapping("/stringRedisTemplateTest")
    public String stringRedisTemplateTest(){
        //操作string
        stringRedisTemplate.opsForValue().append("redistest", "testvalue");
        String str = stringRedisTemplate.opsForValue().get("redistest");
        return str;
    }
    // 测试使用 redisTemplate
    @RequestMapping("/redisTemplateTest")
    public Object redisTemplateTest(){
        // 操作对象
        // 这里是默认使用 jdk 序列化的方式将对象存储,
        // 可以为 redis 进行配置,使得对象存放在 redis 中可以是各种形式 比如 json
        redisTemplate.opsForValue().set("object",new Integer(2));
        Object object = redisTemplate.opsForValue().get("object");

        return object;
    }


    // 测试 redis 接收 json 数据 存储
    @RequestMapping("/redisJsonTest")
    public void redisJsonTest(@RequestBody RedisTest redisTest){
        redisTemplate.opsForValue().set("redisJson", redisTest);
        System.out.println("redisJsonTest");
    }

    // 测试 redis 接收 json 数据  取
    @RequestMapping("/redisJsonGet")
    public RedisTest redisJsonTestGet(@RequestParam("key") String key){
        return (RedisTest) redisTemplate.opsForValue().get(key);
    }
}
