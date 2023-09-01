package ${pageParamUrl};
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
<#list pkgs as ps>
	<#if ps??>
import ${ps};
	</#if>
</#list>

/**   
 * @Description:TODO(${entityComment}-查询参数)
 * 
 * @version: ${version}
 * @author: ${author}
 * @date    ${createDate}
 */
@ApiModel(value="${entityName}SelectParam对象", description="${entityComment}-查询参数")
@Data
@ToString
@Accessors(chain = true)
public class ${entityName}SelectParam implements Serializable {

	private static final long serialVersionUID = ${agile}L;

    @ApiModelProperty(name = "id" , value = "id")
    private String id;

}
	