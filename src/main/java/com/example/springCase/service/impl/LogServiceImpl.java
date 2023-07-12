package com.example.springCase.service.impl;

import com.example.springCase.bean.constant.error.ErrorEnum;
import com.example.springCase.bean.dto.LogInsertDTO;
import com.example.springCase.bean.dto.LogListDTO;
import com.example.springCase.bean.dto.LogListResponseDTO;
import com.example.springCase.bean.entity.LogDO;
import com.example.springCase.dao.LogDao;
import com.example.springCase.exception.BizException;
import com.example.springCase.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rjhy.base.error.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tao.wu
 * @date 2022/4/16
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public LogListResponseDTO listPage(LogListDTO logListDTO) {
        PageHelper.startPage(logListDTO.getPageNo(), logListDTO.getPageSize());
        List<LogDO> logDOS = logDao.findAll();
        PageInfo<LogDO> logDoPageInfo = new PageInfo<>(logDOS);

        LogListResponseDTO logListResponseDTO = new LogListResponseDTO();
        logListResponseDTO.setTotalNum(logDoPageInfo.getTotal());
        logListResponseDTO.setLogDOS(logDoPageInfo.getList());
        return logListResponseDTO;
    }

    @Override
    public void insertLog(LogInsertDTO logInsertDTO) {
        LogDO logDo = new LogDO();
        logDo.setUsername(logInsertDTO.getUsername());
        logDo.setContent(logInsertDTO.getContent());
        logDo.setAddress(logInsertDTO.getAddress());
        Integer count = logDao.insertLog(logDo);
        if(count == 0){
            throw  new BizException(ErrorEnum.ERROR_INSERT_LOG.code(), ErrorEnum.ERROR_INSERT_LOG.message());
        }
    }
}
