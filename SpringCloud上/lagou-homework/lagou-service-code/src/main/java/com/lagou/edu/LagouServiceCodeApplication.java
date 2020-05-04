package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 14:13 2020/5/3 0003
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LagouServiceCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LagouServiceCodeApplication.class, args);
    }
}
