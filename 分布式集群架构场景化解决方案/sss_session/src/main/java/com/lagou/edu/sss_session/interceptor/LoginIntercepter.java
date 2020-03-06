package com.lagou.edu.sss_session.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 17:34 2020/1/19 0019
 */
public class LoginIntercepter implements HandlerInterceptor {

    /**
     * 之前执行（进入Handler处理之前）
     * 可以进行权限验证
     * @param request
     * @param response
     * @param handler
     * @return  true放行，false中止程序
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // 取出session中的username
        Object username = session.getAttribute("username");
        System.out.println("=================>>>>>username:" + username);
        if(username == null) {
            // 没有登录,重定向到登录页
            System.out.println("未登录，请登录");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return false;
        }else{
            System.out.println("已登录，放行请求");
            // 已登录，放行
            return true;
        }

        /*
        String isLogin = (String) session.getAttribute("is_login");
        if (isLogin != null){
            return true;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        return false;*/
    }
}
