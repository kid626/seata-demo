package com.bruce.seata.demo.service;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc service 层
 * @ProjectName seata-demo
 * @Date 2022-6-2 13:36:22
 * @Author fanzh
 */
public interface AccountTblService  {

    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);

    /**
     * 从用户账户中借出
     */
    void unDebit(String userId, int money);

}
