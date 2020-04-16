package com.lagou.service.impl;

import com.lagou.service.HelloService;

import java.util.Random;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:21 2020/4/15 0015
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String methodA(String name) {
        Random r = new Random();
        try {
            Thread.sleep(r.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Hello," + name + "This is MethodA";
    }

    @Override
    public String methodB(String name) {
        Random r = new Random();
        try {
            Thread.sleep(r.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello," + name + "This is MethodB";
    }

    @Override
    public String methodC(String name) {
        Random r = new Random();
        try {
            Thread.sleep(r.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello," + name + "This is MethodC";
    }
}
