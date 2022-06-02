package com.bruce.seata.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.seata.demo.service.AccountService;
import com.bruce.seata.demo.mapper.AccountTblMapper;
import com.bruce.seata.demo.model.po.AccountTbl;
import com.bruce.seata.demo.service.AccountTblService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc service 实现类
 * @ProjectName seata-demo
 * @Date 2022-6-2 13:36:22
 * @Author fanzh
 */
@Service
@DubboService
public class AccountTblServiceImpl implements AccountService, AccountTblService {

    @Resource
    private AccountTblMapper mapper;

    @Override
    public void debit(String userId, int money) {
        LambdaQueryWrapper<AccountTbl> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(AccountTbl::getUserId, userId);
        AccountTbl accountTbl = mapper.selectOne(lambdaQuery);
        if (accountTbl == null) {
            throw new RuntimeException("用户不存在!");
        }
        if (accountTbl.getMoney() < money) {
            throw new RuntimeException("用户余额不足!");
        }
        accountTbl.setMoney(accountTbl.getMoney() - money);
        mapper.updateById(accountTbl);

    }
}
