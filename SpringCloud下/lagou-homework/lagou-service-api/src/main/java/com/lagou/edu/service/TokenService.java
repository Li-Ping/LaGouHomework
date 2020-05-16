package com.lagou.edu.service;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 22:49 2020/5/16 0016
 */
public interface TokenService {

    /**
     * 保存token
     * @param token
     * @param email
     */
    public void insert(String token,String email);

    /**
     * 根据token获取邮箱
     * @param token
     * @return
     */
    public String getEmail(String token);
}
