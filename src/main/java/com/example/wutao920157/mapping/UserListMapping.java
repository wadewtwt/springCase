package com.example.wutao920157.mapping;

import com.example.wutao920157.bean.dto.UserListItemResponseDTO;
import com.example.wutao920157.bean.dto.UserListResponseDTO;
import com.example.wutao920157.bean.entity.UserDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tao.wu
 * @date 2022/4/10
 */
public class UserListMapping {

    public static UserListResponseDTO buildUserListResponseDTO(List<UserDO> userList){
        UserListResponseDTO userListResponseDTO = new UserListResponseDTO();
        List<UserListItemResponseDTO> tmpList = new ArrayList<>();
        for (UserDO user : userList){
            UserListItemResponseDTO userListItemResponseDTO = new UserListItemResponseDTO();
            userListItemResponseDTO.setId(user.getId());
            userListItemResponseDTO.setUsername(user.getUsername());
            tmpList.add(userListItemResponseDTO);
        }
        userListResponseDTO.setUserListItem(tmpList);
        return userListResponseDTO;
    }
}
