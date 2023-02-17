package com.dove.dto.requestDTO;

import com.dove.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 部门列表请求dto
 *
 * @author dove_whispers
 * @date 2023-02-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取部门列表的RequestDTO")
public class DepartmentListRequestDTO extends PageDTO implements Serializable {
    private static final long serialVersionUID = -5025737214684222583L;
    @ApiModelProperty(value = "部门名称",example = "")
    private String name;
    @ApiModelProperty(value = "部门地址",example = "")
    private String address;
    @ApiModelProperty(value = "状态:1有效,0无效",example = "")
    private Integer status;
}
