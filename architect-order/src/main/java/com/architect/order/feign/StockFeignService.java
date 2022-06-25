package com.architect.order.feign;

//import com.architect.order.config.FeignCfg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * name: rest接口对应的服务名
 * path: rest接口所在的controller指定的RequestMapping
 * configuration: 局部配置
 */
@FeignClient(name = "architect-stock", path = "/stock"/*, configuration = FeignCfg.class*/)
public interface StockFeignService {

    @RequestMapping("reduce")
    public String reduce();

    @RequestMapping("reduce1")
    public String reduce1();
}
