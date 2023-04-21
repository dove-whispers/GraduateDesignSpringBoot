package com.dove.dto.requestDTO;

import com.dove.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 员工列表请求dto
 *
 * @author dove_whispers
 * @date 2023-02-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取员工列表的RequestDTO")
public class EmployeeListRequestDTO extends PageDTO implements Serializable {
    private static final long serialVersionUID = -7601935052854149512L;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "部门")
    private Integer depId;
    @ApiModelProperty(value = "职位")
    private Integer positionId;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
