package com.lagou.edu.service;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:59 2020/5/3 0003
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
