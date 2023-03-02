package com.dove.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 报销单表(ExpenseReport)实体类
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("expense_report")
public class ExpenseReport implements Serializable {
    private static final long serialVersionUID = 965476388182794094L;
    /**
     * 报销单id
     */
    @TableId(type = IdType.AUTO)
    private Integer expenseId;
    /**
     * 报销原因
     */
    private String cause;
    /**
     * 创建人
     */
    private Integer emId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 待处理人
     */
    private Integer nextDealEm;
    /**
     * 报销总金额
     */
    private BigDecimal totalAmount;
    /**
     * 状态
     */
    private String status;
}
