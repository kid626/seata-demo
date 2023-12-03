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
 * @Date 2022-6-2 13:42:26
 * @Author fanzh
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("storage_tbl")
@ApiModel(value = "StorageTbl对象", description = "")
public class StorageTbl implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "")
    private String commodityCode;

    @ApiModelProperty(value = "")
    private Integer count;


}