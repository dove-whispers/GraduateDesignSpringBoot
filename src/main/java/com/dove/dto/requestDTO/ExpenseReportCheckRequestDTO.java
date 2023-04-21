package com.dove.dto.requestDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 报销单查重请求dto
 *
 * @author dove_whispers
 * @date 2023-04-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "报销单查重的RequestDTO")
public class ExpenseReportCheckRequestDTO implements Serializable {
    private static final long serialVersionUID = -4309740792344627027L;
    @ApiModelProperty(value = "发票代码")
    private String code;
    @ApiModelProperty(value = "发票号码")
    private String num;
}
