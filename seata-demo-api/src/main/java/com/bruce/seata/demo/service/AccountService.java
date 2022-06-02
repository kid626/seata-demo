package com.bruce.seata.demo.service;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName seata-demo
 * @Date 2022/6/2 13:28
 * @Author fzh
 */
public interface AccountService {

    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);

}
