package com.dove.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 报销单细节表(ExpenseReportDetail)实体类
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("expense_report_detail")
public class ExpenseReportDetail implements Serializable {
    private static final long serialVersionUID = -99587584206781056L;
    /**
     * 报销单细节表id
     */
    @TableId(type = IdType.AUTO)
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
     * 费用明细
     */
    private BigDecimal amount;
    /**
     * 费用备注
     */
    private String comment;
}

