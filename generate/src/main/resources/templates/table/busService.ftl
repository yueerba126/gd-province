package ${serviceUrl};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${entityUrl}.${entityName};
import ${saveParamUrl}.${entityName}SaveParam;
import ${pageParamUrl}.${entityName}PageParam;
import ${voUrl}.${entityName}Vo;
import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(${entityComment}业务服务层)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 *
 */
public interface IBus${entityName}Service extends IService<${entityName}> {

    /**
     * 分页列表返回之前的处理
     */
    void beforeReturnPage(Page<${entityName}> page);

    /**
     * 详情返回之前的处理
     */
    void beforeReturnDetail(${entityName}Vo ${entityName?uncap_first}Vo);

    /**
     * 详情列表返回之前的处理
     */
    void beforeReturnDetailList(List<${entityName}Vo>  ${entityName?uncap_first}VoList);

    /**
     * 保存之前的处理
     */
    void beforeDoSave(${entityName}  ${entityName?uncap_first});

    /**
     * 更新之前的处理
     */
    void beforeDoUpdate(${entityName}  ${entityName?uncap_first});

    /**
     * 删除之前的处理
     */
    void beforeDoRemove(List<String> ids);

}