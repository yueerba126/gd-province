package ${entityUrl};
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
<#list pkgs as ps>
    <#if ps??>
import ${ps};
    </#if>
</#list>

/**
 * @Description:TODO(${entityComment}实体类)
 *
 * @version: ${version}
 * @author: ${author}
 * @date    ${createDate}
 */
@ApiModel(value="${entityName}对象", description="${entityComment}")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ${entityName} implements Serializable {

    private static final long serialVersionUID = ${agile}L;

    @ApiModelProperty(name = "id" , value = "id")
    private String id;

}
	