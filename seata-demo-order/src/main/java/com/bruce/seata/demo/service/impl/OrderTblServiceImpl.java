package com.bruce.seata.demo.service.impl;

import com.bruce.seata.demo.mapper.OrderTblMapper;
import com.bruce.seata.demo.model.po.OrderTbl;
import com.bruce.seata.demo.service.AccountService;
import com.bruce.seata.demo.service.OrderService;
import com.bruce.seata.demo.service.OrderTblService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc service 实现类
 * @ProjectName seata-demo
 * @Date 2022-6-2 13:42:13
 * @Author fanzh
 */
@Service
@DubboService
public class OrderTblServiceImpl implements OrderService, OrderTblService {

    @Resource
    private OrderTblMapper mapper;

    @DubboReference
    private AccountService accountService;

    @Override
    public void create(String userId, String commodityCode, int orderCount) {

        // 暂定单价为 1
        int orderMoney = 1 * orderCount;

        accountService.debit(userId, orderMoney);

        OrderTbl order = new OrderTbl();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(orderMoney);
        mapper.insert(order);

    }

    @Override
    public String createLocal(String userId, String commodityCode, int orderCount) {
        // 暂定单价为 1
        int orderMoney = 1 * orderCount;

        OrderTbl order = new OrderTbl();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(orderMoney);
        mapper.insert(order);
        return order.getId() + "";
    }

    @Override
    public void unCreate(String orderId) {
        mapper.deleteById(orderId);
    }
}
