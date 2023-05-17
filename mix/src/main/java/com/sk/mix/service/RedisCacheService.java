package com.sk.mix.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sk.mix.pojo.RedisTest;


import java.util.List;


public interface RedisCacheService extends IService<RedisTest> {
    List<RedisTest> findAll();
}
