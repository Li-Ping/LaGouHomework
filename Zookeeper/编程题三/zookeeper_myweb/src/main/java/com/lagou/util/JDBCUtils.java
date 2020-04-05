package com.lagou.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 20:27 2020/4/1 0001
 */
public class JDBCUtils {

    /**
     * 定义数据源
     */
    private static DataSource ds;

    private static Properties pro;

    static {

        // 获取配置信息以及数据源
        try {
            getConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getConfig() throws Exception {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        // 判断zookeeper中是否存在配置信息
        boolean exists = zkClient.exists("/config");
        pro = new Properties();
        if (exists){
            // 拉取配置信息
            pro = (Properties) zkClient.readData("/config");
        }else{
            try {
                // 加载默认配置文件 jdbc.properties
                InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
                pro.load(is);
                // 创建配置信息节点
                zkClient.createPersistent("/config",pro);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("配置信息：" + pro.toString());

        //监听配置文件修改
        zkClient.subscribeDataChanges("/config", new IZkDataListener(){
            @Override
            public void handleDataChange(String path, Object data)throws Exception {
                System.out.println("监听到配置文件被修改：" + data);
                getConfig();
            }

            @Override
            public void handleDataDeleted(String arg0) throws Exception {
                System.out.println("监听到配置文件被删除");
                getConfig();
            }

        });

        // 获取数据源
        ds = DruidDataSourceFactory.createDataSource(pro);
    }

    /**
     * 获取数据源
     * @return 返回数据源
     */
    public static DataSource getDataSource(){
        return ds;
    }

    /**
     * 获取连接对象
     * @return 返回连接对象
     * @throws SQLException  抛出的编译异常
     */
    public static Connection getConn() throws SQLException {
        return ds.getConnection();
    }

    /**
     *  关闭连接
     * @param stmt  sql执行对象
     * @param conn  数据库连接对象
     */
    public static void close(Statement stmt, Connection conn){
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭资源的重载方法
     * @param rs    处理结果集的对象
     * @param stmt  执行sql语句的对象
     * @param conn  连接数据库的对象
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn){

        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
