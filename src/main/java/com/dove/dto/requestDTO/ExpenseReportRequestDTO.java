package com.dove.dto.requestDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
    private static final long serialVersionUID = -6352088734105011056L;
    private Integer expensiveDetailId;
    private Integer expensiveId;
    private String item;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;
    private String type;
    private String code;
    private String num;
    private BigDecimal amount;
    private String comment;
    private String image;
}
