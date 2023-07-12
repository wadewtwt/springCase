package com.example.wutao920157.controller;

import com.example.wutao920157.annotation.IgnoreAuth;
import com.example.wutao920157.annotation.OperateLog;
import com.example.wutao920157.bean.ao.UserListAO;
import com.example.wutao920157.bean.ao.UserLoginAO;
import com.example.wutao920157.bean.ao.UserRegisterAO;
import com.example.wutao920157.bean.ao.UserUpdateAO;
import com.example.wutao920157.service.UserService;
import com.rjhy.base.bean.dto.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @author tao.wu
 * @date 2022/4/5
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @OperateLog(content = "用户列表")
    @PostMapping(value = "user/listPage")
    public ApiResult listPage(@RequestBody UserListAO userListAO){

        return ApiResult.ok(userService.listPage(UserListAO.buildUserListDto(userListAO)));
    }

    @OperateLog(content = "用户详情")
    @GetMapping(value = "user/detail")
    public ApiResult detail(@RequestParam Integer id){
        return ApiResult.ok(userService.detail(id));
    }

    @OperateLog(content = "用户信息完善")
    @PostMapping(value = "user/update")
    public ApiResult update(@RequestHeader("Authorization") String token,
                            @RequestBody @Valid UserUpdateAO userUpdateAO){

        userService.update(UserUpdateAO.buildUserUpdateDTO(userUpdateAO, token));
        return ApiResult.ok();
    }

    @IgnoreAuth
    @PostMapping(value = "user/register")
    public ApiResult register(@RequestHeader("platformId") Integer platformId,
                              @RequestBody @Valid UserRegisterAO userRegisterAO){

        userService.register(UserRegisterAO.buildUserRegisterDTO(userRegisterAO, platformId));
        return ApiResult.ok();
    }

    @IgnoreAuth
    @PostMapping(value = "user/login")
    public ApiResult login(@RequestBody @Valid UserLoginAO userLoginAO, HttpServletRequest httpServletRequest){
        String ipAddress = httpServletRequest.getRemoteAddr();
        return ApiResult.ok(userService.login(UserLoginAO.buildUserLoginDTO(userLoginAO, ipAddress)));
    }

    @OperateLog(content = "用户中心")
    @GetMapping(value = "user/centre")
    public ApiResult centre(@RequestHeader("Authorization") String token){
        return ApiResult.ok(userService.centre(token));
    }

}
