package com.architect.order.service;

import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Trace
    @Tags({
            @Tag(key="rtnVal", value = "returnedObj"),
            @Tag(key = "name", value = "arg[0]")
    })
    public String skywalkingTag(String name, int id) {
        return "skywalkingTag: " + "{ id: " + id + ", name: " + name + " }";
    }
}
