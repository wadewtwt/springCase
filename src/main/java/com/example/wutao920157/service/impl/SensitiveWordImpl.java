package com.example.wutao920157.service.impl;

import com.example.wutao920157.bean.entity.SensitiveWordDO;
import com.example.wutao920157.dao.SensitiveWordDao;
import com.example.wutao920157.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tao.wu
 * @date 2022/4/22
 */
@Service
public class SensitiveWordImpl implements SensitiveWordService {

    @Autowired
    private SensitiveWordDao sensitiveWordDao;

    @Override
    public List<String> findAll() {
        List<SensitiveWordDO> sensitiveWords = sensitiveWordDao.findAll();
        return sensitiveWords.stream().map(SensitiveWordDO::getContent).collect(Collectors.toList());
    }

    @Override
    public Boolean judgeSensitiveWord(String content) {
        List<String> sensitiveWordList = findAll();
        boolean haveSensitiveWord = false;
        for (String sensitiveWord : sensitiveWordList){
            if (content.contains(sensitiveWord)){
                haveSensitiveWord = true;
                break;
            }
        }
        return haveSensitiveWord;
    }


}
