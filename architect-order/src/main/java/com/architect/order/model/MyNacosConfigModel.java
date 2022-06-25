package com.architect.order.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
public class MyNacosConfigModel {

    @Data
    class Server {
        private String id;
        private int order;
    }

    public String projectName;
    public String describe;
    public List<Server> serverList;
}
