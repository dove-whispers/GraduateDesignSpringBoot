package com.dove.dto;

import com.dove.entity.Department;
import com.dove.entity.Position;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 员工dto
 *
 * @author dove_whispers
 * @date 2023-02-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户信息DTO")
public class EmployeeDTO implements Serializable {
    /**
     * 员工id
     */
    @ApiModelProperty(value = "主键id")
    private Integer emId;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String loginName;
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Integer depId;
    /**
     * 职位id
     */
    @ApiModelProperty(value = "职位id")
    private Integer positionId;
    /**
     * 状态(0:离职 1:在职)
     */
    @ApiModelProperty(value = "状态(0:离职 1:在职)")
    private Integer status;
    /**
     * 一对一关联部门
     */
    @ApiModelProperty(value = "一对一关联部门")
    private Department department;
    /**
     * 一对一关联职位
     */
    @ApiModelProperty(value = "一对一关联职位")
    private Position position;
}
