package com.lagou.edu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 23:55 2020/5/3 0003
 */
@FeignClient(name = "lagou-service-user")
public interface TokenFeignClient {

    @GetMapping("/user/info/{token}")
    public String info(@PathVariable String token);
}
