package com.sydata.collect.api.aspect.owner;

import com.sydata.collect.api.aspect.IDataOwnerCheck;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.organize.enums.OrganizeKindEnum;
import com.sydata.organize.security.LoginUser;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static com.sydata.framework.util.StringUtils.isEmpty;

/**
 * @author lzq
 * @description 企业组织数据归属权限校验
 * @date 2023/5/30 15:35
 */
@Component
public class EnterpriseDataOwnerCheck implements IDataOwnerCheck {

    @Override
    public OrganizeKindEnum type() {
        return OrganizeKindEnum.ENTERPRISE;
    }

    @Override
    public void checkDataOwner(BaseApiParam apiParam, LoginUser login) {
        // 比较参数企业ID和当前组织ID是否一致 -- 只允许传输本公司的数据
        String dataCompanyId = apiParam.getBuildCompanyId();
        boolean companyOwner = isEmpty(dataCompanyId) || dataCompanyId.equals(login.getOrganizeId());
        Assert.state(companyOwner, "数据归属企业校验失败：上传数据与该企业无关");

        // 比较参数库区ID和当前库区ID是否一致 -- 只允许传输本库区的数据
        String dataStockHouseId = apiParam.getBuildStockHouseId();
        String stockHouseId = login.getStockHouseId();
        boolean stockHouseOwner = isEmpty(dataStockHouseId) || isEmpty(stockHouseId) || dataStockHouseId.equals(stockHouseId);
        Assert.state(stockHouseOwner, "数据归属库区校验失败：上传数据与该库区无关");
    }
}
