package com.lagou.edu.controller;

import com.lagou.edu.utils.IPUtils;
import com.lagou.service.HelloService;
import com.lagou.service.UserService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:09 2020/4/16 0016
 */
@Controller
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private HelloService helloService;

    @Autowired
    private UserService userService;

    @RequestMapping("/providerB")
    public ModelAndView providerB(HttpServletRequest req){
        String ip = IPUtils.getIP(req);
        System.out.println("IP --->" + ip);
        RpcContext.getContext().setAttachment("WEB-IP", ip);

        String lzz = helloService.hello("lzz");
        //System.out.println(lzz);

        // 封装了数据和页面信息
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("provider", "dubbo-hello-provider: " + ip);
        // 视图信息，封装跳转的页面信息
        modelAndView.setViewName("test");
        return modelAndView;
    }


    @RequestMapping("/providerC")
    public ModelAndView providerC(HttpServletRequest req){
        String ip = IPUtils.getIP(req);
        System.out.println("IP --->" + ip);
        RpcContext.getContext().setAttachment("WEB-IP", ip);

        userService.userInfo();

        // 封装了数据和页面信息
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("provider", "dubbo-user-provider: " + ip);
        // 视图信息，封装跳转的页面信息
        modelAndView.setViewName("test");
        return modelAndView;
    }


}
