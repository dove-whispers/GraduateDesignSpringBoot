package com.dove.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 部门
 *
 * @author dove_whispers
 * @date 2023-02-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {
    private static final long serialVersionUID = -45540149693001688L;
    /**
     * 部门id
     */
    private Integer depId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 办公地点
     */
    private String address;
    /**
     * 1代表有效,0代表无效
     */
    private Integer status;
}

