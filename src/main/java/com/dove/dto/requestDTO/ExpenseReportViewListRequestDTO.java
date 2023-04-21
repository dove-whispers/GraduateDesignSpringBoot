package com.dove.dto.requestDTO;

import com.dove.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 报销单列表请求dto
 *
 * @author dove_whispers
 * @date 2023-04-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "获取报销单列表的RequestDTO")
public class ExpenseReportViewListRequestDTO extends PageDTO implements Serializable {
  private static final long serialVersionUID = 1158989695581993211L;
  @ApiModelProperty(value = "报销人")
  private String name;
  @ApiModelProperty(value = "报销原因")
  private String cause;
}
