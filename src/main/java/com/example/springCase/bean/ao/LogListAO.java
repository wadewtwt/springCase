package com.example.springCase.bean.ao;

import com.example.springCase.bean.dto.LogListDTO;
import lombok.Data;

/**
 * @author tao.wu
 * @date 2022/4/19
 */
@Data
public class LogListAO {

    private Integer pageNo = 1;

    private Integer pageSize = 20;

    public static LogListDTO buildLogListDTO(LogListAO logListAO){
        LogListDTO logListDTO = new LogListDTO();
        logListDTO.setPageNo(logListAO.getPageNo());
        logListDTO.setPageSize(logListAO.getPageSize());
        return logListDTO;
    }
}
