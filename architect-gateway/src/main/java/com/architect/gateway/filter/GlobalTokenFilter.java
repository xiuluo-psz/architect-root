package com.architect.gateway.filter;

import com.architect.gateway.properties.GwProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalTokenFilter implements GlobalFilter, Ordered {

    @Autowired
    GwProperties gwProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("自定义全局过滤器");
        String currentUrl = exchange.getRequest().getURI().getPath();

        if (this.shouldSkip(currentUrl)) {
            return chain.filter(exchange);
        }
//        String token = exchange.getRequest().getHeaders().getFirst("token");
//        if (null == token) {
//            ServerHttpResponse response = exchange.getResponse();
//            response.getHeaders().add("Content-Type", "application/json; charset=utf-8");
//            String msg = "无token";
//            DataBuffer buf = response.bufferFactory().wrap(msg.getBytes());
//            return exchange.getResponse().writeWith(Mono.just(buf));
//        }
        return chain.filter(exchange);
    }

    // 过滤器执行顺序，数值越小，优先级越高
    @Override
    public int getOrder() {
        return 0;
    }

    // 放行路径
    private boolean shouldSkip(String currentUrl) {
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String skipPath : gwProperties.getIgnoreUrlList()) {
            if (pathMatcher.match(skipPath, currentUrl)) {
                return true;
            }
        }
        return false;
    }
}