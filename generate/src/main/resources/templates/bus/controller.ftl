package ${controllerUrl};

import ${serviceUrl}.I${entityName}Service;
import ${pageParamUrl}.${entityName}SelectParam;
import ${voUrl}.${entityName}Vo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
/**
 * 
 * <p>说明： ${entityComment}API接口层</P>
 * @version: ${version}
 * @author: ${author}
 * @date    ${createDate}
 *
 */
@Api(tags = "${entityComment}",value = "${entityComment}")
@Validated
@RestController
@RequestMapping("/${requestMappingPrefix}/${entityName}")
public class ${entityName}Controller {

    @Resource
    private I${entityName}Service ${entityName?uncap_first}Service;

    <#list methodInfoList as pa>
    @ApiOperation("${pa.comment}")
    @PostMapping("/${pa.methodName?lower_case}")
    public ${pa.returnType} ${pa.methodName}(@RequestBody @Valid ${entityName}SelectParam param) {
        return ${entityName?uncap_first}Service.${pa.methodName}(param);
    }

    </#list>
}