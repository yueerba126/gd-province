package ${voUrl};
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.io.Serializable;
<#list pkgs as ps>
    <#if ps??>
import ${ps};
    </#if>
</#list>

/**
 * @Description:TODO(${entityComment}Vo)
 *
 * @version: ${version}
 * @author: ${author}
 * @date    ${createDate}
 */
@ApiModel(value="${entityName}Vo对象", description="${entityComment}")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ${entityName}Vo implements Serializable {

    private static final long serialVersionUID = ${agile}L;

    @ApiModelProperty(name = "id" , value = "id")
    private String id;

}
	