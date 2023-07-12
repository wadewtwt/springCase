package com.example.springCase.bean.constant;

/**
 * @author tao.wu
 * @date 2022/4/22
 */
public final class RedisConstant {

    private RedisConstant(){}

    public static final String CACHE_JWT_KEY = "simpleCase#jwt#%s";
    public static final String CACHE_LOGIN_KEY = "simpleCase#login#%s";

    public static final Integer CACHE_LOGIN_TIME = 2;
    public static final Integer CACHE_LOGIN_TRY_TIMES = 5;

}
