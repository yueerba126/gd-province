package com.sydata.common.param;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @author lzq
 * @describe 公共模块-分页查询参数基类
 * @date 2022-07-09 16:11
 */
@ApiModel(description = "公共模块-分页查询参数基类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryParam implements Serializable {

    @ApiModelProperty(value = "当前页", example = "1")
    private int pageNum = 1;

    @ApiModelProperty(value = "每页数量", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "排序列表")
    private List<OrderItem> orders;

    public PageQueryParam(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
