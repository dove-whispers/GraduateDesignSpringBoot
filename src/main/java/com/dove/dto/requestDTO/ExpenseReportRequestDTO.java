package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 报销单请求dto
 *
 * @author dove_whispers
 * @date 2023-03-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "新增报销单的RequestDTO")
public class ExpenseReportRequestDTO implements Serializable {
    private static final long serialVersionUID = -5760414685692790177L;
    List<ExpenseReportDetailRequestDTO> expenseReportDetails;
    String cause;
    BigDecimal totalAmount;
}
