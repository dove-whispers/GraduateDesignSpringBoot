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
 * 新增报销单请求dto
 *
 * @author dove_whispers
 * @date 2023-03-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "新增报销单的RequestDTO")
public class ExpenseReportAddRequestDTO implements Serializable {
    private static final long serialVersionUID = -5760414685692790177L;
    @ApiModelProperty(value = "报销细节")
    private List<ExpenseReportDetailRequestDTO> expenseReportDetails;
    @ApiModelProperty(value = "报销原因")
    private String cause;
    @ApiModelProperty(value = "报销单总金额")
    private BigDecimal totalAmount;
}
