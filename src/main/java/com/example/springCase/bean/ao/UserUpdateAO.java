package com.example.springCase.bean.ao;

import com.example.springCase.bean.dto.UserUpdateDTO;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author tao.wu
 * @date 2022/4/10
 */
@Data
public class UserUpdateAO {

    @Max(100)
    @Min(1)
    private Integer age;

    private String address;

    public static UserUpdateDTO buildUserUpdateDTO(UserUpdateAO userUpdateAO, String token){
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setAddress(userUpdateAO.getAddress());
        userUpdateDTO.setAge(userUpdateAO.getAge());
        userUpdateDTO.setToken(token);
        return userUpdateDTO;
    }
}
