package com.sydata.monitoring.entity;

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
 * 流通检测-监测点配置表
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("monitoring_check_point")
@ApiModel(value = "CheckPoint对象", description = "流通检测-监测点配置表")
public class CheckPoint extends BaseFiledEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "监测点名称")
    private String name;

    @ApiModelProperty(value = "状态：1-正常，0-禁用")
    private Boolean status;

    @ApiModelProperty(value = "经度")
    private String jd;

    @ApiModelProperty(value = "纬度")
    private String wd;

    @ApiModelProperty(value = "监测点账号ID")
    private String accountUserId;

}
