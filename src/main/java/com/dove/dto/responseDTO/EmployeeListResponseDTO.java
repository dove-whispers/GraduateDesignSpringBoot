package com.dove.dto.responseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 职位列表响应dto
 *
 * @author dove_whispers
 * @date 2023-02-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取员工列表ResponseDTO")
public class EmployeeListResponseDTO implements Serializable {
    private static final long serialVersionUID = -5130126866582147728L;
    @ApiModelProperty(value = "员工id")
    private Integer emId;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "手机")
    private String loginName;
    @ApiModelProperty(value = "部门id")
    private Integer depId;
    @ApiModelProperty(value = "职位id")
    private Integer positionId;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
