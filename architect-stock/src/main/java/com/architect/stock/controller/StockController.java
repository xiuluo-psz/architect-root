package com.architect.stock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value="/stock")
public class StockController {

    @RequestMapping("/reduce")
    public String reduce() {
        String msg = "出库成功";
        String rtn = "architect-stock: reduce | 0 | " + msg;
        System.out.println(rtn);
        return rtn;
    }

    @RequestMapping("/reduce1")
    public String reduce1() {
        String msg = "出库成功";
        String rtn = "architect-stock: reduce1 | 1 | " + msg;
        System.out.println(rtn);
        return rtn;
    }
}
