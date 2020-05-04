package com.lagou.edu.service.impl;

import com.lagou.edu.dao.AuthCodeDao;
import com.lagou.edu.pojo.AuthCode;
import com.lagou.edu.service.AuthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 15:49 2020/5/3 0003
 */
@Service
public class AuthCodeServiceImpl implements AuthCodeService {

    @Autowired
    private AuthCodeDao authCodeDao;

    @Override
    public AuthCode checkCode(String email) {
        // 根据email查询数据库中最近一次的验证码信息
        AuthCode authCode = authCodeDao.findBySql(email);
        return authCode;
    }

    @Override
    public void createCode(String email, String code) {
        AuthCode authCode = new AuthCode();
        authCode.setEmail(email);
        authCode.setCode(code);
        Timestamp timestamp  = new Timestamp(System.currentTimeMillis());
        authCode.setCreatetime(timestamp);
        authCode.setExpiretime(new Timestamp(timestamp.getTime() + (long)1000*60*10));
        authCodeDao.save(authCode);
    }
}
