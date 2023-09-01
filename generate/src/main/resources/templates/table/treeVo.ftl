package ${voUrl};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import ${entityUrl}.${entityName};
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import ${dataBindUrl}.*;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
<#list pkgs as ps>
	<#if ps??>
import ${ps};
	</#if>
</#list>

/**   
 * @Description:TODO(${entityComment}TreeVo)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 * 
 */
@ApiModel(value="${entityName}TreeVo对象", description="${entityComment}TreeVo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ${entityName}TreeVo extends ${entityName} implements Serializable {

	private static final long serialVersionUID = ${agile}L;

    @ApiModelProperty(value = "子节点")
    private List<${entityName}TreeVo> child;

}
	