package com.bruce.seata.demo.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc 实体类
 * @ProjectName seata-demo
 * @Date 2022-6-2 13:42:13
 * @Author fanzh
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("order_tbl")
@ApiModel(value = "OrderTbl对象", description = "")
public class OrderTbl implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "")
            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "")
    private String userId;

    @ApiModelProperty(value = "")
    private String commodityCode;

    @ApiModelProperty(value = "")
    private Integer count;

    @ApiModelProperty(value = "")
    private Integer money;


}