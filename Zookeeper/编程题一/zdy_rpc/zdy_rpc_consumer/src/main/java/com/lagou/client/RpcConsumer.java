package com.lagou.client;

import com.lagou.utils.JSONSerializer;
import com.lagou.utils.RpcEncoder;
import com.lagou.utils.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RpcConsumer {

    private static String host;

    private static Integer port;

    private Channel channel;

    public RpcConsumer() {
    }

    public RpcConsumer(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    //创建线程池对象
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static UserClientHandler userClientHandler;

    // 通信协议对象
    private static RpcRequest rpcRequest;

    private static Bootstrap bootstrap;

    //1.创建一个代理对象 providerName：UserService#sayHello are you ok?
    //public Object createProxy(final Class<?> serviceClass,final String providerName){
    public Object createProxy(final Class<?> serviceClass){
        //借助JDK动态代理生成代理对象
        return  Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //（1）调用初始化netty客户端的方法
                /*if(userClientHandler == null){
                    initClient();
                }*/

                initClient();
                //doConnect(host, port);

                // 封装通信协议对象
                rpcRequest = new RpcRequest();
                rpcRequest.setRequestId(getProcessID());
                rpcRequest.setClassName(serviceClass.getTypeName());
                rpcRequest.setMethodName(method.getName());
                rpcRequest.setParameterTypes(method.getParameterTypes());
                rpcRequest.setParameters(args);
                // 设置 通信协议参数
                userClientHandler.setPara(rpcRequest);

                // 设置参数
                //userClientHandler.setPara(providerName+args[0]);

                // 去服务端请求数据
                return executor.submit(userClientHandler).get();
            }
        });
    }

    //2.初始化netty客户端
    public static  void initClient() throws InterruptedException {
        userClientHandler = new UserClientHandler();

        EventLoopGroup group = new NioEventLoopGroup();

        //Bootstrap bootstrap = new Bootstrap();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                        //pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                        pipeline.addLast(userClientHandler);
                    }
                });

        //bootstrap.connect("127.0.0.1",8990).sync();

        //bootstrap.connect(host,port).sync();

        doConnect(host, port);


    }


    /**
     * 建立连接，并且可以实现自动重连.
     * @param port port.
     * @param host host.
     * @throws InterruptedException InterruptedException.
     */
    private static void doConnect(String host,int port) throws InterruptedException {

        final int portConnect = port;
        final String hostConnect = host;

        ChannelFuture future = bootstrap.connect(host, port).sync();

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (!futureListener.isSuccess()) {
                    System.out.println("Failed to connect to server, try connect after 10s");
                    // 重新连接
                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                doConnect(hostConnect,portConnect);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        }).sync();

    }


    /**
     * 获取进程id
     * @return
     */
    public static final String getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getName().split("@")[0];
    }
}
