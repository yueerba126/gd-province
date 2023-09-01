package ${serviceUrl};
import ${pageParamUrl}.${entityName}SelectParam;
import ${voUrl}.${entityName}Vo;
import java.util.List;
/**   
 * @Description:TODO(${entityComment}服务层)
 * @version: ${version}
 * @author: ${author}
 * @date    ${createDate}
 */
public interface I${entityName}Service {
    <#list methodInfoList as pa>
     /**
     * ${pa.comment}
     *
     * @param  param
     * @return ${pa.returnType}
     */
      ${pa.returnType} ${pa.methodName}(${entityName}SelectParam param);
    </#list>
}