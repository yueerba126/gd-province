package com.sydata.dostrict.storage.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 安全仓储-安全生产-生产制度-新增参数
 * @version: 1.0
 */
@ApiModel(description = "安全仓储-安全生产-生产制度-新增参数")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorSecureSystemSaveParam implements Serializable {

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "生产制度id")
    private String id;

    @ApiModelProperty(name = "deptId" , value = "部门")
    private String deptId;

    @ApiModelProperty(name = "dwdm" , value = "发布单位")
    private String dwdm;

    @ApiModelProperty(name = "billStatus" , value = "状态")
    private String billStatus;

    @ApiModelProperty(name = "fileAttachment" , value = "附件记录")
    private String fileAttachment;

    @ApiModelProperty(name = "fileName" , value = "文件标题")
    private String fileName;

    @ApiModelProperty(name = "number" , value = "发文号")
    private String number;

    @ApiModelProperty(name = "releaseId" , value = "发布人ID")
    private String releaseId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "releaseTime" , value = "发布时间")
    private LocalDateTime releaseTime;

    @ApiModelProperty(name = "remark" , value = "文件描述")
    private String remark;

    @ApiModelProperty(name = "typeId" , value = "制度管理id")
    private String typeId;

    @ApiModelProperty(name = "czbz" , value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @NotNull(message = "最后更新时间必填")
    @ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
