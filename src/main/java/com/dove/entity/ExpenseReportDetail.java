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
 * 报销单细节表(ExpenseReportDetail)实体类
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("expense_report_detail")
public class ExpenseReportDetail implements Serializable {
    private static final long serialVersionUID = 868414949066409016L;
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
     * 开票日期
     */
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
    private byte[] image;
}

