package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 位置信息请求dto
 *
 * @author dove_whispers
 * @date 2023-02-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取职位信息的RequestDTO")
public class PositionInfoRequestDTO implements Serializable {
    private static final long serialVersionUID = -2757138289377637072L;
    @ApiModelProperty(value = "职位id")
    private Integer positionId;
    @ApiModelProperty(value = "职位名称")
    private String positionName;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
