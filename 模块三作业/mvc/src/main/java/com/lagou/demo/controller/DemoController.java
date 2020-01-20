package com.lagou.demo.controller;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:48 2020/1/14 0014
 */

import com.lagou.demo.service.DemoService;
import com.lagou.edu.mvcframework.annotations.LagouAutowired;
import com.lagou.edu.mvcframework.annotations.LagouController;
import com.lagou.edu.mvcframework.annotations.LagouRequestMapping;
import com.lagou.edu.mvcframework.annotations.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@LagouController
@LagouRequestMapping("/demo")
public class DemoController {

    @LagouAutowired
    private DemoService demoService;

    @Security({"laGou","admin","student"})
    @LagouRequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response,String name){
        System.out.println("query方法执行成功");
        return  demoService.get(name);
    }

    @Security({"laGou","admin","student"})
    @LagouRequestMapping("/handler01")
    public void handler01(HttpServletRequest request, HttpServletResponse response,String name) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("handler01方法执行成功！");
    }

    @Security({"laGou","admin"})
    @LagouRequestMapping("/handler02")
    public void handler02(HttpServletRequest request, HttpServletResponse response,String name) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("handler02方法执行成功！");
    }

    @Security({"laGou","student"})
    @LagouRequestMapping("/handler03")
    public void handler03(HttpServletRequest request, HttpServletResponse response,String name) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("handler03方法执行成功！");
    }

    @Security({"admin","student"})
    @LagouRequestMapping("/handler04")
    public void handler04(HttpServletRequest request, HttpServletResponse response,String name) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("handler04方法执行成功！");
    }

    @Security({"admin"})
    @LagouRequestMapping("/handler05")
    public void handler05(HttpServletRequest request, HttpServletResponse response,String name) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("handler05方法执行成功！");
    }
}
