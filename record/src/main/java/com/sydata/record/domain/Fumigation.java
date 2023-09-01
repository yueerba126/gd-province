package com.sydata.record.domain;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 备案管理-熏蒸对象 record_fumigation
 *
 * @author system
 * @date 2022-12-10
 */
@ApiModel(description = "备案管理-熏蒸")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("record_fumigation")
public class Fumigation extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键ID(库区代码+填报日期+熏蒸编码)")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "填报日期")
    private LocalDate tbrq;

    @ApiModelProperty(value = "熏蒸编码(4位顺序码)")
    private String xzbm;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "申请熏蒸日期")
    private LocalDate sqxzrq;

    @ApiModelProperty(value = "负责人")
    private String fzr;

    @ApiModelProperty(value = "负责人电话")
    private String fzrdh;

    @ApiModelProperty(value = "现场负责人")
    private String xcfzr;

    @ApiModelProperty(value = "现场负责人职务")
    private String xcfzrzw;

    @ApiModelProperty(value = "现场负责人电话")
    private String xcfzrdh;

    @ApiModelProperty(value = "填表人")
    private String tbr;

    @ApiModelProperty(value = "填表人电话")
    private String tbrdh;

    @ApiModelProperty(value = "是否设置警戒线(东)：是或否")
    private String sfszjjxd;

    @ApiModelProperty(value = "是否设置警戒线(西)：是或否")
    private String sfszjjxx;

    @ApiModelProperty(value = "是否设置警戒线(南)：是或否")
    private String sfszjjxn;

    @ApiModelProperty(value = "是否设置警戒线(北)：是或否")
    private String sfszjjxb;

    @ApiModelProperty(value = "实施熏蒸作业时天气预报情况")
    private String ssxzzystqybqk;

    @ApiModelProperty(value = "熏蒸安排及实施过程")
    private String xzssgcap;

    @ApiModelProperty(value = "安全防护及应急处置措施")
    private String aqfhjyjcccs;

    @ApiModelProperty(value = "熏蒸注意事项")
    private String xzzysx;

    @ApiModelProperty(value = "文件存储ID")
    private String fileStorageId;

    @ApiModelProperty(value = "药剂名称")
    private String yjmc;

    @ApiModelProperty(value = "药剂类型/型号")
    private String yjlx;

    @ApiModelProperty(value = "药剂有效期至")
    private LocalDate yjyxqz;

    @ApiModelProperty(value = "领取数量(公斤)")
    private BigDecimal lqsl;

    @ApiModelProperty(value = "施药方式及设备")
    private String sysbjfs;

    @ApiModelProperty(value = "暂存地点")
    private String zcdd;

    @ApiModelProperty(value = "领取人")
    private String lqr;

    @ApiModelProperty(value = "领取日期")
    private LocalDate lqrq;

    @ApiModelProperty(value = "审核人")
    private String shrs;

    @ApiModelProperty(value = "审核意见")
    private String shyj;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime shsj;

    @ApiModelProperty(value = "审核状态：1待审核、2审核通过、3退回")
    private String shzt;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}