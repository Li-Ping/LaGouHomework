package com.lagou.edu.factory;

import com.lagou.edu.annotation.Autowired;
import com.lagou.edu.annotation.Component;
import com.lagou.edu.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:33 2020/1/4 0004
 */
@Component
public class ProxyFactory {

    @Autowired
    private TransactionManager transactionManager;

    /*public void setTransactionManager(TransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }*/

    /*private ProxyFactory(){

    }

    private static ProxyFactory proxyFactory = new ProxyFactory();

    public static ProxyFactory getInstance(){
        return proxyFactory;
    }*/

    /**
     * 使用jdk动态代理增强事务控制
     * @param o
     * @return
     */
    public Object getJdkProxy(Object o){
        return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object ob = null;
                try{
                    // 开启事务(关闭事务的自动提交)
                    transactionManager.beginTransaction();
                    ob = method.invoke(o, args);
                    // 提交事务
                    transactionManager.commit();
                }catch (Exception e) {
                    e.printStackTrace();
                    // 回滚事务
                    transactionManager.rollback();
                    // 抛出异常便于上层servlet捕获
                    throw e;
                }
                return ob;
            }
        });
    }

    public Object getCglibProxy(Object o){
        return Enhancer.create(o.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object ob = null;
                try{
                    // 开启事务(关闭事务的自动提交)
                    transactionManager.beginTransaction();
                    ob = method.invoke(o, objects);
                    // 提交事务
                    transactionManager.commit();
                }catch (Exception e) {
                    e.printStackTrace();
                    // 回滚事务
                    transactionManager.rollback();
                    // 抛出异常便于上层servlet捕获
                    throw e;
                }
                return ob;
            }
        });
    }
}
