package com.bruce.seata.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.seata.demo.mapper.AccountTblMapper;
import com.bruce.seata.demo.mapper.OrderTblMapper;
import com.bruce.seata.demo.mapper.StorageTblMapper;
import com.bruce.seata.demo.model.po.AccountTbl;
import com.bruce.seata.demo.model.po.OrderTbl;
import com.bruce.seata.demo.model.po.StorageTbl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName seata-demo
 * @Date 2022/6/2 14:11
 * @Author fzh
 */
@Service
@Slf4j
public class BusinessService {

    @DubboReference
    private StorageService storageService;
    @DubboReference
    private OrderService orderService;

    @Resource
    private StorageTblMapper storageTblMapper;
    @Resource
    private AccountTblMapper accountTblMapper;
    @Resource
    private OrderTblMapper orderTblMapper;

    /**
     * 下单，本地事务
     */
    @Transactional(rollbackFor = Exception.class)
    public void purchaseSimple(String userId, String commodityCode, Integer orderCount) {
        // 1 扣除商品数量
        deduct(commodityCode, orderCount);
        // 2 用户扣款
        debit(userId, orderCount);
        // 3 新增订单
        create(userId, commodityCode, orderCount);

    }

    /**
     * 下单，本地事务 失效 情况1
     */
    public void purchaseSimpleInvalidV1(String userId, String commodityCode, Integer orderCount) {
        // TODO check param
        purchaseSimple(userId, commodityCode, orderCount);
        // TODO 发送下单成功消息
    }

    /**
     * 下单，本地事务 失效 情况2
     */
    @Transactional(rollbackFor = SQLException.class)
    public void purchaseSimpleInvalidV2(String userId, String commodityCode, Integer orderCount) {
        // 1 扣除商品数量
        deduct(commodityCode, orderCount);
        // 2 用户扣款
        debit(userId, orderCount);
        // 3 新增订单
        create(userId, commodityCode, orderCount);
    }

    @Transactional(rollbackFor = Exception.class)
    public void purchaseSimpleInvalidV3(String userId, String commodityCode, Integer orderCount) {
        // 1 扣除商品数量
        deduct(commodityCode, orderCount);
        try {
            // 2 用户扣款
            debit(userId, orderCount);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        // 3 新增订单
        create(userId, commodityCode, orderCount);


    }

    /**
     * 扣除商品数量
     */
    private void deduct(String commodityCode, Integer count) {
        LambdaQueryWrapper<StorageTbl> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(StorageTbl::getCommodityCode, commodityCode);
        StorageTbl storageTbl = storageTblMapper.selectOne(lambdaQuery);
        if (storageTbl == null) {
            throw new RuntimeException("商品不存在!");
        }
        if (storageTbl.getCount() < count) {
            throw new RuntimeException("商品库存不足");
        }
        storageTbl.setCount(storageTbl.getCount() - count);
        storageTblMapper.updateById(storageTbl);
    }

    /**
     * 用户扣款
     */
    private void debit(String userId, Integer money) {
        LambdaQueryWrapper<AccountTbl> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(AccountTbl::getUserId, userId);
        AccountTbl accountTbl = accountTblMapper.selectOne(lambdaQuery);
        if (accountTbl == null) {
            throw new RuntimeException("用户不存在!");
        }
        if (accountTbl.getMoney() < money) {
            throw new RuntimeException("用户余额不足!");
        }
        accountTbl.setMoney(accountTbl.getMoney() - money);
        accountTblMapper.updateById(accountTbl);
    }

    /**
     * 新增订单
     */
    private void create(String userId, String commodityCode, Integer orderCount) {
        // 单价定为 1
        int orderMoney = 1 * orderCount;
        OrderTbl order = new OrderTbl();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(orderMoney);
        orderTblMapper.insert(order);
    }


    public Integer getByCommodityCode(String commodityCode) {
        LambdaQueryWrapper<StorageTbl> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(StorageTbl::getCommodityCode, commodityCode);
        StorageTbl storageTbl = storageTblMapper.selectOne(lambdaQuery);
        if (storageTbl == null) {
            throw new RuntimeException("商品不存在!");
        }
        return storageTbl.getCount();
    }

    public Integer getByUserId(String userId) {
        LambdaQueryWrapper<AccountTbl> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(AccountTbl::getUserId, userId);
        AccountTbl accountTbl = accountTblMapper.selectOne(lambdaQuery);
        if (accountTbl == null) {
            throw new RuntimeException("用户不存在!");
        }
        return accountTbl.getMoney();
    }

    /**
     * 下单分布式  无事务
     */
    // @GlobalTransactional(name = "my_test_tx_group")
    public void purchase(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode, orderCount);
        orderService.create(userId, commodityCode, orderCount);
    }

    /**
     * 下单分布式手动事务
     */
    @Transactional(rollbackFor = Exception.class)
    public void purchaseV2(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode, orderCount);
        try {
            orderService.create(userId, commodityCode, orderCount);
        } catch (Exception e) {
            storageService.undoDeduct(commodityCode, orderCount);
            throw e;
        }

    }

    /**
     * 下单分布式事务
     */
    @GlobalTransactional(name = "my_test_tx_group")
    public void purchaseV3(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode, orderCount);
        orderService.create(userId, commodityCode, orderCount);
    }

}
