package com.bruce.seata.demo.service;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName seata-demo
 * @Date 2022/6/2 13:28
 * @Author fzh
 */
public interface OrderService {

    /**
     * 创建订单
     */
    void create(String userId, String commodityCode, int orderCount);
}
