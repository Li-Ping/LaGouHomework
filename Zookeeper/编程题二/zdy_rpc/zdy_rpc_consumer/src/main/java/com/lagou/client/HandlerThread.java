package com.lagou.client;

import com.lagou.service.UserService;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 18:30 2020/4/1 0001
 */
public class HandlerThread extends Thread {

    private String host;

    private Integer port;

    // 节点路径
    private String path;

    private ZkClient zkClient;

    //private static List<String> serverList = new ArrayList<String>();

    public HandlerThread(String host, Integer port, String path, ZkClient zkClient) {
        this.host = host;
        this.port = port;
        this.path = path;
        this.zkClient = zkClient;
    }

    @Override
    public void run() {

        // 判断该节点是否存在
        boolean exists = zkClient.exists(path);
        //System.out.println(path + "-->" + exists);

        RpcConsumer rpcConsumer = new RpcConsumer(host,port);
        // 生成代理对象
        UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class);
        while (exists){
            //System.out.println("while 01 -->" + path + "-->" + exists);
            exists = zkClient.exists(path);
            //System.out.println("while 02 -->" + path + "-->" + exists);
            if (!exists){
                //System.out.println("while break -->" + path + "-->" + exists);
                break;
            }else{
                System.out.println(host + ":" + port + " 返回信息：" + proxy.sayHello("are you ok?"));
            }
        }
    }

}


/*
    // 判断该节点是否存在
    boolean exists = zkClient.exists(path);
// 未连接过该服务端
        if (!serverList.contains(host + ":" + port)){
                serverList.add(host + ":" + port);

                RpcConsumer rpcConsumer = new RpcConsumer(host,port);
                //UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class, providerName);
                // 生成代理对象
                UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class);

        while (exists){
        exists = zkClient.exists(path);
        if (!exists){
        serverList.remove(host + ":" + port);
        break;
        } else {
        System.out.println(host + ":" + port + " 返回信息：" + proxy.sayHello("are you ok?"));
        }
        }
        }*/
