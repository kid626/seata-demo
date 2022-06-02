package com.bruce.seata.demo.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName seata-demo
 * @Date 2022/6/2 14:11
 * @Author fzh
 */
@Service
public class BusinessService {

    @DubboReference
    private StorageService storageService;

    @DubboReference
    private OrderService orderService;

    /**
     * 采购
     */
    @GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode, orderCount);
        orderService.create(userId, commodityCode, orderCount);
    }


}
