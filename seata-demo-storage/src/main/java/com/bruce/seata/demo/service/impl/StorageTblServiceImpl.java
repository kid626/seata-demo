package com.bruce.seata.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.seata.demo.service.StorageService;
import com.bruce.seata.demo.mapper.StorageTblMapper;
import com.bruce.seata.demo.model.po.StorageTbl;
import com.bruce.seata.demo.service.StorageTblService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc service 实现类
 * @ProjectName seata-demo
 * @Date 2022-6-2 13:42:26
 * @Author fanzh
 */
@Service
@DubboService
public class StorageTblServiceImpl implements StorageService, StorageTblService {

    @Resource
    private StorageTblMapper mapper;

    @Override
    public void deduct(String commodityCode, int count) {
        LambdaQueryWrapper<StorageTbl> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(StorageTbl::getCommodityCode, commodityCode);
        StorageTbl storageTbl = mapper.selectOne(lambdaQuery);
        if (storageTbl == null) {
            throw new RuntimeException("商品不存在!");
        }
        if (storageTbl.getCount() < count) {
            throw new RuntimeException("商品库存不足");
        }
        storageTbl.setCount(storageTbl.getCount() - count);
        mapper.updateById(storageTbl);
    }

    @Override
    public void undoDeduct(String commodityCode, int count) {
        LambdaQueryWrapper<StorageTbl> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(StorageTbl::getCommodityCode, commodityCode);
        StorageTbl storageTbl = mapper.selectOne(lambdaQuery);
        if (storageTbl == null) {
            throw new RuntimeException("商品不存在!");
        }
        storageTbl.setCount(storageTbl.getCount() + count);
        mapper.updateById(storageTbl);
    }
}
