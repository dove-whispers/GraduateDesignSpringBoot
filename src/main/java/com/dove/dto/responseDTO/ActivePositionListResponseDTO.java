package com.dove.dto.responseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 活跃位置列表响应dto
 *
 * @author dove_whispers
 * @date 2023-02-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "有效的职位列表ResponseDTO(下拉列表用)")
public class ActivePositionListResponseDTO implements Serializable {
    private static final long serialVersionUID = -1292945272524218308L;
    @ApiModelProperty(value = "职位id")
    private Integer positionId;
    @ApiModelProperty(value = "职位名称")
    private String positionName;
}
