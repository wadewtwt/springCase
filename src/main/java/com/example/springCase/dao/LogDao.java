package com.example.springCase.dao;

import com.example.springCase.bean.entity.LogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tao.wu
 * @date 2022/4/16
 */
@Mapper
public interface LogDao {

    public List<LogDO> findAll();
    /**
     * 插入日志
     * @param logDo
     * @return
     */
    public Integer insertLog(LogDO logDo);
}
