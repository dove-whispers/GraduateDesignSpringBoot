package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录请求dto
 *
 * @author dove_whispers
 * @date 2023-02-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录", description = "用户登录DTO")
public class LoginRequestDTO implements Serializable {
    private static final long serialVersionUID = 7237615621828904568L;
    @ApiModelProperty(value = "用户名", required = true, example = "dove_whispers")
    private String userName;
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;
    @ApiModelProperty(value = "是否要对用户输入的验证码进行校验", example = "false")
    private Boolean needVerify;
    @ApiModelProperty(value = "用户输入的验证码")
    private String verifyCodeActual;
}
