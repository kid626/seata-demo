package com.bruce.seata.demo.service;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName seata-demo
 * @Date 2022/6/2 13:28
 * @Author fzh
 */
public interface StorageService {

    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);

}
