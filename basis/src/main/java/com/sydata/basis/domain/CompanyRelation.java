package com.sydata.basis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 单位信息关系对象 basis_company_relation
 *
 * @author lzq
 * @date 2022-12-06
 */
@ApiModel(description = "单位信息关系")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("basis_company_relation")
public class CompanyRelation implements Serializable {

    @ApiModelProperty(value = "主键ID(单位代码)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "下级单位代码集合,分隔")
    private String sonIds;

    @ApiModelProperty(value = "上级单位代码集合,分隔")
    private String parentIds;
}