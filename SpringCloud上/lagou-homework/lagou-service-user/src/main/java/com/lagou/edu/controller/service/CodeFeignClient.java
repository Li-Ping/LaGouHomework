package com.lagou.edu.controller.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 18:22 2020/5/3 0003
 */
@FeignClient(name = "lagou-service-code")
public interface CodeFeignClient {

    @GetMapping("/code/validate/{email}/{code}")
    public Integer checkCode(@PathVariable String email, @PathVariable String code);
}
