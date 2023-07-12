package com.example.springCase.controller;

import com.alibaba.fastjson.JSON;
import com.example.springCase.annotation.OperateLog;
import com.example.springCase.bean.ao.LogListAO;
import com.example.springCase.bean.dto.LogListResponseDTO;
import com.example.springCase.service.LogService;
import com.rjhy.base.bean.dto.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author tao.wu
 * @date 2022/4/19
 */
@Slf4j
@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @OperateLog(content = "日志列表")
    @PostMapping(value = "log/listPage")
    public Object listPage(@RequestBody LogListAO logListAO){
        log.info("LogController listPage logListAO:{}", JSON.toJSONString(logListAO));
        LogListResponseDTO logListResponseDTO = logService.listPage(LogListAO.buildLogListDTO(logListAO));
        return ApiResult.ok(logListResponseDTO);
    }

}
