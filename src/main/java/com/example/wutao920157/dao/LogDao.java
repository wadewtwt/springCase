package com.example.wutao920157.dao;

import com.example.wutao920157.bean.entity.LogDO;
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
