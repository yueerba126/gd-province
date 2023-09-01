package ${pageParamUrl};

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.List;
<#list pkgs as ps>
    <#if ps??>
import ${ps};
    </#if>
</#list>

/**
* @Description:TODO(${entityComment}-分页参数)
* @date ${createDate}
* @version: ${version}
* @author: ${author}
*
*/
@ApiModel(value="${entityName}PageParam对象", description="${entityComment}-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
public class ${entityName}PageParam extends PageQueryParam implements Serializable {

private static final long serialVersionUID = ${agile}L;

<#list pageParamInfoList?sort_by(["id"]) as pa>
    <#if pa.javaType=="String">
        <#if voInfoList?seq_contains(pa)>
    @ApiModelProperty(name = "${pa.property}" , value = "${pa.comment}" , example = "1")
    @Size(min = 0, max = ${pa.columnAccuracy}, message = "${pa.comment} 长度必须在 0 - ${pa.columnAccuracy} 之间")
    private ${pa.javaType} ${pa.property};
        <#else>
    @ApiModelProperty(name = "${pa.property}" , value = "${pa.comment}" , example = "${pa.comment}")
    @Size(min = 0, max = ${pa.columnAccuracy}, message = "${pa.comment} 长度必须在 0 - ${pa.columnAccuracy} 之间")
    private ${pa.javaType} ${pa.property};
        </#if>
    <#elseif pa.javaType=="Long"||pa.javaType=="Integer">
    @ApiModelProperty(name = "${pa.property}" , value = "${pa.comment}" , example = "1")
    @Digits(integer = ${pa.columnAccuracy}, fraction = 0, message = "${pa.comment}格式不正确")
    private ${pa.javaType} ${pa.property};
    <#elseif pa.javaType=="BigDecimal">
    @ApiModelProperty(name = "${pa.property}" , value = "${pa.comment}" , example = "1")
    @Digits(integer = ${pa.columnAccuracy?substring(0,2)}, fraction = ${pa.columnAccuracy?substring(3,4)}, message = "${pa.comment}整数位上限为${pa.columnAccuracy?substring(0,1)}位，小数位上限为${pa.columnAccuracy?substring(3,4)}位")
    @DecimalMin(value = "0", message = "${pa.comment}不能小于零")
    private ${pa.javaType} ${pa.property};
    <#elseif pa.javaType=="LocalDate">
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @ApiModelProperty(name = "begin${pa.property?cap_first}" ,value = "开始${pa.comment}" ,example = "${startDate}")
    private ${pa.javaType} begin${pa.property?cap_first};

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @ApiModelProperty(name = "end${pa.property?cap_first}" ,value = "结束${pa.comment}" ,example = "${endDate}")
    private ${pa.javaType} end${pa.property?cap_first};
    <#elseif pa.javaType=="LocalDateTime">
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "begin${pa.property?cap_first}" ,value = "开始${pa.comment}" ,example = "${startTime}")
    private ${pa.javaType} begin${pa.property?cap_first};

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "end${pa.property?cap_first}" ,value = "结束${pa.comment}" ,example = "${endTime}")
    private ${pa.javaType} end${pa.property?cap_first};
    <#else>
    @ApiModelProperty(name = "${pa.property}" , value = "${pa.comment}" , example = "${pa.comment}")
    private ${pa.javaType} ${pa.property};
    </#if>

</#list>
    @ApiModelProperty(name = "ids" ,value = "id集合")
    private List<String> ids;

}
	