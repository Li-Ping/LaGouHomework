package com.lagou.edu.filter;

import com.lagou.edu.service.TokenFeignClient;
import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 22:26 2020/5/3 0003
 */
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    // 模拟不过滤的请求地址列表
    private static List<String> urlList = new ArrayList<>();
    static {
        urlList.add("/user/register/");
        urlList.add("/user/isRegistered/");
        urlList.add("/code/create/");
        urlList.add("/code/validate/");
        urlList.add("/user/login/");
    }

    @Autowired
    private TokenFeignClient tokenFeignClient;

    /**
     * token验证，⽤户微服务和验证码微服务的请求不过滤（⽹关调⽤下游
     * ⽤户微服务的token验证接⼝
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
        // 判断访问路径是否需要过滤器过滤
        List<String> urls = urlList.stream().filter(e -> path.contains(e)).collect(Collectors.toList());
        if (urls.size() == 0){
            //获取token
            String token = getCookie(request, "token");
            String url;
            // 没有token，进行跳转到登陆页面
            if (token == null){
                url = "http://localhost:8030/";
                //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().set(HttpHeaders.LOCATION, url);
                return response.setComplete();
            }else {
                // 根据token查询邮箱
                String email = tokenFeignClient.info(token);
                if (email == null){
                    // 跳转到登陆页面
                    url = "http://localhost:8030/";
                    //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
                    response.setStatusCode(HttpStatus.SEE_OTHER);
                    response.getHeaders().set(HttpHeaders.LOCATION, url);
                    return response.setComplete();
                }
            }
        }
        // 合法请求，放⾏，执⾏后续的过滤器
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public static String getCookie(ServerHttpRequest request,String name) {
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (cookies == null || cookies.size() < 1) {
            return null;
        }
        String token = null;
        for (Map.Entry<String, List<HttpCookie>> c : cookies.entrySet()) {
            if (name.equals(c.getKey())) {
                token = c.getValue().get(0).getValue();
                break;
            }
        }
        return token;
    }


}
