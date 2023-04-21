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

    /**
     * 处理方式
     *
     * @author dove_whispers
     * @date 2023-04-17
     */
    interface Way {
        String CREATE = "创建";
        String AUDIT = "审核";
        String EDIT = "修改";
    }

    /**
     * 处理结果
     *
     * @author dove_whispers
     * @date 2023-04-17
     */
    interface Result {
        String CREATED = "已创建";
        String PASS = "通过";
        String REPULSE = "打回";
        String REMITTANCE = "已打款";
        String TERMINATED = "已终止";
        String EDITED = "已修改";
    }

    /**
     * 报销进度
     * 1创建 2部门经理审核通过 3总经理审核通过 4财务打款
     * 5部门经理打回 6总经理打回 7财务打回
     * 8部门经理终止 9总经理终止 10财务终止
     */
    interface Step {
        Integer CREATED = 1;
        Integer PASSED_BY_DEPARTMENT_MANAGER = 2;
        Integer PASSED_BY_GENERAL_MANAGER = 3;
        Integer PASSED_BY_FINANCIAL_SUPERVISOR = 4;
        Integer REPULSED_BY_DEPARTMENT_MANAGER = 5;
        Integer REPULSED_BY_GENERAL_MANAGER = 6;
        Integer REPULSED_BY_FINANCIAL_SUPERVISOR = 7;
        Integer TERMINATED_BY_DEPARTMENT_MANAGER = 8;
        Integer TERMINATED_BY_GENERAL_MANAGER = 9;
        Integer TERMINATED_BY_FINANCIAL_SUPERVISOR = 10;
    }
}
