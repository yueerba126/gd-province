package com.sydata.reserve.layout.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;
/**
 * @author lzq
 * @describe 企业分页查询参数
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "企业分页查询参数")
public class ReserveCompanyPageParam extends PageQueryParam implements Serializable {


    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

    @ApiModelProperty("状态")
    private String status;




}
