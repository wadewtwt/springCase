package com.example.wutao920157.bean.ao;

import com.example.wutao920157.bean.dto.UserLoginDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author tao.wu
 * @date 2022/4/10
 */
@Data
public class UserLoginAO {

    @NotEmpty(message = "用户名不能为空！")
    private String username;

    @NotEmpty(message = "密码不能为空！")
    private String password;

    public static UserLoginDTO buildUserLoginDTO(UserLoginAO userLoginAO, String ipAddress){
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(userLoginAO.getUsername());
        userLoginDTO.setPassword(userLoginAO.getPassword());
        userLoginDTO.setIpAddress(ipAddress);
        return userLoginDTO;
    }

}
