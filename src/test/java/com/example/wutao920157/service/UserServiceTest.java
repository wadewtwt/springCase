package com.example.wutao920157.service;

import com.alibaba.fastjson.JSONObject;
import com.example.wutao920157.Application;
import com.example.wutao920157.bean.dto.UserListDTO;
import com.example.wutao920157.bean.dto.UserLoginDTO;
import com.example.wutao920157.bean.dto.UserRegisterDTO;
import com.example.wutao920157.bean.dto.UserUpdateDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tao.wu
 * @date 2022/4/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHBpcmVJbiI6MTY1MDc5MzEzNjk1MiwiaWQiOjQsInVzZXJOYW1lIjoi6LW15LqRIiwiZXhwIjoxNjUwNzkzMTM2LCJpYXQiOjE2NTA3MDY3MzZ9.akEA9xCiMbbyIEzRF-5GAu1Ey02MoSO42BQ9xy3SCxymIVTNTPh5MNErBLUoOj8uKpMwb748zZ3_hsEn7hF2sw";

    @Test
    public void listPageTest(){
        UserListDTO userListDTO = new UserListDTO();
        userListDTO.setUsername("赵云");
        userListDTO.setAge(22);
        userListDTO.setPageNo(1);
        userListDTO.setPageSize(20);
        System.out.println("UserServiceTests listPageTest:" + JSONObject.toJSONString(userService.listPage(userListDTO)));
    }

    @Test
    public void detailTest(){
        Integer id = 2;
        System.out.println("UserServiceTests detailTest:" + JSONObject.toJSONString(userService.detail(id)));
    }

    @Test
    public void updateTest(){
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setAge(11);
        userUpdateDTO.setAddress("山东1");
        userUpdateDTO.setToken(token);

        userService.update(userUpdateDTO);
    }

    @Test
    public void registerTest(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("孙权");
        userRegisterDTO.setPlatformId(3);
        userRegisterDTO.setPassword("49ba59abbe56e057");
        userRegisterDTO.setRepeatPassword("49ba59abbe56e057");

        userService.register(userRegisterDTO);
    }

    @Test
    public void loginTest(){
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("孙权");
        userLoginDTO.setPassword("49ba59abbe56e057");
        userLoginDTO.setIpAddress("127.0.0.2");

        System.out.println("UserServiceTests loginTest:" + JSONObject.toJSONString(userService.login(userLoginDTO)));
    }

    @Test
    public void centreTest(){
        System.out.println("UserServiceTests centreTest:" + JSONObject.toJSONString(userService.centre(token)));
    }

}





