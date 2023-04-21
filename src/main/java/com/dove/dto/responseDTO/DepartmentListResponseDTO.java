package com.dove.dto.responseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 部门列表响应dto
 *
 * @author dove_whispers
 * @date 2023-02-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取部门列表ResponseDTO")
public class DepartmentListResponseDTO implements Serializable {
    private static final long serialVersionUID = 6746189630143569672L;
    @ApiModelProperty(value = "部门id")
    private Integer depId;
    @ApiModelProperty(value = "部门名称")
    private String name;
    @ApiModelProperty(value = "办公地点")
    private String address;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
