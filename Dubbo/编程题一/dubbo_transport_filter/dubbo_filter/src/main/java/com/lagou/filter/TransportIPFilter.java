package com.lagou.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:06 2020/4/16 0016
 */
@Activate(group = {CommonConstants.PROVIDER})
public class TransportIPFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 获取到IP
        String ip = invocation.getAttachment("IP");
        // 设置到RpcContext
        //RpcContext.getContext().setAttachment("WEB-IP", ip);
        // 执行原方法
        return invoker.invoke(invocation);

    }
}
