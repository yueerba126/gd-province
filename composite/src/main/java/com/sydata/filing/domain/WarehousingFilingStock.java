package com.sydata.filing.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description:TODO(仓储备案-仓储库点实体类)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value="WarehousingFilingStock对象", description="仓储备案-仓储库点")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("warehousing_filing_stock")
public class WarehousingFilingStock implements Serializable {

    private static final long serialVersionUID = 1687254203072L;

    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(name = "id" , value = "主键ID")
    private String id;

    @ApiModelProperty(name = "companyId" , value = "仓储企业ID")
    private String companyId;

    @ApiModelProperty(name = "dwdm" , value = "仓储单位统一社会信用代码")
    private String dwdm;

    @ApiModelProperty(name = "dwmc" , value = "粮油仓储单位名称")
    private String dwmc;

    @ApiModelProperty(name = "kddm" , value = "库点编号")
    private String kddm;

    @ApiModelProperty(name = "kdmc" , value = "库点名称")
    private String kdmc;

    @ApiModelProperty(name = "zdmj" , value = "占地面积(m)")
    private BigDecimal zdmj;

    @ApiModelProperty(name = "whcr" , value = "完好仓容(吨)")
    private BigDecimal whcr;

    @ApiModelProperty(name = "yggr" , value = "油罐罐容(吨)")
    private BigDecimal yggr;

    @ApiModelProperty(name = "xzqhdm" , value = "所属区域")
    private String xzqhdm;

    @ApiModelProperty(name = "zyjhys" , value = "有专用检验化验室 0:有 1:无")
    private String zyjhys;

    @ApiModelProperty(name = "lycgjynl" , value = "具有粮油常规检验能力 0:有 1:无")
    private String lycgjynl;

    @ApiModelProperty(name = "lypzjynl" , value = "具有粮油品质检验能力 0:有 1:无")
    private String lypzjynl;

    @ApiModelProperty(name = "zbywwxy" , value = "周边有无危险源 0:有 1:无")
    private String zbywwxy;

    @ApiModelProperty(name = "zbywwry" , value = "周边有无污染源 0:有 1:无")
    private String zbywwry;

    @ApiModelProperty(name = "czbz" , value = "操作标志")
    private String czbz;

    @ApiModelProperty(name = "createBy" , value = "创建者")
    private String createBy;

    @ApiModelProperty(name = "createTime" , value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "updateBy" , value = "更新者")
    private String updateBy;

    @ApiModelProperty(name = "updateTime" , value = "修改时间")
    private LocalDateTime updateTime;

}
