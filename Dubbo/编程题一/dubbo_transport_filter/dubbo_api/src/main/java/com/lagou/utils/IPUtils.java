package com.lagou.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 10:14 2020/4/17 0017
 */
public class IPUtils {

    private static final ThreadLocal<String> Cache  = new ThreadLocal<String>();

    public static String getIP() {
        return Cache.get();
    }

    public static void setIP(String IP) {
        Cache.set(IP);
    }

    public static void clear() {
        Cache.remove();
    }


}
