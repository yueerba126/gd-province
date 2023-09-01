package com.sydata.generate.core.table.filing;
import com.google.common.collect.Lists;
import com.sydata.generate.core.framework.domain.PropertyInfo;

import java.util.List;

/**
 * @program: multiple
 * @description:
 * @author: lzq
 * @create: 2023-06-18 15:52
 */
public class warehousing_filing_device {
    // 类信息：类名、对象名（一般是【类名】的首字母小些）、类说明、时间
    public static final String PROJECT = "multiple";
    public static final String MODULE = "generate";
    public static final String REQUEST_MAPPING_PREFIX = "filing";
    public static final String BASE_URL = "com.sydata.filing.";
    public static final String TABLE = "warehousing_filing_device";
    public static final String CLASS_COMMENT = "仓储备案-仓储设备";
    public static final String DATE = "2023年06月16日";
    // 实体类中要排除的字段
    public static final String ENTITY_EXCLUDE
            = "zhgxsj,czbz,createBy,createTime,updateBy,updateTime,regionId,countryId,provinceId,cityId,areaId,enterpriseId,stockHouseId";
    // 在此处添加，分页页面的查询参数
    public static final String PAGE_PARAM_ADD = "id,companyId,stockId,sbmc";
    // 在此处添加，页面中需要翻译的id，在该字段后会默认添加Name
    public static final List<PropertyInfo> VO_ADD_LIST = Lists.newArrayList();
    static {
        VO_ADD_LIST.add(new PropertyInfo("companyId","DataBindWarehousingFilingCompany",""));
        VO_ADD_LIST.add(new PropertyInfo("stockId","DataBindWarehousingFilingStock",""));
    }
    // 确定该表的每条数据唯一的联合键
    public static final String PRIMARY_KEY_ADD = "companyId,stockId,sbmc";
    // 是否从表 是否有主表id，没有不填
    public static final String MAIN_TABLE_ID_ADD = "stockId";
    // 导出Excel要排除的字段
    public static final String EXCEL_EXCLUDE = "companyId,stockId"
            + ",id," + ENTITY_EXCLUDE;
}
