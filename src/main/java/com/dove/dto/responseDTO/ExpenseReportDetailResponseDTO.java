package com.dove.dto.responseDTO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 报销单细节的响应dto
 *
 * @author dove_whispers
 * @date 2023-04-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "报销单细节的ResponseDTO")
public class ExpenseReportDetailResponseDTO implements Serializable {
    private static final long serialVersionUID = 1337420169617151771L;
    List<ExpenseReportDetailOptimized> expenseReportDetails;
    private String cause;
    private BigDecimal totalAmount;
}
