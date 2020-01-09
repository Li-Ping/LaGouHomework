package com.lagou.edu.service;

/**
 * @author:LiPing
 * @description：转账接口
 * @date:Created in 12:30 2020/1/4 0004
 */
public interface TransferService {

    void transfer(String fromCardNo, String toCardNo, int money) throws Exception;
}
