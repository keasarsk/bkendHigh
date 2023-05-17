package com.sk.mix.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: bkendHigh
 * @description: 使用 redis 配置分布式 Session
 * @author: kaesar
 * @create: 2023-04-30 14:29
 **/

@RestController
public class redisSessionController {

    // 设置 Session
    @RequestMapping("/redisSessionSet")
    public String setSession(HttpSession session, @RequestParam("name") String name, @RequestParam("value") String value){
        session.setAttribute(name, value);
        return "设置成功, SessionID 为" + session.getId();
    }

    // 获取 Session
    @RequestMapping("/redisSessionGet")
    public String getSession(HttpSession session, @RequestParam("name") String name){
        String attribute = (String)session.getAttribute(name);
        System.out.println(attribute);
        return "获取成功, SessionID 为" + session.getId()+ "值为" + attribute;
    }

}
