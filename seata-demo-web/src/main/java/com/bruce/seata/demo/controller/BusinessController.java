package com.bruce.seata.demo.controller;

import com.bruce.seata.demo.service.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "下单相关接口")
public class BusinessController {

    @Resource
    private BusinessService businessService;

    @GetMapping("/v1/simple")
    @ApiOperation("下单-本地事务")
    public String purchaseSimple(String userId, String commodityCode, Integer orderCount) {
        businessService.purchaseSimple(userId, commodityCode, orderCount);
        return "success";
    }
    @GetMapping("/v1/invalid")
    @ApiOperation("下单-本地事务-失效情况1")
    public String purchaseSimpleInvalidV1(String userId, String commodityCode, Integer orderCount) {
        businessService.purchaseSimpleInvalidV1(userId, commodityCode, orderCount);
        return "success";
    }

    @GetMapping("/v3/invalid")
    @ApiOperation("下单-本地事务-失效情况3")
    public String purchaseSimpleInvalidV3(String userId, String commodityCode, Integer orderCount) {
        businessService.purchaseSimpleInvalidV3(userId, commodityCode, orderCount);
        return "success";
    }

    @GetMapping("/v1/purchase")
    @ApiOperation("下单-分布式-无事务")
    public String purchase(String userId, String commodityCode, Integer orderCount) {
        businessService.purchase(userId, commodityCode, orderCount);
        return "success";
    }

    @GetMapping("/v2/purchase")
    @ApiOperation("下单-分布式-手动事务")
    public String purchaseV2(String userId, String commodityCode, Integer orderCount) {
        businessService.purchaseV2(userId, commodityCode, orderCount);
        return "success";
    }

    @GetMapping("/v3/purchase")
    @ApiOperation("下单-分布式-事务")
    public String purchaseV3(String userId, String commodityCode, Integer orderCount) {
        businessService.purchaseV3(userId, commodityCode, orderCount);
        return "success";
    }

}
