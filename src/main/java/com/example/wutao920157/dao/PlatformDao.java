package com.example.wutao920157.dao;

import com.example.wutao920157.bean.entity.PlatformDO;
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
