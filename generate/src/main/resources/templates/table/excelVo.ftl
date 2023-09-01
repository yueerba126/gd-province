package ${voUrl};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.*;
import lombok.experimental.Accessors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
<#list pkgs as ps>
    <#if ps??>
import ${ps};
    </#if>
</#list>

/**
* @Description:TODO(${entityComment}ExcelVo)
* @date ${createDate}
* @version: ${version}
* @author: ${author}
*
*/
@ApiModel(value="${entityName}ExcelVo对象", description="${entityComment}ExcelVo")
@Data
@ToString
@Accessors(chain = true)
public class ${entityName}ExcelVo implements Serializable {

    private static final long serialVersionUID = ${agile}L;

<#list excelVoExcludeAfterList?sort_by(["id"]) as ci>
    @Excel(name = "${ci.comment}")
    @ApiModelProperty(name = "${ci.property}" ,value = "${ci.comment}")
    private ${ci.javaType} ${ci.property};

</#list>
<#list voInfoList as vo>
    @Excel(name = "${vo.comment}")
    @ApiModelProperty(name = "${vo.property}Name" , value = "${vo.comment}")
    private ${vo.javaType} ${vo.property}Name;

</#list>
}
	