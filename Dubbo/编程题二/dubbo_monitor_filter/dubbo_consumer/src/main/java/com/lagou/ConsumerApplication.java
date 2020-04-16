package com.lagou;

import com.lagou.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:38 2020/4/15 0015
 */
public class ConsumerApplication {

    public static void main(String[] args) throws  Exception{

        // 定义一个线程池
        int corePoolSize = 10;
        int maximumPoolSize =50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-comsumer.xml");
        context.start();
        HelloService demoService = context.getBean(HelloService.class);


        while (true){
            System.in.read();
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(demoService.methodA("worldA"));
                }
            });

            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(demoService.methodB("worldB"));
                }
            });

            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(demoService.methodC("worldC"));
                }
            });
        }



    }
}
