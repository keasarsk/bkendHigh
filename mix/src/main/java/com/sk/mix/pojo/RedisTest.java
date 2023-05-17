package com.sk.mix.pojo;


import lombok.Data;

/**
 * @program: bkendHigh
 * @description: 学习 redis 接受 json 数据存储使用的实体类
 * @author: kaesar
 * @create: 2023-04-30 15:36
 **/
@Data
public class RedisTest {
    private Integer id;
    private String name;
    private Integer number;
}
