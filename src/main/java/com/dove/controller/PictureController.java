package com.dove.controller;

import com.dove.property.KeyProperty;
import com.dove.util.IdentifyUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片识别控制器
 *
 * @author dove_whispers
 * @date 2023-04-25
 */
@Api(tags = "图片识别")
@RestController
@RequestMapping("/picture")
public class PictureController extends BaseController {
    @Resource
    private KeyProperty keyProperty;

    @ApiOperation(value = "获取图片识别数据")
    @PostMapping("/getImgInfo")
    public Map<String, Object> getImgInfo(String base64) {
        Map<String, Object> map = new HashMap<>(2);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String identifyInvoice = IdentifyUtil.identifyInvoice(base64, keyProperty.getAk(), keyProperty.getSk());
            map.put("success", true);
            map.put("data", objectMapper.readValue(identifyInvoice, Object.class));
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }
}
