package ${daoUrl};

import org.apache.ibatis.annotations.Mapper;
import ${pageParamUrl}.${entityName}SelectParam;
import ${voUrl}.${entityName}Vo;
import java.util.List;

/**   
 * @Description:TODO(${entityComment}数据访问层)
 *
 * @version: ${version}
 * @author: ${author}
 * @date    ${createDate}
 */
@Mapper
public interface ${entityName}Mapper {
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
	