package com.architect.order.controller;

import com.architect.order.feign.StockFeignService;
import com.architect.order.model.MyNacosConfigModel;
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

    @Value("${projectName}")
    public String projectName;

    @Autowired
    StockFeignService stockFeignService;

    @RequestMapping("nacosCfg")
    public Object getCfgAll() {

        return projectName;
    }

    @RequestMapping("/add")
    public String add() {
        String msg = "下单成功";
        String rtn = "architect-order: add | 0 | " + msg;
        System.out.println(rtn);
        return rtn;
    }

    @RequestMapping("/add1")
    public String add1() {
        String msg = "下单成功";
        String stockMsg = stockFeignService.reduce1();
        String rtn = "architect-order: add1 | 1 | " + msg + " | "+ stockMsg;
        System.out.println(rtn);
        return rtn;
    }

}
