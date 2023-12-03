package com.bruce.seata.demo.service;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc service 层
 * @ProjectName seata-demo
 * @Date 2022-6-2 13:42:13
 * @Author fanzh
 */
public interface OrderTblService {

    String createLocal(String userId, String commodityCode, int orderCount);


    void unCreate(String orderId);

}
