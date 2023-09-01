package com.sydata.custody.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.manage.domain.Ventilation;
import com.sydata.manage.vo.VentilationVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author zhangcy
 * @since 2023/4/23 14:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GrainVentilationJobVo extends VentilationVo {
    @ApiModelProperty(value = "状态名称")
    private String statusName = "审核";

    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "廒间名称")
    private String ajmc;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "实际数量(公斤)")
    private BigDecimal sjsl;

    @ApiModelProperty(value = "计价数量（公斤）")
    private BigDecimal jjsl;

    @ApiModelProperty(value = "包存量包数（包）")
    private Integer bclbs;

    @DataBindCargo
    @ApiModelProperty("货位代码")
    private String hwmc;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质名称")
    private String lsxzdmName;

    public GrainVentilationJobVo(Ventilation source) {
        if (source != null) {
            setId(source.getId());
            setTfzydh(source.getTfzydh());
            setTfrq(source.getTfrq());
            setCfdm(source.getCfdm());
            setTfmd(source.getTfmd());
            setLdkxd(source.getLdkxd());
            setTflx(source.getTflx());
            setFdxs(source.getFdxs());
            setFwszfs(source.getFwszfs());
            setZfdjmj(source.getZfdjmj());
            setZhfdjmj(source.getZhfdjmj());
            setZfdzcd(source.getZfdzcd());
            setFwkkl(source.getFwkkl());
            setKqtjb(source.getKqtjb());
            setTfkszgs(source.getTfkszgs());
            setTfjxh(source.getTfjxh());
            setTfjts(source.getTfjts());
            setDtfjedqy(source.getDtfjedqy());
            setDtfjedfl(source.getDtfjedfl());
            setDtfjedgl(source.getDtfjedgl());
            setSffs(source.getSffs());
            setDtfjscfl(source.getDtfjscfl());
            setDtfjzgl(source.getDtfjzgl());
            setZfl(source.getZfl());
            setDwtfl(source.getDwtfl());
            setScxtzl(source.getScxtzl());
            setZhdl(source.getZhdl());
            setZysqw(source.getZysqw());
            setZysqs(source.getZysqs());
            setTfsc(source.getTfsc());
            setZyqpjlw(source.getZyqpjlw());
            setJshpjlw(source.getJshpjlw());
            setJwfd(source.getJwfd());
            setDljwnh(source.getDljwnh());
            setSsll(source.getSsll());
            setBsxgpjjg(source.getBsxgpjjg());
            setTfjwjyxpjzc(source.getTfjwjyxpjzc());
            setTfjwjyxpjsc(source.getTfjwjyxpjsc());
            setTfjwjyxpjzjc(source.getTfjwjyxpjzjc());
            setTfjwjyxpjxc(source.getTfjwjyxpjxc());
            setZyqpjsf(source.getZyqpjsf());
            setZyhpjsf(source.getZyhpjsf());
            setJsfd(source.getJsfd());
            setDljsnh(source.getDljsnh());
            setTfzyry(source.getTfzyry());
            setTfjsjyxfxzc(source.getTfjsjyxfxzc());
            setTfjsjyxfxsc(source.getTfjsjyxfxsc());
            setTfjsjyxfxxc(source.getTfjsjyxfxxc());
            setTfjsjyxfxzjc(source.getTfjsjyxfxzjc());
            setTfzyfzr(source.getTfzyfzr());
            setCzbz(source.getCzbz());
            setZhgxsj(source.getZhgxsj());
            setCreateBy(source.getCreateBy());
            setCreateTime(source.getCreateTime());
            setUpdateBy(source.getUpdateBy());
            setUpdateTime(source.getUpdateTime());
            setRegionId(source.getRegionId());
            setCountryId(source.getCountryId());
            setProvinceId(source.getProvinceId());
            setCityId(source.getCityId());
            setAreaId(source.getAreaId());
            setEnterpriseId(source.getEnterpriseId());
            setStockHouseId(source.getStockHouseId());

        }
    }
}
