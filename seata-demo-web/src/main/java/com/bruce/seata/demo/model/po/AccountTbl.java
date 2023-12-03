package com.bruce.seata.demo.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc 实体类
 * @ProjectName seata-demo
 * @Date 2022-6-2 13:36:22
 * @Author fanzh
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("account_tbl")
@ApiModel(value = "AccountTbl对象", description = "")
public class AccountTbl implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "")
    private String userId;

    @ApiModelProperty(value = "")
    private Integer money;


}