package com.bruce.seata.demo.controller;

import com.bruce.seata.demo.service.AccountTblService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName seata-demo
 * @Date 2023/12/3 20:09
 * @Author Bruce
 */
@RequestMapping("/")
@RestController
public class AccountController {

    @Resource
    private AccountTblService accountTblService;

    @GetMapping("/debit")
    public String debit(String userId, Integer money) {
        accountTblService.debit(userId, money);
        return "success";
    }

    @GetMapping("/undebit")
    public String unDebit(String userId, Integer money) {
        accountTblService.unDebit(userId, money);
        return "success";
    }


}
