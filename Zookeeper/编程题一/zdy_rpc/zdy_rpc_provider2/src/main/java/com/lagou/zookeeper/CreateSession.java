package com.lagou.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 10:19 2020/4/1 0001
 */
public class CreateSession {

    private static ZkClient zkClient;

    public static void register(String data){
        if (data != null) {
            // 建立连接
            ZkClient zk = createSession();
            if (zk != null) {
                // 创建节点
                createNode(data);
            }
        }
    }

    /**
     * 建立连接
     * @throws IOException
     * @throws InterruptedException
     * @return
     */
    public static ZkClient createSession(){
        zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("ZooKeeper session established.");
        return zkClient;
    }

    /**
     * 创建节点
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void createNode(String data){
        /*String s = zooKeeper.create("/server", data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("创建的临时顺序服务器节点:" + s);*/

        //判断节点是否存在
        boolean exists = zkClient.exists("/server");
        if (!exists){
            zkClient.createPersistent("/server");
        }

        //createParents的值设置为true，可以递归创建节点
        //服务端启动，在zookeeper创建临时顺序节点
        String node = zkClient.createEphemeralSequential("/server/server", data);

        System.out.println("服务端启动，创建临时顺序节点:"+ node);
    }

}
