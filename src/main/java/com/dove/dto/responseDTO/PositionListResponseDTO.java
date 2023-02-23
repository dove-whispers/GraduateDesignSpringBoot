package com.dove.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 职位列表响应dto
 *
 * @author dove_whispers
 * @date 2023-02-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取职位列表ResponseDTO")
public class PositionListResponseDTO implements Serializable {
    private static final long serialVersionUID = -6243977661133172355L;
    @ApiModelProperty(value = "职位id")
    private Integer positionId;
    @ApiModelProperty(value = "职位名称")
    private String positionName;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
