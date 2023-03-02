package com.dove.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 报销单细节表(ExpenseReportDetail)实体类
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("expense_report_detail")
public class ExpenseReportDetail implements Serializable {
    private static final long serialVersionUID = 868414949066409016L;
    /**
     * 报销单细节表id
     */
    @TableId(type = IdType.AUTO)
    private Integer expensiveDetailId;
    /**
     * 报销单id
     */
    private Integer expensiveId;
    /**
     * 报销项目
     */
    private String item;
    /**
     * 开票日期
     */
    private Date time;
    /**
     * 类别
     */
    private String type;
    /**
     * 发票代码
     */
    private String code;
    /**
     * 发票号码
     */
    private String num;
    /**
     * 费用明细
     */
    private BigDecimal amount;
    /**
     * 费用备注
     */
    private String comment;
    /**
     * 报销单图片
     */
    private byte[] image;


    public Integer getExpensiveDetailId() {
        return expensiveDetailId;
    }

    public void setExpensiveDetailId(Integer expensiveDetailId) {
        this.expensiveDetailId = expensiveDetailId;
    }

    public Integer getExpensiveId() {
        return expensiveId;
    }

    public void setExpensiveId(Integer expensiveId) {
        this.expensiveId = expensiveId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}

