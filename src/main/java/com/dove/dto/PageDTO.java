package com.dove.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页dto(用于继承)
 *
 * @author dove_whispers
 * @date 2023-02-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页DTO")
public class PageDTO implements Serializable {
    private static final long serialVersionUID = 2313987067709328120L;
    @ApiModelProperty(value = "数据总量")
    private Long total;
    @ApiModelProperty(value = "总页数")
    private Long pages;
    @ApiModelProperty(value = "当前页")
    private Long current = 1L;
    @ApiModelProperty(value = "每页大小")
    private Long size = 10L;
}
