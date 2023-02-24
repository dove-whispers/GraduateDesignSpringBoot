package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 切换部门请求dto
 *
 * @author dove_whispers
 * @date 2023-02-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "切换单个员工状态的RequestDTO")
public class ToggleEmployeeRequestDTO implements Serializable {
    private static final long serialVersionUID = 733863333492092803L;
    @ApiModelProperty(value = "员工id")
    private Integer emId;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
