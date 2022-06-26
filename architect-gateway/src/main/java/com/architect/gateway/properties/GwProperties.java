package com.architect.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "custom.gateway")
public class GwProperties {

    /**
     * 不需要token校验的url
     */
    private List<String> ignoreUrlList;

    /**
     * 登录的url
     */
    private String loginUrl;
}
