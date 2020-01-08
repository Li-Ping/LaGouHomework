package com.lagou.edu.dao;

import com.lagou.edu.pojo.Account;

/**
 * @author:LiPing
 * @description：dao层接口
 * @date:Created in 12:30 2020/1/4 0004
 */
public interface AccountDao {

    /**
     * 根据卡号查询账户信息
     * @param cardNo
     * @return
     * @throws Exception
     */
    Account queryAccountByCardNo(String cardNo) throws Exception;

    /**
     * 修改账户信息-转账
     * @param account
     * @return
     * @throws Exception
     */
    int updateAccountByCardNo(Account account) throws Exception;
}
