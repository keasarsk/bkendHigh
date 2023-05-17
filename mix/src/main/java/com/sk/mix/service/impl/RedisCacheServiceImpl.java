package com.sk.mix.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sk.mix.mapper.RedisCacheMapper;
import com.sk.mix.pojo.RedisTest;
import com.sk.mix.service.RedisCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: bkendHigh
 * @description: 在这进行保持双写一致性
 * @author: kaesar
 * @create: 2023-04-30 17:24
 **/
@Slf4j
@Service
public class RedisCacheServiceImpl extends ServiceImpl<RedisCacheMapper, RedisTest> implements RedisCacheService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<RedisTest> findAll() {
        String redisCacheFindAll = redisTemplate.opsForValue().get("RedisCacheFindAll");
        // 若缓存中有 则走缓存
        if(StringUtils.isNotBlank(redisCacheFindAll)){
            List<RedisTest> rds = JSON.parseArray(redisCacheFindAll, RedisTest.class);
            System.out.println("走了缓存");
            log.info("走了缓存");
            return rds;
        }
        // 无 则走数据库,同步到缓存
        else {
            List<RedisTest> rds = baseMapper.selectList(new LambdaQueryWrapper<>());
            redisTemplate.opsForValue().set("RedisCacheFindAll", JSON.toJSONString(rds));
            System.out.println("走了数据库, 放了缓存");
            return rds;
        }
    }
}
