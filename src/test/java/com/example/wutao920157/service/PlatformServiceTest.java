package com.example.springCase.service;

import com.alibaba.fastjson.JSONObject;
import com.example.springCase.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tao.wu
 * @date 2022/4/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlatformServiceTest {

    @Autowired
    private PlatformService platformService;

    private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHBpcmVJbiI6MTY1MDc5MzEzNjk1MiwiaWQiOjQsInVzZXJOYW1lIjoi6LW15LqRIiwiZXhwIjoxNjUwNzkzMTM2LCJpYXQiOjE2NTA3MDY3MzZ9.akEA9xCiMbbyIEzRF-5GAu1Ey02MoSO42BQ9xy3SCxymIVTNTPh5MNErBLUoOj8uKpMwb748zZ3_hsEn7hF2sw";

    @Test
    public void mapNameTest(){
        System.out.println("UserServiceTests mapNameTest:" + JSONObject.toJSONString(platformService.mapName()));
    }

}





