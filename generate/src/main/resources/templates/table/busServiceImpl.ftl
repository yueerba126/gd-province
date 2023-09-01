package ${serviceImplUrl};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ${entityUrl}.${entityName};
import ${saveParamUrl}.${entityName}SaveParam;
import ${pageParamUrl}.${entityName}PageParam;
import ${voUrl}.${entityName}Vo;
import ${daoUrl}.${entityName}Mapper;
import ${serviceUrl}.I${entityName}Service;
import ${serviceUrl}.IBus${entityName}Service;
import java.util.Collection;
import java.util.List;

/**   
 * @Description:TODO(${entityComment}业务服务实现)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 * 
 */
@Service("bus${entityName}Service")
public class Bus${entityName}ServiceImpl extends ServiceImpl<${entityName}Mapper, ${entityName}> implements IBus${entityName}Service {

    @Override
    public void beforeReturnPage(Page<${entityName}> page){};

    @Override
    public void beforeReturnDetail(${entityName}Vo ${entityName?uncap_first}Vo){};

    @Override
    public void beforeReturnDetailList(List<${entityName}Vo>  ${entityName?uncap_first}VoList){};

    @Override
    public void beforeDoSave(${entityName}  ${entityName?uncap_first}){};

    @Override
    public void beforeDoUpdate(${entityName}  ${entityName?uncap_first}){};

    @Override
    public void beforeDoRemove(List<String> ids){};

}