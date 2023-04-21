package com.dove.dto.responseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 活跃部门列表响应dto
 *
 * @author dove_whispers
 * @date 2023-02-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "有效的部门列表ResponseDTO(下拉列表用)")
public class ActiveDepartmentListResponseDTO implements Serializable {
    private static final long serialVersionUID = -7312026831623421240L;
    @ApiModelProperty(value = "部门id")
    private Integer depId;
    @ApiModelProperty(value = "部门名称")
    private String name;
}
