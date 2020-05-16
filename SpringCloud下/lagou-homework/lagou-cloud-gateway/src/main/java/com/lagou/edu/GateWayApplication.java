package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 20:56 2020/5/3 0003
 */
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
