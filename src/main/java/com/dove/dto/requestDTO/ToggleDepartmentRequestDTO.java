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
@ApiModel(description = "切换单个部门状态的RequestDTO")
public class ToggleDepartmentRequestDTO implements Serializable {
    private static final long serialVersionUID = 7468042292214962421L;
    @ApiModelProperty(value = "部门id")
    private Integer depId;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
