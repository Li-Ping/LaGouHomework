package com.lagou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerBootstrap {

    public static void main(String[] args) throws InterruptedException {
        //UserServiceImpl.startServer("127.0.0.1",8990);
        SpringApplication.run(ServerBootstrap.class, args);

    }



}
