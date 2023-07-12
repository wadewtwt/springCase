package com.example.springCase.exception;

/**
 * @author tao.wu
 * @date 2022/4/27
 */
public interface BaseErrorInfoInterface {
    /** 错误码*/
    String getResultCode();

    /** 错误描述*/
    String getResultMsg();
}
