package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 员工信息请求dto
 *
 * @author dove_whispers
 * @date 2023-02-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取员工信息的RequestDTO")
public class EmployeeInfoRequestDTO implements Serializable {
    private static final long serialVersionUID = -5660422344697028207L;
    @ApiModelProperty("员工id")
    private Integer emId;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("用户名")
    private String loginName;
    @ApiModelProperty("部门id")
    private Integer depId;
    @ApiModelProperty("职位id")
    private Integer positionId;
    @ApiModelProperty("状态(0:离职 1:在职)")
    private Integer status;
}
