package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 检查密码请求dto
 *
 * @author dove_whispers
 * @date 2023-04-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "检查密码", description = "用户修改密码DTO")
public class PasswordRequestDTO implements Serializable {
    private static final long serialVersionUID = -8374558762324639697L;
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
