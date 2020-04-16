package com.lagou.edu.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * @author:LiPing
 * @description：
 * @date:Created in 23:29 2020/4/16 0016
 */
@Component
public class IPUtils {

    public static String getIP(HttpServletRequest req) {

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
