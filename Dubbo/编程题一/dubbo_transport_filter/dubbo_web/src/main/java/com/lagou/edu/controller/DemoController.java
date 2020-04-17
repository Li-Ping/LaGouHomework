package com.lagou.edu.controller;

import com.lagou.service.HelloService;
import com.lagou.service.UserService;
import com.lagou.utils.IPUtils;
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

    @RequestMapping("/provider")
    public ModelAndView provider(HttpServletRequest req){
        String ip = getIPByReq(req);
        System.out.println("IP --->" + ip);
        //IPUtils.setIP(ip);
        RpcContext.getContext().setAttachment("IP", ip);
        helloService.hello();
        userService.userInfo();
        //System.out.println(lzz);

        // 封装了数据和页面信息
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("provider", "dubbo-provider: " + ip);
        // 视图信息，封装跳转的页面信息
        modelAndView.setViewName("test");
        return modelAndView;
    }

    public static String getIPByReq(HttpServletRequest req) {

        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }

        return ip;
    }


}
