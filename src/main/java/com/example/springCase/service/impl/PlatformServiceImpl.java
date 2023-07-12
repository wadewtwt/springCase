package com.example.springCase.service.impl;

import com.example.springCase.bean.entity.PlatformDO;
import com.example.springCase.dao.PlatformDao;
import com.example.springCase.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tao.wu
 * @date 2022/4/18
 */
@Service
public class PlatformServiceImpl implements PlatformService {

    @Resource
    private PlatformDao platformDao;

    @Override
    public Map<Integer, String> mapName() {
        List<PlatformDO> platformDOS = platformDao.findAll();
        Map<Integer, String> map = platformDOS.stream().collect(Collectors.toMap(PlatformDO::getId, PlatformDO::getName));
        return map;
    }
}
