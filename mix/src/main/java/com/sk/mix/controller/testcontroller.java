package com.sk.mix.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: bkendHigh
 * @description: nacostest
 * @author: kaesar
 * @create: 2023-04-28 21:41
 **/

@RefreshScope
@RestController
public class testcontroller {
    @Value("${mix.config.test}")
    private String nacoconfigtest;

    @RequestMapping("/nacosconfigtest")
    public String nacosconfigtest(){
        return nacoconfigtest;
    }
}
