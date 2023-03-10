package com.dove.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 职位
 *
 * @author dove_whispers
 * @date 2023-02-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position implements Serializable {
    private static final long serialVersionUID = -49746560125483815L;
    /**
     * 职位表id
     */
    private Integer positionId;
    /**
     * 职位名
     */
    private String positionName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态 1:有效 0:无效
     */
    private Integer status;
}

