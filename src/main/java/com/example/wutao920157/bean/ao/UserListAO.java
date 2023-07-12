package com.example.wutao920157.bean.ao;

import com.example.wutao920157.bean.dto.UserListDTO;
import lombok.Data;

import java.util.Objects;

/**
 * @author tao.wu
 * @date 2022/4/13
 */
@Data
public class UserListAO {

    private String username;

    /**
     * 这边是要给login验证密码的
     */
    private String password;

    private Integer age;

    private String address;

    private Integer pageNo;

    private Integer pageSize;

    public static UserListDTO buildUserListDto(UserListAO userListAO){
        UserListDTO userListDto = new UserListDTO();
        userListDto.setUsername(userListAO.getUsername());
        userListDto.setPassword(userListAO.getPassword());
        userListDto.setAge(userListAO.getAge());
        userListDto.setAddress(userListAO.getAddress());
        userListDto.setPageNo(Objects.isNull(userListAO.getPageNo()) ? 1 : userListAO.getPageNo());
        userListDto.setPageSize(Objects.isNull(userListAO.getPageSize()) ? 1 : userListAO.getPageSize());
        return userListDto;
    }
}
