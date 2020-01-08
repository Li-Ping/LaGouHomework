package com.lagou.edu.utils;

import com.lagou.edu.annotation.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接工具类：从当前线程当中获取绑定的connection连接
 */
@Component("connectionUtils")
public class ConnectionUtils {

    /*// 饿汉式单例模式
    private ConnectionUtils() {

    }

    private static ConnectionUtils connectionUtils = new ConnectionUtils();

    public static ConnectionUtils getInstance() {
        return connectionUtils;
    }*/

    // 存储当前线程的连接
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 从当前线程获取连接
     * @return
     * @throws SQLException
     */
    public Connection getCurrentThreadConn() throws SQLException {
        // 判断当前线程中是否已经绑定连接，如果没有绑定，需要从连接池获取一个连接绑定到当前线程
        Connection connection = threadLocal.get();
        if(connection == null) {
            // 从连接池拿连接并绑定到线程
            connection = DruidUtils.getInstance().getConnection();
            // 绑定到当前线程
            threadLocal.set(connection);
        }
        return connection;

    }
}
