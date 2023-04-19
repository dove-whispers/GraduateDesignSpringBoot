package com.dove.dto;

import com.dove.entity.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作记录dto
 *
 * @author dove_whispers
 * @date 2023-04-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "操作记录DTO")
public class DealRecordDTO implements Serializable {
    private static final long serialVersionUID = -2900050215797040431L;
    /**
     * 操作记录id
     */
    @ApiModelProperty(value = "主键id")
    private Integer recordId;
    /**
     * 报销单id
     */
    @ApiModelProperty(value = "报销单id")
    private Integer expensiveId;
    /**
     * 操作员工id
     */
    @ApiModelProperty(value = "操作员工id")
    private Integer emId;
    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dealTime;
    /**
     * 处理方式
     */
    @ApiModelProperty(value = "处理方式")
    private String dealWay;
    /**
     * 处理结果
     */
    @ApiModelProperty(value = "处理结果")
    private String dealResult;
    /**
     * 处理结果备注
     */
    @ApiModelProperty(value = "处理结果备注")
    private String comment;
    /**
     * 一对一关联操作员工
     */
    @ApiModelProperty(value = "一对一关联操作员工")
    private Employee employee;
}
