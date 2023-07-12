package com.example.springCase.dao;

import com.example.springCase.bean.dto.UserListDTO;
import com.example.springCase.bean.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tao.wu
 * @date 2022/4/5
 */
@Mapper
public interface UserDao {

    List<UserDO> selectUserList(UserListDTO userListDto);

    List<UserDO> selectUserDetail(@Param("id") Integer id);

    void updateUserDetail(UserDO userDo);

    Integer countByUsername(@Param("username") String username);

    Integer insertUser(UserDO userDo);

}
