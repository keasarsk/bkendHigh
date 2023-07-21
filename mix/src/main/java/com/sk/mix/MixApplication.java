package com.sk.mix;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

//redis
@EnableRedisHttpSession
//nacos
@EnableDiscoveryClient
@SpringBootApplication
public class MixApplication {

    public static void main(String[] args) {
        SpringApplication.run(MixApplication.class, args);
    }

}
