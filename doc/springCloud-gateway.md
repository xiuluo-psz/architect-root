### 核心概念

Route ：路由网关的基本构建块。它由 ID，目标 URI，谓词集合和 filte r集合定义。如果聚合谓词为 true，则匹配路由。

Predicate ：Java 8 函数谓词。Importing 类型为 Spring Framework ServerWebExchange。这使开发人员可以匹配 HTTP 请求中的所有内容，例如 Headers 或参数。

filter ：这些实例Spring Framework GatewayFilter 是使用特定工厂构造的。在此，可以在发送下游请求之前或之后修改请求和响应。

### 路由规则：

1. path

   ```yml
   server:
     port: 9001                                   #网关地址以localhost:9001为例
   spring:
     cloud:
       gateway:
         routes:
           - id: id-path                          #路由id
             uri: https://www.baidu.com           #目标URI
             predicates:                          #断言
             - Path=/example/**                   #匹配对应URI的请求
   ```

   请求 <mark>http://localhost:9001/example/1 </mark>将会路由至 <mark>https://www.baidu.com/example/1</mark>

2. query

   ```yml
   spring:
     cloud:
       gateway:
         routes:
           - id: id-query
             uri: https://www.bilibili.com/
             predicates:
   #            - Query=token               #匹配请求参数中包含token的请求
               - Query=token, reg.          #匹配请求中包含token并且其参数值满足正则表达式 reg. 的请求
   ```

   Query=token : <mark>http:localhost:9001/example?token=123</mark>

   Query=token, reg. :  <mark>http:localhost:9001/example?token=reg1</mark>

3. Method

   ```yml
   spring:
     cloud:
       gateway:
         routes:
           - id: id-method
             uri: https://www.bilibili.com/
             predicates:
               - Method=POST                #匹配任意Post请求
   ```

   

4. Datetime

   ```yml
   spring:
     cloud:
       gateway:
         routes:
           - id: id-datetime
             uri: https://www.bilibili.com/
             predicates:
             	# 匹配在上海时间2022-04-06 20:20:20 之后的请求
               - After=2022-04-06T20:20:20.000+08:00[Asia/Shanghai]
               # 匹配在上海时间2022-04-10 20:20:20 之前的请求
               - Before=2022-04-10T20:20:20.000+08:00[Asia/Shanghai]
               # 匹配在上海时间2022-04-06 20:20:20 与 2022-04-10 20:20:20 之间的请求
               - Between=2022-04-06T20:20:20.000+08:00[Asia/Shanghai], 2022-04-10T20:20:20.000+08:00[Asia/Shanghai]
   ```

   

5. RemoteAddr

   ```yml
   spring:
     cloud:
       gateway:
         routes:
           - id: id-remoteAddr
             uri: https://www.bilibili.com/
             predicates:
               - RemoteAddr=192.168.150.1/0    #匹配远程地址是RemoteAddr的请求，0表示子网掩码
   ```

   <mark>localhost:9001请求不到，得是192.168.150.1:9001才请求得到</mark>

6. Header

   ```yml
   spring:
     cloud:
       gateway:
         routes:
           - id: id-header
             uri: https://www.bilibili.com/
             predicates:
               - Header=exp, \d+              #匹配请求头中包含exp，且值匹配\d+的正则的请求
   ```

### 过滤器

#### 网关过滤器

##### 路径过滤器

1. RewritePathGatewayFilterFactory

   ```yml
   spring:
     cloud:
       gateway:
         routes:
           - id: id-RewritePath
             uri: https://www.bilibili.com/
             predicates:
               - Path=/example/**, /api-example/**
             filters:
             	# 将/api-example/id/1 重写为 /id/1
             	- RewritePath=/api-example(?<segment>/?.*), $\{segmnet}
   ```

2. PrefixPathGatewayFilterFactory

   ```yml
   spring:
     cloud:
       gateway:
         routes:
           - id: id-PrefixPath
             uri: https://www.bilibili.com/
             predicates:
               - Path=/**
             filters:
             	# 将/** 重写为/prefix-example/**
             	- PrefixPath=/prefix-example
   ```

3. StripPathGatewayFilterFactory

   ```yml
   spring:
     cloud:
       gateway:
         routes:
           - id: id-StripPath
             uri: https://www.bilibili.com/
             predicates:
               - Path=/**
             filters:
             	# 以'/'分割路径。数字从1开始。/a/b/c/d 重写为 /b/c/d
             	- StripPrefix=1
   ```

4. SetPathGatewayFilterFactory

   ```yml
   spring:
     cloud:
       gateway:
         routes:
           - id: id-SetPath
             uri: https://www.bilibili.com/
             predicates:
               - Path=/api/service-name/{segment}
             filters:
             	# 将segment拼到以下路径。/api/service-name/1 重写为 /example/1
             	- SetPath=/example/{segment}
   ```

##### Parameter参数过滤器

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: id-paramter
          uri: https://www.bilibili.com/
          predicates:
            - Path=/api/**
          filters:
          	# 在下游请求中添加flag的参数，且其值为1
          	- AddRequestParamter=flag, 1
```

##### Status过滤器

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: id-paramter
          uri: https://www.bilibili.com/
          predicates:
            - Path=/api/**
          filters:
          	# 任何情况下，响应的HTTP状态置为404
          	- SetStatus=404
```



##### 全局过滤器



##### 自定义网关过滤器

```java
package com.architect.gateway.myfilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// 自定义网关过滤器需要实现：GatewayFilter， Ordered两个接口
public class MyFilter implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("自定义网关过滤器");
        return chain.filter(exchange);
    }

    // 过滤器执行顺序，数值越小，优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
```

```java
package com.architect.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.architect.gateway.myfilter.MyFilter;

@Configuration
public class MyGatewayRoutesConfiguration {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("404", r -> r
                        .path("/404")
                        .filters(f -> f.filters(new MyFilter()))
                        .uri("https://www.baidu.com")
                )
                .build();
    }
}
```



##### 自定义全局过滤器

```java
package com.architect.gateway.myfilter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.http.HttpResponse;

// 自定义全局过滤器需要实现：GlobalFilter， Ordered两个接口
@Component
public class MyFilterGlobal implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("自定义全局过滤器");
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (null == token) {
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", "application/json; charset=utf-8");
            String msg = "无token";
            DataBuffer buf = response.bufferFactory().wrap(msg.getBytes());
            return exchange.getResponse().writeWith(Mono.just(buf));
        }
        return chain.filter(exchange);
    }

    // 过滤器执行顺序，数值越小，优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
```

