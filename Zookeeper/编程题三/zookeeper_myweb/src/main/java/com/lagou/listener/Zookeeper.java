package com.lagou.listener;

import com.lagou.util.JDBCUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 19:12 2020/4/1 0001
 */
public class Zookeeper implements ServletContextListener {

    /**
     * 项目启动时
     * @param arg0
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        try {
            JDBCUtils.getConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
