package com.sydata.dostrict.storage.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 安全仓储-安全生产-制度类别-新增参数
 * @version: 1.0
 */
@ApiModel(description = "安全仓储-安全生产-制度类别-新增参数")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorSecureTypeSaveParam implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "安全生产-制度类别管理Id")
    private String id;

    @ApiModelProperty(name = "isEnable" , value = "启用状态(01正常 02停用)")
    private String isEnable;

    @ApiModelProperty(name = "isLeafNode" , value = "是否叶节点(01是 02否)")
    private String isLeafNode;

    @ApiModelProperty(name = "parentId" , value = "父类ID")
    private String parentId;

    @ApiModelProperty(name = "remark" , value = "备注")
    private String remark;

    @ApiModelProperty(name = "typeName" , value = "分类名称")
    private String typeName;

    @ApiModelProperty(name = "typeCode" , value = "分类编码")
    private String typeCode;

    @ApiModelProperty(name = "czbz" , value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
