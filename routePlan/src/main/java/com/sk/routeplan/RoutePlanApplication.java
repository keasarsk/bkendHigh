package com.sk.routeplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RoutePlanApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoutePlanApplication.class, args);
    }

}
