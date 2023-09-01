package ${serviceImplUrl};
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.lang3.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import ${daoUrl}.${entityName}Mapper;
import ${serviceUrl}.I${entityName}Service;
import ${pageParamUrl}.${entityName}SelectParam;
import ${voUrl}.${entityName}Vo;
import com.sydata.common.api.enums.CzBzEnum;

/**   
 * @Description:TODO(${entityComment}服务实现)
 *
 * @version: ${version}
 * @author: ${author}
 * @date    ${createDate}
 */
@Service("${entityName?uncap_first}Service")
public class ${entityName}ServiceImpl implements I${entityName}Service  {

      @Resource
      private ${entityName}Mapper ${entityName?uncap_first}Mapper;

      <#list methodInfoList as pa>
      /**
      * ${pa.comment}
      *
      * @param  param
      * @return ${pa.returnType}
      */
      @Override
      public ${pa.returnType} ${pa.methodName}(${entityName}SelectParam param){
          return ${entityName?uncap_first}Mapper.${pa.methodName}(param);
      }
      </#list>

}