package com.dove.dto.requestDTO;

import com.dove.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 职位列表请求dto
 *
 * @author dove_whispers
 * @date 2023-02-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取职位列表的RequestDTO")
public class PositionListRequestDTO extends PageDTO implements Serializable {
    private static final long serialVersionUID = -3969301302288368351L;
    @ApiModelProperty(value = "职位名称")
    private String positionName;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
