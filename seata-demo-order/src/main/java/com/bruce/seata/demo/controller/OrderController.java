package com.bruce.seata.demo.controller;

import com.bruce.seata.demo.service.OrderTblService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName seata-demo
 * @Date 2023/12/3 21:06
 * @Author Bruce
 */
@RestController
@RequestMapping("/")
public class OrderController {

    @Resource
    private OrderTblService orderTblService;

    @GetMapping("/create")
    public String create(String userId, String commodityCode, Integer money) {
        return orderTblService.createLocal(userId, commodityCode, money);
    }

    @GetMapping("/uncreate")
    public void unCreate(String orderId) {
        orderTblService.unCreate(orderId);
    }


}
