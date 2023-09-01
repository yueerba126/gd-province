package com.sydata.safe.asess.dto;

import com.sydata.safe.asess.domain.Review;
import com.sydata.safe.asess.domain.ReviewIndex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzq
 * @description 考核评审分配DTO
 * @date 2023/4/13 18:07
 */
@ApiModel(description = "粮食安全考核-考核评审分配DTO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReviewAllotDto extends Review implements Serializable {

    @ApiModelProperty(value = "考核评审指标列表")
    private List<ReviewIndex> reviewDtls;
}
