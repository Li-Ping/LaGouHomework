package com.lagou.demo.service.impl;

import com.lagou.demo.service.DemoService;
import com.lagou.edu.mvcframework.annotations.LagouService;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:50 2020/1/14 0014
 */
@LagouService
public class DemoServiceImpl implements DemoService {
    @Override
    public String get(String name) {
        System.out.println(name);
        return name;
    }
}
