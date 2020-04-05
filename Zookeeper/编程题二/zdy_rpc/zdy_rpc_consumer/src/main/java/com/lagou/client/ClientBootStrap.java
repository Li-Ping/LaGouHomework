package com.lagou.client;

import com.lagou.service.UserService;
import io.netty.util.internal.ThreadLocalRandom;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        while (true){

            //当客户端发起调用，每次都选择最后一次响应时间短的服务端进行服务调用，如果时间一致，随机选取一个服务端进行调用，从而实现负载均衡
            // 获取服务端连接
            String server = getServer();
            // 判断zookeeper中是否有注册服务可用
            if (server != null){
                server = zkClient.readData("/server/" + server);
                // 获取服务器地址
                String host = server.split(":")[0];
                // 获取端口号
                Integer port = Integer.valueOf(server.split(":")[1]);
                // 客户端发起调用
                RpcConsumer rpcConsumer = new RpcConsumer(host,port);
                // 生成代理对象
                UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class);
                System.out.println(host + ":" + port + " 返回信息：" + proxy.sayHello("are you ok?"));
            }else{
                System.out.println("zookeeper中没有注册服务可用");
            }

        }

    }

    public static List<String> serverList;

    /**
     * 获取服务端连接
     */
    public static String getServer(){

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
        //connectServer(serverList);

        // 获取需要连接的服务器地址以及端口号
        String server = discover(serverList);

        return server;

    }

    /**
     * 与服务端建立连接
     * @param serverList
     */
    public static void connectServer(List<String> serverList){

        // server0000000004
        String server = discover(serverList);
        if (server != null){
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
            HandlerThread myThread = new HandlerThread(host,port,"/server/" + server,zkClient);
            myThread.start();
        }else{
            System.out.println("zookeeper中没有注册服务可用");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 通过服务发现，获取服务提供方的地址
     * @return
     */
    public static String discover(List<String> serverList) {
        String data = null;
        int size = serverList.size();
        if (size > 0) {
            if (size == 1) {
                //只有一个服务提供方
                data = serverList.get(0);
            } else {
                long timeMillis = System.currentTimeMillis();
                // Zookeeper记录每个服务端的最后一次响应时间，有效时间为5秒，5s内如果该服务端没有新的请求，响应时间清零或失效
                List<String> collect = serverList.stream().filter((p) ->
                        timeMillis - Long.valueOf(zkClient.readData("/server/" + p).toString().split(":")[2]) <= 10)
                        .collect(Collectors.toList());

                if (collect != null && collect.size() > 0){
                    // 选择最后一次响应时间短的服务端进行服务调用
                    Optional<String> min = collect.stream().min((p1, p2) ->
                            zkClient.readData("/server/" + p1).toString()
                                    .split(":")[2]
                                    .compareTo(zkClient.readData("/server/" + p2).toString().split(":")[2]));
                    data = min.get();
                }else{
                    // 如果时间一致，随机选取一个服务端进行调用，从而实现负载均衡
                    //使用随机分配法 简单的负载均衡法
                    data = serverList.get(ThreadLocalRandom.current().nextInt(size));
                }
            }
        }
        return data;
    }



}
