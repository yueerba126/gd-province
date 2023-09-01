package com.sydata.report.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sydata.framework.core.jackson.deserializer.JsonToStringDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据上报-国家平台指令接收记录对象 report_command_record
 *
 * @author lzq
 * @date 2022-10-31
 */
@ApiModel(description = "数据上报-国家平台指令接收记录")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("report_command_record")
public class CommandRecord implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "指令ID")
    private String orderid;

    @ApiModelProperty(value = "指令类型 0：心跳指令 1：数据指令")
    private String type;

    @JsonDeserialize(using = JsonToStringDeserializer.class)
    @ApiModelProperty(value = "指令内容")
    private String data;

    @ApiModelProperty(value = "接收时间")
    private LocalDateTime receiveTime;
}