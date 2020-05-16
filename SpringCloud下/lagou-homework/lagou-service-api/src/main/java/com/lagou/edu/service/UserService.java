package com.lagou.edu.service;

import com.lagou.edu.pojo.User;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:44 2020/5/16 0016
 */
public interface UserService {

    /**
     * 条件查询用户信息
     * @param email
     * @param password
     * @return
     */
    public User getUser(String email, String password);

    /**
     * 注册用户
     * @param email
     * @param password
     */
    public void save(String email,String password);
}
