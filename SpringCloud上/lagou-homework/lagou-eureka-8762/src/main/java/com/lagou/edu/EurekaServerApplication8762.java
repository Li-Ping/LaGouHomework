package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 10:47 2020/5/3 0003
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication8762 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication8762.class, args);
    }
}
