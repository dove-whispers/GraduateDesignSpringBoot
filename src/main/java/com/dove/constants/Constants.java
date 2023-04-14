package com.dove.constants;

/**
 * 常量
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
public interface Constants {
    /**
     * 状态码
     *
     * @author dove_whispers
     * @date 2023-04-14
     */
    interface Status {
        String CREATE = "新创建";
        String PAID = "已打款";
        String TO_BE_GM_REVIEWED = "总经理审核";
        String TO_BE_FINANCE_REVIEWED = "财务审核";
        String TERMINATED = "已终止";
        String TO_BE_MODIFIED = "待修改";
    }

    /**
     * 职位名称
     *
     * @author dove_whispers
     * @date 2023-04-14
     */
    interface Name {
        String STAFF = "普通员工";
        String DEPARTMENT_MANAGER = "部门经理";
        String GENERAL_MANAGER = "总经理";
        String FINANCIAL_SUPERVISOR = "财务";
        String TENTATIVE = "无";
    }
}
