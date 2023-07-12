package com.example.wutao920157.bean.dto;

import lombok.Data;

/**
 * @author tao.wu
 * @date 2022/4/22
 */
@Data
public class UserListDTO {

    private String username;

    /**
     * 这边是要给login验证密码的
     */
    private String password;

    private Integer age;

    private String address;

    private Integer pageNo;

    private Integer pageSize;
}
