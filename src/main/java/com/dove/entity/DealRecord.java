package com.dove.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作记录表(DealRecord)实体类
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("deal_record")
public class DealRecord implements Serializable {
    private static final long serialVersionUID = 727824225482021712L;
    /**
     * 操作记录表id
     */
    @TableId(type = IdType.AUTO)
    private Integer recordId;
    /**
     * 报销单id
     */
    private Integer expensiveId;
    /**
     * 操作员工id
     */
    private Integer emId;
    /**
     * 处理方式
     */
    private Date dealTime;
    /**
     * 处理方式
     */
    private String dealWay;
    /**
     * 处理结果
     */
    private String dealResult;
    /**
     * 处理结果备注
     */
    private String comment;
}

