package com.example.springCase.bean.dto;

import lombok.Data;

import java.util.List;

/**
 * @author tao.wu
 * @date 2022/4/10
 */
@Data
public class UserListResponseDTO {
    private Long totalNum;
    private List<UserListItemResponseDTO> userListItem;

}
