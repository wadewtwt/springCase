package com.example.wutao920157.service.impl;

import com.example.wutao920157.bean.entity.PlatformDO;
import com.example.wutao920157.dao.PlatformDao;
import com.example.wutao920157.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tao.wu
 * @date 2022/4/18
 */
@Service
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private PlatformDao platformDao;

    @Override
    public Map<Integer, String> mapName() {
        List<PlatformDO> platformDOS = platformDao.findAll();
        Map<Integer, String> map = platformDOS.stream().collect(Collectors.toMap(PlatformDO::getId, PlatformDO::getName));
        return map;
    }
}
