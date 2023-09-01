package com.sydata.custody.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.manage.domain.SteamTaskInformation;
import com.sydata.manage.vo.SteamTaskInformationVo;
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
public class GrainSteamTaskInformationVo extends SteamTaskInformationVo {
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
    @ApiModelProperty("货位名称")
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

    public GrainSteamTaskInformationVo(SteamTaskInformation source) {
        if (source != null) {
            setId(source.getId());
            setXzzydh(source.getXzzydh());
            setCfdm(source.getCfdm());
            setXzkssj(source.getXzkssj());
            setXzjssj(source.getXzjssj());
            setHcjcdh(source.getHcjcdh());
            setDjcxz(source.getDjcxz());
            setCgxzfs(source.getCgxzfs());
            setHlxzfs(source.getHlxzfs());
            setHlxzynhljsjh(source.getHlxzynhljsjh());
            setXzfazd(source.getXzfazd());
            setXzfaqcr(source.getXzfaqcr());
            setXzfapzr(source.getXzfapzr());
            setXzfabbqk(source.getXzfabbqk());
            setSyryzzqk(source.getSyryzzqk());
            setSyzzsh(source.getSyzzsh());
            setYjmc(source.getYjmc());
            setJx(source.getJx());
            setNd(source.getNd());
            setLddwyyl(source.getLddwyyl());
            setKjdwyyl(source.getKjdwyyl());
            setZyyl(source.getZyyl());
            setSyff(source.getSyff());
            setFzsycs(source.getFzsycs());
            setKqhqjaqjcqk(source.getKqhqjaqjcqk());
            setLhqjczztsqk(source.getLhqjczztsqk());
            setYqsdjczztsqk(source.getYqsdjczztsqk());
            setLhqbjyaqjc(source.getLhqbjyaqjc());
            setYqbjyaqjc(source.getYqbjyaqjc());
            setByqcnlhqnd(source.getByqcnlhqnd());
            setLhqnddw(source.getLhqnddw());
            setMbnd(source.getMbnd());
            setJsbyl(source.getJsbyl());
            setSjbyl(source.getSjbyl());
            setByff(source.getByff());
            setZyrs(source.getZyrs());
            setByzypzr(source.getByzypzr());
            setXczhr(source.getXczhr());
            setFznd(source.getFznd());
            setMbndwcts(source.getMbndwcts());
            setLswzjc(source.getLswzjc());
            setLqbwcqdbjcs(source.getLqbwcqdbjcs());
            setMbsj(source.getMbsj());
            setCtz(source.getCtz());
            setSqqlhqnd(source.getSqqlhqnd());
            setSqrq(source.getSqrq());
            setSqff(source.getSqff());
            setSqpzr(source.getSqpzr());
            setSqcsts(source.getSqcsts());
            setSqjsslhqnd(source.getSqjsslhqnd());
            setCzsjzysj(source.getCzsjzysj());
            setCzsjzyrs(source.getCzsjzyrs());
            setCzsjff(source.getCzsjff());
            setCzsjzypzr(source.getCzsjzypzr());
            setCzclcs(source.getCzclcs());
            setCzclzyrs(source.getCzclzyrs());
            setCzclpzr(source.getCzclpzr());
            setXzhhcjcqk(source.getXzhhcjcqk());
            setXzhckmd(source.getXzhckmd());
            setPyswthhcs(source.getPyswthhcs());
            setPysswthhcs(source.getPysswthhcs());
            setXzxgpj(source.getXzxgpj());
            setXzfzr(source.getXzfzr());
            setXzzyry(source.getXzzyry());
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
