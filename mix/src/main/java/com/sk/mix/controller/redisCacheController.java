package com.sk.mix.controller;

import com.sk.mix.pojo.RedisTest;
import com.sk.mix.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: bkendHigh
 * @description: redis 作为数据库缓存 保证双写一致性
 * @author: kaesar
 * @create: 2023-04-30 16:55
 **/


@RestController
public class redisCacheController {

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisCacheService redisCacheService;

    @RequestMapping("/redisCacheTest")
    public List<RedisTest> redisCacheTest(){
        return redisCacheService.findAll();
        // return redisCacheService.getById(1);
    }

}
