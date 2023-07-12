package com.example.springCase.dao;

import com.example.springCase.bean.entity.PlatformDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tao.wu
 * @date 2022/4/18
 */
@Mapper
public interface PlatformDao {
    public List<PlatformDO> findAll();
}
