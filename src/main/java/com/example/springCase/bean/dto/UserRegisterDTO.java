package com.example.springCase.bean.dto;

import lombok.Data;

/**
 * @author tao.wu
 * @date 2022/4/22
 */
@Data
public class UserRegisterDTO {
    private Integer platformId;

    private String username;

    private String password;

    private String repeatPassword;
}
