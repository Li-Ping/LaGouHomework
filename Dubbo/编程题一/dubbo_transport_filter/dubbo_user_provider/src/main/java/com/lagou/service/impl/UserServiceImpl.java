package com.lagou.service.impl;

import com.lagou.service.UserService;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:19 2020/4/16 0016
 */
public class UserServiceImpl implements UserService {

    @Override
    public String userInfo() {
        String IP = RpcContext.getContext().getAttachment("WEB-IP");
        System.out.println("dubbo-hello-provider,IP is " + IP);
        return "dubbo-hello-provider,IP is " + IP;
    }
}
