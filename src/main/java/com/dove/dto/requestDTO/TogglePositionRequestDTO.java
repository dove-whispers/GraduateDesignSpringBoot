package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 切换位置请求dto
 *
 * @author dove_whispers
 * @date 2023-02-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "切换单个职位状态的RequestDTO")
public class TogglePositionRequestDTO implements Serializable {
    private static final long serialVersionUID = 170068117115799721L;
    @ApiModelProperty(value = "职位id")
    private Integer positionId;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
