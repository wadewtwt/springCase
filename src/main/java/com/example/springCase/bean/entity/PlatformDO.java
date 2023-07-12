package com.example.springCase.bean.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author tao.wu
 * @date 2022/4/18
 */
@Data
public class PlatformDO {
    private Integer id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private Integer createId;
    private Integer updateId;
    private Integer isDel;

}
