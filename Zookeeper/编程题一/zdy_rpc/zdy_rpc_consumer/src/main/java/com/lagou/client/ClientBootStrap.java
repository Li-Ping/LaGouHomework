package com.lagou.client;

import io.netty.util.internal.ThreadLocalRandom;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

public class ClientBootStrap {

    //public static  final String providerName="UserService#sayHello#";

    private static ZkClient zkClient;

    public static void main(String[] args) throws InterruptedException {

        // 与zookeeper建立会话
        zkClient = new ZkClient("127.0.0.1:2181");

        System.out.println("ZooKeeper session established.");

        //判断节点是否存在
        boolean exists = zkClient.exists("/server");

        if (!exists){
            // 创建根节点
            zkClient.createPersistent("/server");
        }

        // 获取服务端连接
        getServer();
    }

    public static List<String> serverList;

    /**
     * 获取服务端连接
     */
    public static void getServer(){

        serverList = zkClient.getChildren("/server");

        //System.out.println(serverList);

        //注册监听
        zkClient.subscribeChildChanges("/server", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                getServer();
            }
        });

        // 与服务端建立连接
        connectServer(serverList);

    }

    /**
     * 与服务端建立连接
     * @param serverList
     */
    public static void connectServer(List<String> serverList){

        if (serverList != null && serverList.size() != 0){
            for (String server : serverList) {
                //System.out.println("server-->" + server);
                String host;
                Integer port;
                // 获取服务端地址与端口号
                String serverName = zkClient.readData("/server/" + server);
                //System.out.println("serverName-->" + serverName);
                // 获取服务器地址
                host = serverName.split(":")[0];
                // 获取端口号
                port = Integer.valueOf(serverName.split(":")[1]);
                // 与服务端进行通信

                //RpcConsumer rpcConsumer = new RpcConsumer(host,port);

                // 生成代理对象
                //UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class);

                HandlerThread myThread = new HandlerThread(host,port,"/server/" + server,zkClient);
                myThread.start();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("zookeeper中没有注册服务可用");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        // server0000000004
        /*String server = discover(serverList);
        if (server != null){
            System.out.println("server-->" + server);
            String host;
            Integer port;
            // 获取服务端地址与端口号
            String serverName = zkClient.readData("/server/" + server);
            System.out.println("serverName-->" + serverName);
            // 获取服务器地址
            host = serverName.split(":")[0];
            // 获取端口号
            port = Integer.valueOf(serverName.split(":")[1]);
            // 与服务端进行通信

            //RpcConsumer rpcConsumer = new RpcConsumer(host,port);

            // 生成代理对象
            //UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class);

            HandlerThread myThread = new HandlerThread(host,port,"/server/" + server,zkClient);
            myThread.start();
        }else{
            System.out.println("zookeeper中没有注册服务可用");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

    }

    /**
     * 通过服务发现，获取服务提供方的地址
     * @return
     */
    public static String discover(List<String> serverList) {
        System.out.println("serverList" + serverList);
        String data = null;
        int size = serverList.size();
        if (size > 0) {
            if (size == 1) {
                //只有一个服务提供方
                data = serverList.get(0);
            } else {
                //使用随机分配法 简单的负载均衡法
                data = serverList.get(ThreadLocalRandom.current().nextInt(size));
            }
        }
        return data;
    }



}
