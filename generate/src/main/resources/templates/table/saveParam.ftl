package ${saveParamUrl};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.common.basis.annotation.DataBindDict;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
<#list pkgs as ps>
    <#if ps??>
import ${ps};
    </#if>
</#list>

/**
 * @Description:TODO(${entityComment}-保存更新参数)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 *
 */
@ApiModel(value="${entityName}SaveParam对象", description="${entityComment}-保存更新参数")
@Data
@ToString
@Accessors(chain = true)
public class ${entityName}SaveParam implements Serializable {

    private static final long serialVersionUID = ${agile}L;

<#list cis as ci>
    <#if ci.property=="id">
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}" , example = "${ci.comment}")
    private ${ci.javaType} ${ci.property};

    </#if>
</#list>
<#list entityExcludeAfterList?sort_by(["id"]) as ci>
    <#if ci.property!="id">
        <#if ci.isNullable=="NO">
            <#if ci.javaType=="String">
    @NotBlank(message = "${ci.property} 不容许为空")
            <#elseif ci.javaType?contains("List")>
    @NotEmpty(message = "${ci.property} 不容许为空")
            <#else>
    @NotNull(message = "${ci.property} 不容许为空")
            </#if>
        </#if>
        <#if ci.javaType=="String">
            <#if voInfoList?seq_contains(ci)>
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}" , example = "1")
    @Size(min = 0, max = ${ci.columnAccuracy}, message = "${ci.comment} 长度必须在 0 - ${ci.columnAccuracy} 之间")
            <#else>
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}" , example = "${ci.comment}")
    @Size(min = 0, max = ${ci.columnAccuracy}, message = "${ci.comment} 长度必须在 0 - ${ci.columnAccuracy} 之间")
            </#if>
        <#elseif ci.javaType=="Long"||ci.javaType=="Integer">
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}" , example = "1")
    @Digits(integer = ${ci.columnAccuracy}, fraction = 0, message = "${ci.comment}格式不正确")
        <#elseif ci.javaType=="BigDecimal">
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}" , example = "1")
    @Digits(integer = ${ci.columnAccuracy?substring(0,2)}, fraction = ${ci.columnAccuracy?substring(3,4)}, message = "${ci.comment}整数位上限为${ci.columnAccuracy?substring(0,1)}位，小数位上限为${ci.columnAccuracy?substring(3,4)}位")
    @DecimalMin(value = "0", message = "${ci.comment}不能小于零")
        <#elseif ci.javaType=="LocalDate">
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}" , example = "${startDate}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
        <#elseif ci.javaType=="LocalDateTime">
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}" , example = "${startTime}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        <#else>
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}" , example = "${ci.comment}")
        </#if>
    private ${ci.javaType} ${ci.property};

    </#if>
</#list>
<#list entityExcludeList?sort_by(["id"]) as ci>
    <#if ci.property!="id">
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}" , example = "${ci.javaType}" ,hidden = true)
        <#if ci.isNullable=="NO" && ci.javaType!="LocalDate" && ci.javaType!="LocalDateTime">
        </#if>
        <#if ci.javaType=="String">
    @Size(min = 0, max = ${ci.columnAccuracy}, message = "${ci.comment} 长度必须在 0 - ${ci.columnAccuracy} 之间")
        </#if>
        <#if ci.javaType=="Long"||ci.javaType=="Integer">
    @Digits(integer = ${ci.columnAccuracy}, fraction = 0, message = "${ci.comment}格式不正确")
        </#if>
        <#if ci.javaType=="BigDecimal">
    @Digits(integer = ${ci.columnAccuracy?substring(0,2)}, fraction = ${ci.columnAccuracy?substring(3,4)}, message = "${ci.comment}整数位上限为${ci.columnAccuracy?substring(0,1)}位，小数位上限为${ci.columnAccuracy?substring(3,4)}位")
    @DecimalMin(value = "0", message = "${ci.comment}不能小于零")
        </#if>
        <#if ci.javaType=="LocalDate">
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate ${ci.property};

        <#elseif ci.javaType=="LocalDateTime">
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ${ci.property};

        <#else>
    private ${ci.javaType} ${ci.property};

        </#if>
    </#if>
</#list>
}
	