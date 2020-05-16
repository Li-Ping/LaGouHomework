package com.lagou.edu.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author:LiPing
 * @description：IP防暴刷（限制单个客户端IP在最近X分钟内请求注册接⼝不能超过Y次）
 * @date:Created in 21:21 2020/5/3 0003
 */
@Component
@RefreshScope
public class IPFilter implements GlobalFilter, Ordered {

    @Value("${config.time}")
    private Integer time;

    @Value("${config.count}")
    private Integer count;

    // IP访问记录
    private static ConcurrentHashMap<String, List<Long>> ipMap = new ConcurrentHashMap<>();

    /**
     * 添加全局过滤器，进⾏IP注册接⼝的防暴刷控制，超过阈值直接返回错误码及错误信息（错
     * 误码：303，错误信息：您频繁进⾏注册，请求已被拒绝
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 从上下⽂中取出request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 访问路径
        String path = request.getURI().getPath();
        // 用户访问注册接口
        if (path.contains("/user/register")){
            List<Long> ipList = new ArrayList<Long>();
            // 从request对象中获取客户端ip
            String clientIp = request.getRemoteAddress().getHostString();
            // 判断clientIp访问次数是否超过阀值
            if (ipMap.containsKey(clientIp)){
                // 过滤出clientIp最近time时间内的访问记录
                ipList = ipMap.get(clientIp).stream()
                        .filter(e -> e > (System.currentTimeMillis() - (long) time * 1000 * 60)).collect(Collectors.toList());
                if (ipList.size() > count){
                    // 决绝访问，返回
                    response.setStatusCode(HttpStatus.SEE_OTHER); // 状态码
                    String data = "您频繁进⾏注册，请求已被拒绝！";
                    DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
                    return response.writeWith(Mono.just(wrap));
                }
                ipList = ipMap.get(clientIp);
            }
            ipList.add(System.currentTimeMillis());
            ipMap.put(clientIp, ipList);
        }
        // 合法请求，放⾏，执⾏后续的过滤器
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
