package com.lagou.edu.sss_session.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Calendar;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 17:41 2020/3/4 0019
 */
@Controller
public class UserController {

    /**
     * 跳转到登陆页面
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String toLoginPage(Model model){
        return "login";
    }

    /**
     * 登陆验证
     * @param name
     * @param password
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login", produces = "text/html;charset=UTF-8")
    public String login(String name, String password, HttpSession session){
        String msg = "";
        if ("admin".equals(name) && "admin".equals(password)){
            //session.setAttribute("is_login","admin");
            session.setAttribute("username",name + System.currentTimeMillis());
            msg = "success";
        }else {
            msg = "用户名或密码不正确，请重新登录";
        }
        return msg;
    }

}
