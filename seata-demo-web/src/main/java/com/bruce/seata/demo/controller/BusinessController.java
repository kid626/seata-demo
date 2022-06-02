package com.bruce.seata.demo.controller;

import com.bruce.seata.demo.service.BusinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName seata-demo
 * @Date 2022/6/2 14:38
 * @Author fzh
 */
@RestController()
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private BusinessService businessService;

    @GetMapping("/v1/purchase")
    public String purchase(String userId, String commodityCode, Integer orderCount) {
        businessService.purchase(userId, commodityCode, orderCount);
        return "success";
    }

}
