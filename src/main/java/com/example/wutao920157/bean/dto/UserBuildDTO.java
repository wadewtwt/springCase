package com.example.wutao920157.bean.dto;

import lombok.Data;

/**
 * @author tao.wu
 * @date 2022/4/22
 */
@Data
public class UserBuildDTO {
    private Integer id;

    private String username;

    private String password;

    private Integer age;

    private Integer platformId;

    private String address;

    private String salt;

}
