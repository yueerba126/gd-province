package com.sydata.manage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 虫害信息表
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("manage_pest_information")
@ApiModel(value = "PestInformation对象", description = "虫害信息表")
public class PestInformation extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "害虫监测单号")
    private String hcjcdh;

    @ApiModelProperty(value = "检测时间")
    private LocalDateTime jcsj;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "检查虫害方法")
    private String jchcff;

    @ApiModelProperty(value = "发生部位")
    private String fsbw;

    @ApiModelProperty(value = "虫害种类")
    private String hczl;

    @ApiModelProperty(value = "虫口密度值集合")
    private String ckmdzjh;


    @ApiModelProperty(value = "虫粮等级判定")
    private String cldjpd;


    @ApiModelProperty(value = "害虫抗药性分析")
    private String hckyxfx;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "虫害最后更新时间")
    private LocalDateTime zhgxsj;

}
