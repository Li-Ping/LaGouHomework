package com.lagou.controller;

import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:53 2020/4/1 0001
 */
@Controller
@RequestMapping("/account")
public class DemoController {


    @Autowired
    private AccountService accountService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public String queryAll() throws Exception {
        return accountService.getAccountList().toString();
    }
}
