package com.lagou.edu.service.impl;

import com.lagou.edu.dao.TokenDao;
import com.lagou.edu.pojo.Token;
import com.lagou.edu.service.TokenService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.util.Optional;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:59 2020/5/3 0003
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenDao tokenDao;

    @Override
    public void insert(String token, String email) {
        Token tokenInfo = new Token();
        tokenInfo.setEmail(email);
        tokenInfo.setToken(token);
        tokenDao.save(tokenInfo);
    }

    @Override
    public String getEmail(String token) {
        Token tokenInfo = new Token();
        tokenInfo.setToken(token);
        Example<Token> example = Example.of(tokenInfo);
        Optional<Token> one = tokenDao.findOne(example);
        if (one != null && one.isPresent()){
            return one.get().getEmail();
        }
        return null;
    }
}
