package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 修改报销单请求dto
 *
 * @author dove_whispers
 * @date 2023-05-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "修改报销单的RequestDTO")
public class ExpenseReportUpdateRequestDTO implements Serializable {
    private static final long serialVersionUID = -2488692551869273067L;
    @ApiModelProperty(value = "报销单id")
    private Integer expenseId;
    @ApiModelProperty(value = "报销细节")
    private List<ExpenseReportDetailRequestDTO> expenseReportDetails;
    @ApiModelProperty(value = "报销原因")
    private String cause;
    @ApiModelProperty(value = "报销单总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "处理结果备注")
    private String comment;
}
