package com.lagou.service.impl;

import com.lagou.pojo.Account;
import com.lagou.util.JDBCUtils;
import com.lagou.service.AccountService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 22:04 2020/4/1 0001
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public Account getAccountList() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Account account = new Account();
        try{
            //获取数据库连接
            conn = JDBCUtils.getConn();

            String sql = "select * from account where id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,"1");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                account.setId(resultSet.getInt("id"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            JDBCUtils.close(rs,st,conn);
        }
        return account;
    }

}
