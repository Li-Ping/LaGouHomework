package com.lagou.edu.service.impl;

import com.lagou.edu.annotation.Autowired;
import com.lagou.edu.annotation.Service;
import com.lagou.edu.annotation.Transactional;
import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.dao.impl.JdbcAccountDaoImpl;
import com.lagou.edu.factory.BeanFactory;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;
import com.lagou.edu.utils.ConnectionUtils;
import com.lagou.edu.utils.TransactionManager;

import java.sql.SQLException;

/**
 * @author:LiPing
 * @description：转账接口实现类
 * @date:Created in 12:30 2020/1/4 0004
 */
@Transactional
@Service("transferService")
public class TransferServiceImpl implements TransferService {

    // 实例化dao层对象
    //private AccountDao accountDao = new JdbcAccountDaoImpl();
    //private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");

    @Autowired
    private AccountDao accountDao;

    // 传值方式：构造函数传值/set方法传值
    /*public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }*/

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {

        /*try{
            // 开启事务(关闭事务的自动提交)
            TransactionManager.getInstance().beginTransaction();
            //ConnectionUtils.getInstance().getCurrentThreadConn().setAutoCommit(false);*/

            // 查询收款账户、付款账户
            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            // 改变金额
            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            // 转账操作
            accountDao.updateAccountByCardNo(to);
            //int c = 1/0;
            accountDao.updateAccountByCardNo(from);

           /* // 提交事务
            //ConnectionUtils.getInstance().getCurrentThreadConn().commit();
            TransactionManager.getInstance().commit();
        }catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            // ConnectionUtils.getInstance().getCurrentThreadConn().rollback();

            TransactionManager.getInstance().rollback();

            // 抛出异常便于上层servlet捕获
            throw e;
        }*/
    }
}
