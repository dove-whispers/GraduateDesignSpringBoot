package com.dove.constants;

/**
 * 常量
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
public interface Constants {
    /**
     * 返回码
     *
     * @author dove_whispers
     * @date 2022/01/14
     */
    interface Code {
        //新创建
        int CREATE = 1;
        //已提交部门经理(待部门经理审核)
        int SUBMIT_TO_DM = 2;
        //部门经理审核打回(待修改)
        int BACK_FROM_DM = 3;
        //部门经理审核终止
        int TERMINATE_BY_DM = 4;
        //已提交总经理(待总经理审核)(部门经理审核通过)
        int SUBMIT_TO_GM = 5;
        //总经理审核打回(待修改)
        int BACK_FROM_GM = 6;
        //总经理审核终止
        int TERMINATE_BY_GM = 7;
        //已提交财务
        int SUBMIT_TO_FINANCE = 8;
        //已打款
        int PAID = 9;
    }
}
