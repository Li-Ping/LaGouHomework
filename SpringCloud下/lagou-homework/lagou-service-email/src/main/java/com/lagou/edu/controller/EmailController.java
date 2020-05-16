package com.lagou.edu.controller;

import com.lagou.edu.service.EmailService;
import com.lagou.edu.service.EmailServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 14:54 2020/5/3 0003
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailservice;

    @GetMapping("/{email}/{code}")
    public boolean sendCode(@PathVariable String email,@PathVariable String code){
        try {
            emailservice.sendSimpleMail(email, "验证码", code);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
