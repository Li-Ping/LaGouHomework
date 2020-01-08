package com.lagou.edu.utils;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidUtils {

    private DruidUtils(){
    }

    private static DruidDataSource druidDataSource = new DruidDataSource();

    static {
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://47.92.156.231:3306/zdy_mybatis");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("thor");

    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }

}
