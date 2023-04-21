package com.dove.dto.responseDTO;

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
 * 报销单细节优化
 *
 * @author dove_whispers
 * @date 2023-04-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "优化image后的报销单细节的DTO")
public class ExpenseReportDetailOptimized implements Serializable {
    private static final long serialVersionUID = 3979096651459953187L;
    /**
     * 报销单细节表id
     */
    private Integer expensiveDetailId;
    /**
     * 报销单id
     */
    private Integer expensiveId;
    /**
     * 报销项目
     */
    private String item;
    /**
     * 开票日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;
    /**
     * 类别
     */
    private String type;
    /**
     * 发票代码
     */
    private String code;
    /**
     * 发票号码
     */
    private String num;
    /**
     * 费用明细
     */
    private BigDecimal amount;
    /**
     * 费用备注
     */
    private String comment;
    /**
     * 报销单图片
     */
    private String image;
}
