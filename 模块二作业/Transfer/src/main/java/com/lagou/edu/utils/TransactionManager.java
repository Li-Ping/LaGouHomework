package com.lagou.edu.utils;

import com.lagou.edu.annotation.Autowired;
import com.lagou.edu.annotation.Component;

import java.sql.SQLException;

/**
 * @author:LiPing
 * @description：事务管理器类：负责手动事务的开启、提交、回滚
 * @date:Created in 13:40 2020/1/4 0004
 */
@Component("transactionManager")
public class TransactionManager {

    @Autowired
    private ConnectionUtils connectionUtils;

    /*public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }*/

    /*private TransactionManager(){

    }

    private static TransactionManager transactionManager = new TransactionManager();

    public static TransactionManager getInstance() {
        return  transactionManager;
    }*/



    // 开启手动事务控制
    public void beginTransaction() throws SQLException {
        connectionUtils.getCurrentThreadConn().setAutoCommit(false);
        //ConnectionUtils.getInstance().getCurrentThreadConn().setAutoCommit(false);
    }


    // 提交事务
    public void commit() throws SQLException {
        connectionUtils.getCurrentThreadConn().commit();
        //ConnectionUtils.getInstance().getCurrentThreadConn().commit();
    }


    // 回滚事务
    public void rollback() throws SQLException {
        connectionUtils.getCurrentThreadConn().rollback();
        //ConnectionUtils.getInstance().getCurrentThreadConn().rollback();
    }
}
