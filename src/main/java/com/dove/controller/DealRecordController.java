package com.dove.controller;

import com.dove.service.impl.DealRecordServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 操作记录表(DealRecord)表控制层
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
@Api(tags = "操作记录管理模块")
@Controller
@RequestMapping
@Slf4j
public class DealRecordController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private DealRecordServiceImpl dealRecordService;
}

