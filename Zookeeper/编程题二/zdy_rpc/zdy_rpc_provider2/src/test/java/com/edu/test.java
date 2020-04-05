package com.edu;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 9:46 2020/4/5 0005
 */
public class test {

    @Test
    public void test01() throws InterruptedException {

        ZkClient zkClient = new ZkClient("127.0.0.1:2181");

        String path = "/lg-zkClient";

        //判断节点是否存在
        boolean exists = zkClient.exists(path);
        String node = null;
        if (!exists){
            node = zkClient.createEphemeralSequential(path, "123");
        }

        System.out.println(node);

        //注册监听
        zkClient.subscribeDataChanges(node, new IZkDataListener() {
            public void handleDataChange(String path, Object data) throws Exception {
                System.out.println(path+"该节点内容被更新，更新后的内容"+data);
            }
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s+" 该节点被删除");
            }
        });

        //获取节点内容
        Object o = zkClient.readData(node);
        System.out.println(o);

        /*while (true){
            //更新
            zkClient.writeData(path, System.currentTimeMillis());
            System.out.println("更新：" + System.currentTimeMillis());
            Thread.sleep(10000);
        }*/

        //删除
        zkClient.delete(node);
        Thread.sleep(1000);

        while (true){

        }

    }
}
