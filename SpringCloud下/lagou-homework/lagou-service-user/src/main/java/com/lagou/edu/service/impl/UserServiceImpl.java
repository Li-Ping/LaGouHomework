package com.lagou.edu.service.impl;

import com.lagou.edu.dao.UserDao;
import com.lagou.edu.pojo.User;
import com.lagou.edu.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        if (password != null){
            user.setPassword(password);
        }
        Example<User> example = Example.of(user);
        Optional<User> userInfo = userDao.findOne(example);
        if (userInfo != null && userInfo.isPresent()){
            return userInfo.get();
        }
        return null;
    }

    @Override
    public void save(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userDao.save(user);
    }
}
