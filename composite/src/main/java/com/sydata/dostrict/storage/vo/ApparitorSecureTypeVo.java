/**
 * @filename:ApparitorSecureTypeBean 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.composite.annotation.DataBindZdglType;
import com.sydata.common.domain.BaseFiledEntity;
import com.sydata.dostrict.storage.domain.ApparitorSecureType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**   
 * @Description:TODO(安全仓储-安全生产-制度类别Vo)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="ApparitorSecureTypeBeanVo", description="安全仓储-安全生产-制度类别Vo")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorSecureTypeVo extends ApparitorSecureType implements Serializable {

    @DataBindDict(sourceField = "#isEnable", sourceFieldCombination = "is_enable")
    @ApiModelProperty(name = "isEnableName" , value = "启用状态名称(01正常 02停用)")
    private String isEnableName;

    @DataBindZdglType(sourceField = "#parentId")
    @ApiModelProperty(name = "parentName" , value = "启用状态名称(01正常 02停用)")
    private String parentName;

    @DataBindDict(sourceField = "#isLeafNode", sourceFieldCombination = "is_leaf_node")
    @ApiModelProperty(name = "isLeafNodeName" , value = "是否叶节点名称(01是 02否)")
    private String isLeafNodeName;
}
