package ${voUrl};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ${entityUrl}.${entityName};
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import ${dataBindUrl}.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
<#list pkgs as ps>
	<#if ps??>
import ${ps};
	</#if>
</#list>

/**   
 * @Description:TODO(${entityComment}Vo)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 * 
 */
@ApiModel(value="${entityName}Vo对象", description="${entityComment}Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ${entityName}Vo extends ${entityName} implements Serializable {

	private static final long serialVersionUID = ${agile}L;

    <#list voInfoList?sort_by(["id"]) as vo>
        <#if "${vo.bindAnnotation}"=="DataBindDict">
    @${vo.bindAnnotation}(sourceField = "#${vo.property}", sourceFieldCombination = "${vo.bindSourceFieldCombination}")
    @ApiModelProperty(name = "${vo.property}Name" , value = "${vo.comment}")
        </#if>
        <#if "${vo.bindAnnotation}"!="DataBindDict" >
    @${vo.bindAnnotation}(sourceField = "#${vo.property}")
    @ApiModelProperty(name = "${vo.property}Name" , value = "${vo.comment}")
        </#if>
    private ${vo.javaType} ${vo.property}Name;

    </#list>
}
	