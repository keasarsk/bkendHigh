package com.sk.mix.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sk.mix.pojo.RedisTest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RedisCacheMapper extends BaseMapper<RedisTest> {
}
