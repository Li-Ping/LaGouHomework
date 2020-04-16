package com.lagou.filter;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import javax.xml.bind.ValidationEvent;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


/**
 * @author:LiPing
 * @description：
 * @date:Created in 21:51 2020/4/15 0015
 */
@Activate(group = {CommonConstants.CONSUMER})
public class TPMonitorFilter implements Filter {

    public static ConcurrentMap<Long,Long> methodAMap = new ConcurrentHashMap<>();

    public static ConcurrentMap<Long,Long> methodBMap = new ConcurrentHashMap<>();

    public static ConcurrentMap<Long,Long> methodCMap = new ConcurrentHashMap<>();

    static {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(5000);
                        long invokerTime = System.currentTimeMillis();
                        // 拿出一分钟内的响应时间序列，求响应次数
                        ConcurrentHashMap<Long, Long> collect = methodAMap.entrySet().stream().filter(e -> invokerTime - e.getKey() < 60000)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, ConcurrentHashMap::new));
                        int invokerCount = collect.size();
                        // 把响应时间按照从小到大排序，求TP90 TP99 的响应次数 C
                        List<Map.Entry<Long, Long>> sortedList = collect.entrySet().stream()
                                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                                .collect(Collectors.toList());
                        int TP90Count = (int) Math.floor(sortedList.size() * 0.9);
                        int TP99Count = (int) Math.floor(sortedList.size() * 0.99);
                        // 从排序好的序列中拿到第C 个请求响应时间
                        if (sortedList != null && sortedList.size() != 0){
                            System.out.println("methodA --> TP90:" + sortedList.get(TP90Count).getValue());
                            System.out.println("methodA --> TP99:" + sortedList.get(TP99Count).getValue());
                        }

                        // 拿出一分钟内的响应时间序列，求响应次数
                        ConcurrentHashMap<Long, Long> collectB = methodBMap.entrySet().stream().filter(e -> invokerTime - e.getKey() < 60000)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, ConcurrentHashMap::new));
                        int invokerCountB = collect.size();
                        // 把响应时间按照从小到大排序，求TP90 TP99 的响应次数 C
                        List<Map.Entry<Long, Long>> sortedListB = collectB.entrySet().stream()
                                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                                .collect(Collectors.toList());
                        int TP90CountB = (int) Math.floor(sortedListB.size() * 0.9);
                        int TP99CountB = (int) Math.floor(sortedListB.size() * 0.99);
                        // 从排序好的序列中拿到第C 个请求响应时间
                        if (sortedListB != null && sortedListB.size() != 0){
                            System.out.println("methodB --> TP90:" + sortedListB.get(TP90CountB).getValue());
                            System.out.println("methodB --> TP99:" + sortedListB.get(TP99CountB).getValue());
                        }

                        // 拿出一分钟内的响应时间序列，求响应次数
                        ConcurrentHashMap<Long, Long> collectC = methodCMap.entrySet().stream().filter(e -> invokerTime - e.getKey() < 60000)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, ConcurrentHashMap::new));
                        int invokerCouCntC = collectC.size();
                        // 把响应时间按照从小到大排序，求TP90 TP99 的响应次数 C
                        List<Map.Entry<Long, Long>> sortedListC = collectC.entrySet().stream()
                                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                                .collect(Collectors.toList());
                        int TP90CountC = (int) Math.floor(sortedListC.size() * 0.9);
                        int TP99CountC = (int) Math.floor(sortedListC.size() * 0.99);
                        // 从排序好的序列中拿到第C 个请求响应时间
                        if (sortedListC != null && sortedListC.size() != 0){
                            System.out.println("methodC --> TP90:" + sortedListC.get(TP90CountC).getValue());
                            System.out.println("methodC --> TP99:" + sortedListC.get(TP99CountC).getValue());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        long startTime  = System.currentTimeMillis();
        try {
            // 执行方法
            return invoker.invoke(invocation);
        } finally {
            long totalTime = System.currentTimeMillis()-startTime;

            long invokerTime = System.currentTimeMillis();

            if ("methodA".equals(invocation.getMethodName())){
                methodAMap.put(invokerTime,totalTime);
            } else if ("methodB".equals(invocation.getMethodName())){
                methodBMap.put(invokerTime,totalTime);
            } else {
                methodCMap.put(invokerTime,totalTime);
            }

            //System.out.println(invocation.getMethodName() + " invoke time:"+ totalTime + "毫秒");
        }

    }
}
