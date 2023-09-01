package ${entityUrl};

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
<#list pkgs as ps>
    <#if ps??>
import ${ps};
    </#if>
</#list>

/**
 * @Description:TODO(${entityComment}实体类)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 *
 */
@ApiModel(value="${entityName}对象", description="${entityComment}")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("${table}")
public class ${entityName} implements Serializable {

    private static final long serialVersionUID = ${agile}L;

<#list cis as ci>
    <#if ci.property=="id">
    @TableId(value = "id",type = IdType.INPUT)
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}")
    private ${ci.javaType} ${ci.property};

    </#if>
</#list>
<#list cis?sort_by(["id"]) as ci>
    <#if ci.property!="id">
    @ApiModelProperty(name = "${ci.property}" , value = "${ci.comment}")
            <#if ci.isNullable=="NO" && ci.javaType!="LocalDate" && ci.javaType!="LocalDateTime">
            </#if>
            <#if ci.javaType=="String">
            </#if>
            <#if ci.javaType=="Long"||ci.javaType=="Integer">
            </#if>
            <#if ci.javaType=="BigDecimal">
            </#if>
            <#if ci.javaType=="LocalDate">
    private LocalDate ${ci.property};

            <#elseif ci.javaType=="LocalDateTime">
    private LocalDateTime ${ci.property};

            <#else>
    private ${ci.javaType} ${ci.property};

            </#if>
    </#if>
</#list>
}
