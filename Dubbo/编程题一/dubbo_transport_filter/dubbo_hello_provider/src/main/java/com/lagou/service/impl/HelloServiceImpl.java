package com.lagou.service.impl;

import com.lagou.service.HelloService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Service;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 15:43 2020/4/16 0016
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        String IP = RpcContext.getContext().getAttachment("WEB-IP");
        System.out.println("dubbo-hello-provider,IP is " + IP);
        return "dubbo-hello-provider,IP is " + IP;
    }
}
