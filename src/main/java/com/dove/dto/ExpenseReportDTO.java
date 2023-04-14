package com.dove.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dove.entity.Department;
import com.dove.entity.Employee;
import com.dove.entity.Position;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 报销单dto
 *
 * @author dove_whispers
 * @date 2023-04-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "报销单信息DTO")
public class ExpenseReportDTO implements Serializable {
    private static final long serialVersionUID = 4468636222137256358L;
    /**
     * 报销单id
     */
    @ApiModelProperty(value = "主键id")
    private Integer expenseId;
    /**
     * 报销原因
     */
    @ApiModelProperty(value = "报销原因")
    private String cause;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人id")
    private Integer emId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 截止时间
     */
    @ApiModelProperty(value = "截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;
    /**
     * 待处理人
     */
    @ApiModelProperty(value = "待处理人id")
    private Integer nextDealEm;
    /**
     * 报销总金额
     */
    @ApiModelProperty(value = "报销总金额")
    private BigDecimal totalAmount;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;
    /**
     * 一对一关联创建人
     */
    @ApiModelProperty(value = "一对一关联创建人")
    private Employee createEmployee;
    /**
     * 一对一关联处理人
     */
    @ApiModelProperty(value = "一对一关联处理人")
    private Employee nextDealEmployee;
}
