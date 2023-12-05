package com.bruce.seata.demo.controller;

import com.bruce.seata.demo.service.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/check")
@Api(tags = "校验接口")
public class CheckController {

    @Resource
    private BusinessService businessService;

    @GetMapping("/commodity/code/count")
    @ApiOperation("根据商品编号获取商品余量")
    public String getByCommodityCode(@RequestParam String commodityCode) {
        Integer count = businessService.getByCommodityCode(commodityCode);
        return count + "";
    }

    @GetMapping("/userId/count")
    @ApiOperation("根据用户主键获取余额")
    public String getByUserId(@RequestParam String userId) {
        Integer money = businessService.getByUserId(userId);
        return money + "";
    }

    @GetMapping("/reset")
    @ApiOperation("重置")
    public String reset() {
        businessService.reset();
        return "success";
    }

}
