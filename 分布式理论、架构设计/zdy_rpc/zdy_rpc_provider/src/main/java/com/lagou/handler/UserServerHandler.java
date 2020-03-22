package com.lagou.handler;

import com.lagou.utils.MyApplicationContext;
import com.lagou.utils.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 通信协议
        RpcRequest rpcRequest = (RpcRequest) msg;
        // 反射 类名返回类的对象
        Class<?> serviceClass = Class.forName(rpcRequest.getClassName());

        // 获取bean
        Object userService = MyApplicationContext.getObject("userService");

        final Object[] result = new Object[1];
        //借助JDK动态代理生成代理对象
        Object o = Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //method = serviceClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                //return method.invoke(userService, rpcRequest.getParameters());
				return method.invoke(userService, args);
            }
        });
        // 调用代理对象的方法
        Method method = serviceClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
        Object resultInfo = method.invoke(o, rpcRequest.getParameters());

        // 返回响应
        ctx.writeAndFlush(resultInfo);

        // 判断是否符合约定，符合则调用本地方法，返回数据
        // msg:  UserService#sayHello#are you ok?
        /*if(msg.toString().startsWith("UserService")){
            UserServiceImpl userService = new UserServiceImpl();
            String result = userService.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }*/

    }
}
