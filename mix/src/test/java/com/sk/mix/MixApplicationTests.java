package com.sk.mix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class MixApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    RedisTemplate redisTemplate;

    @Test
    void redisTest(){
        //操作string
        stringRedisTemplate.opsForValue().append("redistest", "testvalue");
        String str = stringRedisTemplate.opsForValue().get("redistest");
        System.out.println(str);

        // 操作对象
        // 这里是默认使用 jdk 序列化的方式将对象存储,
        // 可以为 redis 进行配置,使得对象存放在 redis 中可以是各种形式 比如 json
        redisTemplate.opsForValue().set("object",new Integer(2));
        Object object = redisTemplate.opsForValue().get("object");
        System.out.println(object);

    }
}

