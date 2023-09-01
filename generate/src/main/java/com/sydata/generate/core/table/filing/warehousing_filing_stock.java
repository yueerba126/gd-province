package com.sydata.generate.core.table.filing;
import com.google.common.collect.Lists;
import com.sydata.generate.core.framework.domain.PropertyInfo;

import java.util.List;

/**
 * @program: multiple
 * @description:
 * @author: lzq
 * @create: 2023-06-18 15:53
 */
public class warehousing_filing_stock {
    // 类信息：类名、对象名（一般是【类名】的首字母小些）、类说明、时间
    public static final String PROJECT = "multiple";
    public static final String MODULE = "generate";
    public static final String REQUEST_MAPPING_PREFIX = "filing";
    public static final String BASE_URL = "com.sydata.filing.";
    public static final String TABLE = "warehousing_filing_stock";
    public static final String CLASS_COMMENT = "仓储备案-仓储库点";
    public static final String DATE = "2023年06月16日";
    // 实体类中要排除的字段
    public static final String ENTITY_EXCLUDE
            = "zhgxsj,czbz,createBy,createTime,updateBy,updateTime,regionId,countryId,provinceId,cityId,areaId,enterpriseId,stockHouseId";
    // 在此处添加，分页页面的查询参数
    public static final String PAGE_PARAM_ADD = "id,companyId,dwdm,dwmc,kddm,kdmc";
    // 在此处添加，页面中需要翻译的id，在该字段后会默认添加Name
    public static final List<PropertyInfo> VO_ADD_LIST = Lists.newArrayList();
    static {
        VO_ADD_LIST.add(new PropertyInfo("xzqhdm","DataBindRegion",""));
        VO_ADD_LIST.add(new PropertyInfo("zyjhys","DataBindDict","filing_yw"));
        VO_ADD_LIST.add(new PropertyInfo("lycgjynl","DataBindDict","filing_yw"));
        VO_ADD_LIST.add(new PropertyInfo("lypzjynl","DataBindDict","filing_yw"));
        VO_ADD_LIST.add(new PropertyInfo("zbywwxy","DataBindDict","filing_yw"));
        VO_ADD_LIST.add(new PropertyInfo("zbywwry","DataBindDict","filing_yw"));
    }
    // 确定该表的每条数据唯一的联合键
    public static final String PRIMARY_KEY_ADD = "dwdm,kddm";
    // 是否从表 是否有主表id，没有不填
    public static final String MAIN_TABLE_ID_ADD = "companyId";
    // 导出Excel要排除的字段
    public static final String EXCEL_EXCLUDE = "companyId,xzqhdm,zyjhys,lycgjynl,lypzjynl,zbywwxy,zbywwry"
            + ",id," + ENTITY_EXCLUDE;
}
