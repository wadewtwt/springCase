package com.example.wutao920157.bean.dto;

import com.example.wutao920157.bean.entity.LogDO;
import lombok.Data;

import java.util.List;

/**
 * @author tao.wu
 * @date 2022/4/19
 */
@Data
public class LogListResponseDTO {

    private Long totalNum;

    private List<LogDO> logDOS;
}
