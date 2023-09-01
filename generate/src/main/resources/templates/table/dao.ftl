package ${daoUrl};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import ${entityUrl}.${entityName};

/**   
 * @Description:TODO(${entityComment}数据访问层)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 * 
 */
@DataPermissionExclude
@Mapper
public interface ${entityName}Mapper extends BaseMapper<${entityName}> {
	
}
	