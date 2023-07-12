package com.example.springCase.dao;

import com.example.springCase.bean.entity.SensitiveWordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tao.wu
 * @date 2022/4/22
 */
@Mapper
public interface SensitiveWordDao {
    /**
     * 查询所有
     * @return
     */
    public List<SensitiveWordDO> findAll();
}
