package com.lagou.edu.service;

import com.lagou.edu.pojo.AuthCode;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:43 2020/5/16 0016
 */
public interface CodeService {

    /**
     * 根据email查询数据库中最近一次的验证码信息
     * @param email
     * @return
     */
    public AuthCode checkCode(String email);

    /**
     * ⽣成验证码并发送到对应邮箱，成功 true，失败 false
     * @param email
     * @return
     */
    public void createCode(String email, String code);
}
