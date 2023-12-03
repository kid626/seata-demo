package com.bruce.seata.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.seata.demo.mapper.StorageTblMapper;
import com.bruce.seata.demo.model.po.StorageTbl;
import com.bruce.seata.demo.service.TccService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName seata-demo
 * @Date 2023/12/3 19:04
 * @Author Bruce
 */
@Service
@Slf4j
public class TccServiceImpl implements TccService {

    @Resource
    private StorageTblMapper storageTblMapper;
    @Resource
    private RestTemplate restTemplate;

    private final Map<String, String> map = new HashMap<>();

    @Override
    public String prepare(BusinessActionContext actionContext, String commodityCode, Integer count, String userId) {
        log.info("开始TCC xid:" + RootContext.getXID());
        actionContext.getActionContext().put("count", count);
        actionContext.getActionContext().put("commodityCode", commodityCode);
        actionContext.getActionContext().put("userId", userId);
        // 1 扣除商品数量
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
        // 2 下单
        String orderId = restTemplate.getForObject("http://127.0.0.1:8092/order/create?userId=" + userId + "&money=" + count + "&commodityCode=" + commodityCode, String.class);
        map.put(actionContext.getBranchId() + "", orderId);
        // 3 用户扣款
        restTemplate.getForObject("http://127.0.0.1:8090/account/debit?userId=" + userId + "&money=" + count, String.class);
        return "执行完毕！";
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        log.info("xid = " + actionContext.getXid() + "提交成功");
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        // 获取下单时的提交参数
        String commodityCode = actionContext.getActionContext("commodityCode").toString();
        String orderId = map.get(actionContext.getBranchId() + "");
        Integer count = Integer.parseInt(actionContext.getActionContext("count").toString());
        // 恢复库存
        LambdaQueryWrapper<StorageTbl> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(StorageTbl::getCommodityCode, commodityCode);
        StorageTbl storageTbl = storageTblMapper.selectOne(lambdaQuery);
        if (storageTbl == null) {
            throw new RuntimeException("商品不存在!");
        }
        storageTbl.setCount(storageTbl.getCount() + count);
        storageTblMapper.updateById(storageTbl);
        restTemplate.execute("http://127.0.0.1:8092/order/uncreate?orderId=" + orderId, HttpMethod.GET, null, null, new HashMap<>());
        map.remove(actionContext.getXid());
        return true;
    }
}
