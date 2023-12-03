package com.bruce.seata.demo.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc service 层
 * @ProjectName seata-demo
 * @Date 2022-6-2 13:42:26
 * @Author fanzh
 */
@LocalTCC
public interface TccService {

    @TwoPhaseBusinessAction(name = "prepare", commitMethod = "commit", rollbackMethod = "rollback")
    String prepare(BusinessActionContext actionContext,
                   @BusinessActionContextParameter(paramName = "commodityCode") String commodityCode,
                   @BusinessActionContextParameter(paramName = "count") Integer count,
                   @BusinessActionContextParameter(paramName = "userId") String userId);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
