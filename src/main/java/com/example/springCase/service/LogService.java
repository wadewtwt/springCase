package com.example.springCase.service;

import com.example.springCase.bean.ao.LogListAO;
import com.example.springCase.bean.dto.LogInsertDTO;
import com.example.springCase.bean.dto.LogListDTO;
import com.example.springCase.bean.dto.LogListResponseDTO;

/**
 * @author tao.wu
 * @date 2022/4/16
 */
public interface LogService {

    /**
     * 日志列表
     * @param logListDTO
     * @return
     */
    public LogListResponseDTO listPage(LogListDTO logListDTO);

    /**
     * 插入日志
     * @param logInsertDTO
     */
    public void insertLog(LogInsertDTO logInsertDTO);
}
