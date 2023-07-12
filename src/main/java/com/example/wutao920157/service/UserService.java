package com.example.wutao920157.service;

import com.example.wutao920157.bean.dto.*;

import java.util.Map;

/**
 * @author tao.wu
 * @date 2022/4/5
 */
public interface UserService {

    /**
     * 列表
     * @param userListDto
     * @return
     */
    public UserListResponseDTO listPage(UserListDTO userListDto);

    /**
     * 详情
     * @param id
     * @return
     */
    public UserItemResponseDTO detail(Integer id);

    /**
     * 更新
     * @param userUpdateDTO
     */
    public void update(UserUpdateDTO userUpdateDTO);

    /**
     * 注册
     * @param userRegisterDTO
     * @return
     */
    public Object register(UserRegisterDTO userRegisterDTO);

    /**
     * 登录，限制2分钟内只能尝试登录5次
     * @param userLoginDTO
     * @return
     */
    public String login(UserLoginDTO userLoginDTO);

    /**
     * 用户中心
     * @param token
     * @return
     */
    public Map<String, Object> centre(String token);
}
