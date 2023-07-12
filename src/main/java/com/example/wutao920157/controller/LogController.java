package com.example.wutao920157.controller;

import com.example.wutao920157.annotation.OperateLog;
import com.example.wutao920157.bean.ao.LogListAO;
import com.example.wutao920157.bean.dto.LogListResponseDTO;
import com.example.wutao920157.service.LogService;
import com.rjhy.base.bean.dto.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author tao.wu
 * @date 2022/4/19
 */
@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @OperateLog(content = "日志列表")
    @PostMapping(value = "log/listPage")
    public Object listPage(@RequestBody LogListAO logListAO){
        LogListResponseDTO logListResponseDTO = logService.listPage(LogListAO.buildLogListDTO(logListAO));
        return ApiResult.ok(logListResponseDTO);
    }

}