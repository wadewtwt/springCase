package com.example.springCase.bean.ao;

import com.example.springCase.bean.dto.UserRegisterDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author tao.wu
 * @date 2022/4/10
 */
@Data
public class UserRegisterAO {

    @NotEmpty(message = "用户名不能为空！")
    private String username;

    @NotEmpty(message = "密码不能为空！")
    private String password;

    @NotEmpty(message = "重复密码不能为空！")
    private String repeatPassword;

    public static UserRegisterDTO buildUserRegisterDTO(UserRegisterAO userRegisterAO, Integer platformId){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setPlatformId(platformId);
        userRegisterDTO.setUsername(userRegisterAO.getUsername());
        userRegisterDTO.setPassword(userRegisterAO.getPassword());
        userRegisterDTO.setRepeatPassword(userRegisterAO.getRepeatPassword());
        return userRegisterDTO;
    }

}
