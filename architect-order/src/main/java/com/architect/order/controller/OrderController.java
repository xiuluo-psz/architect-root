package com.architect.order.controller;

import com.architect.order.feign.StockFeignService;
import com.architect.order.model.MyNacosConfigModel;
import com.architect.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RefreshScope // 动态更新配置中心数据Value
@RestController
@RequestMapping(value="/order")
public class OrderController {

    @Value("${server.port}")
    public int port;

    // Nacos配置的信息
    @Value("${projectName}")
    public String projectName;

    @Autowired
    StockFeignService stockFeignService;

    @Autowired
    OrderService orderService;

    @RequestMapping("nacosCfg")
    public String getNacosCfg() {

        return projectName;
    }

    @RequestMapping("/add")
    public String add() {
        String msg = "下单成功";
        String rtn = "architect-order: add | " + port + " | " + msg;
        System.out.println(rtn);
        return rtn;
    }

    @RequestMapping("/add1")
    public String add1() {
        String msg = "下单成功";
        String stockMsg = stockFeignService.reduce1();
        String rtn = "architect-order: add1 | " + port + " | " + msg + " | "+ stockMsg;
        System.out.println(rtn);
        return rtn;
    }

    @RequestMapping("/skywalking")
    public String skywalking(String name, int id) {
        String msg = "skywalking接口" + "{ id: " + id + ", name: " + name + " }";
        String rtn = "architect-order: skywalking | " + port + " | " + msg;
        System.out.println(rtn);
        return rtn;
    }

    @RequestMapping("/skywalkingTag")
    public String skywalkingTag(String name, int id) {
        String tag = orderService.skywalkingTag(name, id);
//        String msg = "skywalkingTag接口" + "{ id: " + id + ", name: " + name + " }";
        String rtn = "architect-order: skywalkingTag | " + port + " | " + tag;
        System.out.println(rtn);
        return rtn;
    }

}
