package com.sydata.trade.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;

/**
 * 粮油购销-合同信息对象 trade_contract
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "合同信息")
@Data
@TableName(value = "trade_contract")
public class Contract extends BaseFiledEntity implements Serializable {

    @ApiModelProperty("主键id(单位代码+合同编号)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty("合同编号")
    private String hth;

    @ApiModelProperty("合同名称")
    private String htmc;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("业务类型")
    private String ywlx;

    @ApiModelProperty("客户类型")
    private String khlx;

    @ApiModelProperty("客户统一社会信用代码")
    private String khtyshxydm;

    @ApiModelProperty("客户名称")
    private String khmc;

    @ApiModelProperty("法定代表人")
    private String fddbr;

    @ApiModelProperty("通讯地址")
    private String txdz;

    @ApiModelProperty("邮政编码")
    private String yzbm;

    @ApiModelProperty("联系人姓名")
    private String lxrxm;

    @ApiModelProperty("联系人电话")
    private String lxrdh;

    @ApiModelProperty("身份证号")
    private String sfzh;

    @ApiModelProperty("电子邮箱")
    private String dzyx;

    @ApiModelProperty("签订日期")
    private LocalDate qdrq;

    @ApiModelProperty("约定完成时间")
    private LocalDate ydwcsj;

    @ApiModelProperty("签订地点")
    private String qddd;

    @ApiModelProperty("粮食品种代码")
    private String lspzdm;

    @ApiModelProperty("粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty("合同单价")
    private BigDecimal htdj;

    @ApiModelProperty("约定购销粮食数量")
    private BigDecimal ydgxlssl;

    @ApiModelProperty("合同总金额")
    private BigDecimal htzje;

    @ApiModelProperty("履约保证金")
    private BigDecimal lybzj;

    @ApiModelProperty("实际完成时间")
    private LocalDate sswcsj;

    @ApiModelProperty("履约数量")
    private BigDecimal lysl;

    @ApiModelProperty("履约率")
    private BigDecimal lyl;

    @ApiModelProperty("结算价格")
    private BigDecimal jsjg;

    @ApiModelProperty("结算总金额")
    private BigDecimal jszje;

    @ApiModelProperty("结算与合同不一致原因")
    private String jsyhtbyzyy;

    @ApiModelProperty("结算与合同一致性")
    private String jsyhtyzx;

    @ApiModelProperty("客户方开户行")
    private String khfkhh;

    @ApiModelProperty("客户方账号")
    private String khfzh;

    @ApiModelProperty("客户签约人")
    private String khqyr;

    @ApiModelProperty("本方开户行")
    private String bfkhh;

    @ApiModelProperty("本方账号")
    private String bfzh;

    @ApiModelProperty("本方签约人")
    private String bfqyr;

    @ApiModelProperty("审核人")
    private String spr;

    @ApiModelProperty("审核时间")
    private LocalDateTime shsj;

    @ApiModelProperty("完成日期")
    private LocalDate wcrq;

    @ApiModelProperty("操作标志")
    private String czbz;

    @ApiModelProperty("最后更新时间")
    private LocalDateTime zhgxsj;
}