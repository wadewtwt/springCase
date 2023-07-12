package com.example.wutao920157.service;

import java.util.List;

/**
 * @author tao.wu
 * @date 2022/4/22
 */
public interface SensitiveWordService {
    /**
     * 查询所有
     * @return
     */
    public List<String> findAll();

    /**
     * 判断是否有敏感词
     * @param content
     * @return
     */
    public Boolean judgeSensitiveWord(String content);
}
