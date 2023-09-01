
package com.sydata.framework.job.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lzq
 * @description limit分片dto
 * @date 2022/11/1 22:14
 */
@Data
@AllArgsConstructor
public class LimitDto implements Serializable {

    @ApiModelProperty(value = "偏移量")
    private int offset;

    @ApiModelProperty(value = "拉取数量")
    private int size;


    @Override
    public String toString() {
        return "limit " + offset + "," + size;
    }
}
