package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 部门信息请求dto
 *
 * @author dove_whispers
 * @date 2023-02-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取部门信息的RequestDTO")
public class DepartmentInfoRequestDTO implements Serializable {
    private static final long serialVersionUID = -3247824570025497634L;
    @ApiModelProperty(value = "部门id")
    private Integer depId;
    @ApiModelProperty(value = "部门名称")
    private String name;
    @ApiModelProperty(value = "部门地址")
    private String address;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
