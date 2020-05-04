package com.lagou.edu.controller.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:49 2020/5/3 0003
 */
@FeignClient(name = "lagou-service-email")
public interface EmailFeignClient {

    @GetMapping("/email/{email}/{code}")
    public boolean sendCode(@PathVariable String email, @PathVariable String code);
}
