package com.example.springCase.bean.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author tao.wu
 * @date 2022/4/5
 */
@Data
public class UserDO {
    private Integer id;
    private String username;
    private String password;
    private Integer age;
    private String address;
    private Integer platformId;
    private String salt;
    private Date createTime;
    private Date updateTime;
    private Integer createId;
    private Integer updateId;
    private Integer isDel;

}
