package com.dove.dto.requestDTO;

import com.dove.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 主页报销单列表请求dto
 *
 * @author dove_whispers
 * @date 2023-04-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取部门列表的RequestDTO")
public class ExpenseReportMainListRequestDTO extends PageDTO implements Serializable {
    private static final long serialVersionUID = -4252463239131141128L;
    @ApiModelProperty(value = "报销人")
    private String name;
    @ApiModelProperty(value = "报销原因")
    private String cause;
    @ApiModelProperty(value = "状态:1有效,0无效")
    private Integer status;
}
