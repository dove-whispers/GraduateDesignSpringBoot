package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 审核报销单操作记录请求dto
 *
 * @author dove_whispers
 * @date 2023-04-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取审核报销单信息的RequestDTO")
public class AuditDealRecordRequestDTO implements Serializable {
    private static final long serialVersionUID = -6702285301182015430L;
    @ApiModelProperty(value = "处理方式")
    private String way;
    @ApiModelProperty(value = "处理结果备注")
    private String comment;
    @ApiModelProperty(value = "报销单id")
    private Integer expenseId;
}
