package com.example.springCase.controller;

import com.alibaba.fastjson.JSON;
import com.example.springCase.annotation.IgnoreAuth;
import com.example.springCase.annotation.OperateLog;
import com.example.springCase.bean.ao.UserListAO;
import com.example.springCase.bean.ao.UserLoginAO;
import com.example.springCase.bean.ao.UserRegisterAO;
import com.example.springCase.bean.ao.UserUpdateAO;
import com.example.springCase.service.UserService;
import com.rjhy.base.bean.dto.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author tao.wu
 * @date 2022/4/5
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @OperateLog(content = "用户列表")
    @PostMapping(value = "user/listPage")
    public ApiResult listPage(@RequestBody UserListAO userListAO){
        log.info("UserController listPage userListAO:{}", JSON.toJSONString(userListAO));
        return ApiResult.ok(userService.listPage(UserListAO.buildUserListDto(userListAO)));
    }

    @OperateLog(content = "用户详情")
    @GetMapping(value = "user/detail")
    public ApiResult detail(@RequestParam Integer id){
        log.info("UserController detail id:{}", id);
        return ApiResult.ok(userService.detail(id));
    }

    @OperateLog(content = "用户信息完善")
    @PostMapping(value = "user/update")
    public ApiResult update(@RequestHeader("Authorization") String token,
                            @RequestBody @Valid UserUpdateAO userUpdateAO){
        log.info("UserController update token:{}, userUpdateAO:{}", token, JSON.toJSONString(userUpdateAO));
        userService.update(UserUpdateAO.buildUserUpdateDTO(userUpdateAO, token));
        return ApiResult.ok();
    }

    @IgnoreAuth
    @PostMapping(value = "user/register")
    public ApiResult register(@RequestHeader("platformId") Integer platformId,
                              @RequestBody @Valid UserRegisterAO userRegisterAO){
        log.info("UserController register platformId:{}, userRegisterAO:{}", platformId, JSON.toJSONString(userRegisterAO));
        userService.register(UserRegisterAO.buildUserRegisterDTO(userRegisterAO, platformId));
        return ApiResult.ok();
    }

    @IgnoreAuth
    @PostMapping(value = "user/login")
    public ApiResult login(@RequestBody @Valid UserLoginAO userLoginAO, HttpServletRequest httpServletRequest){
        log.info("UserController login userLoginAO:{}", JSON.toJSONString(userLoginAO));
        String ipAddress = httpServletRequest.getRemoteAddr();
        return ApiResult.ok(userService.login(UserLoginAO.buildUserLoginDTO(userLoginAO, ipAddress)));
    }

    @OperateLog(content = "用户中心")
    @GetMapping(value = "user/centre")
    public ApiResult centre(@RequestHeader("Authorization") String token){
        log.info("UserController centre token:{}", token);
        return ApiResult.ok(userService.centre(token));
    }

}
