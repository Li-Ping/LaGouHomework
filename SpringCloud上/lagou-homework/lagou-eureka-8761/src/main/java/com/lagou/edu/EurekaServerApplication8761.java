package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 10:46 2020/5/3 0003
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication8761 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication8761.class, args);
    }
}
