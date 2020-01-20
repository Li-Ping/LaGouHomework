package com.lagou.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 17:41 2020/1/19 0019
 */
@Controller
public class UserController {

    @ResponseBody
    @RequestMapping(value = "login", produces = "text/html;charset=UTF-8")
    public String login(String name, String password, HttpSession session){
        String msg = "";
        if ("admin".equals(name) && "admin".equals(password)){
            session.setAttribute("is_login","admin");
            msg = "success";
        }else {
            msg = "用户名或密码不正确，请重新登录";
        }
        return msg;
    }
}
