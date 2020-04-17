package com.lagou.filter;

import com.lagou.utils.IPUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;


/**
 * @author:LiPing
 * @description：
 * @date:Created in 16:06 2020/4/16 0016
 */
@Activate(group = {CommonConstants.PROVIDER,CommonConstants.CONSUMER})
public class TransportIPFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 获取到IP
        //String ip = invocation.getAttachment("IP");
        // 设置到RpcContext
        //RpcContext.getContext().setAttachment("WEB-IP", ip);

        String ip = RpcContext.getContext().getAttachment("IP");
        if (!StringUtils.isEmpty(ip) ) {
            // 从RpcContext里获取ip并保存
            IPUtils.setIP(ip);
        } else {
            // 交互前重新设置ip, 避免信息丢失
            RpcContext.getContext().setAttachment("IP", IPUtils.getIP());
        }
        // 执行原方法
        return invoker.invoke(invocation);

    }
}
