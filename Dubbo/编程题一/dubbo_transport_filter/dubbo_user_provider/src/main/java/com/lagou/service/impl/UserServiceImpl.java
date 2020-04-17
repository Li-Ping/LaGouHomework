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
        String IP = RpcContext.getContext().getAttachment("IP");
        System.out.println("dubbo-user-provider,IP is " + IP);
        return "dubbo-user-provider,IP is " + IP;
    }
}
