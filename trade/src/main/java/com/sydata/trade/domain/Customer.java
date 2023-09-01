package com.sydata.trade.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
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
 * 粮油购销-客户对象 trade_customer
 *
 * @author lzq
 * @date 2022-07-22
 */
@ApiModel(description = "粮油购销-客户信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("trade_customer")
public class Customer extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID（由单位代码+客户统一社会信用代码组成）")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "客户类型")
    private String khlx;

    @ApiModelProperty(value = "客户统一社会信用代码或身份证号")
    private String khtyshxydmhsfzh;

    @ApiModelProperty(value = "客户名称")
    private String khmc;

    @ApiModelProperty(value = "法定代表人")
    private String fddbr;

    @ApiModelProperty(value = "通讯地址")
    private String txdz;

    @ApiModelProperty(value = "邮政编码")
    private String yzbm;

    @ApiModelProperty(value = "联系人姓名")
    private String lxrxm;

    @ApiModelProperty(value = "联系电话")
    private String lxrdh;

    @ApiModelProperty(value = "联系人身份证号")
    private String lxrsfzh;

    @ApiModelProperty(value = "电子信箱")
    private String dzyx;

    @ApiModelProperty(value = "客户方开户行")
    private String khfkhh;

    @ApiModelProperty(value = "客户方账号")
    private String khfzh;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}