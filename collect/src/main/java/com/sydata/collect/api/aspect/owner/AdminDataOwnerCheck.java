package com.sydata.collect.api.aspect.owner;

import cn.dev33.satoken.spring.SpringMVCUtil;
import com.sydata.collect.api.aspect.IDataOwnerCheck;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.framework.core.exception.WebSecurityException;
import com.sydata.framework.util.AesUtil;
import com.sydata.organize.domain.AppApiStockHouse;
import com.sydata.organize.enums.OrganizeKindEnum;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IAppApiStockHouseService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.sydata.framework.util.StringUtils.isEmpty;
import static com.sydata.framework.util.StringUtils.isNotEmpty;

/**
 * @author lzq
 * @description 行政组织数据归属权限校验
 * @date 2023/5/30 15:35
 */
@Component
public class AdminDataOwnerCheck implements IDataOwnerCheck {

    @Resource
    private IAppApiStockHouseService appApiStockHouseService;

    @Override
    public OrganizeKindEnum type() {
        return OrganizeKindEnum.ADMIN;
    }

    @Override
    public void checkDataOwner(BaseApiParam apiParam, LoginUser login) {
        // 从报文头获取库区并且解密
        HttpServletRequest request = SpringMVCUtil.getRequest();
        String stockHouseIdSecret = request.getHeader("stockHouse");
        WebSecurityException.state(isNotEmpty(stockHouseIdSecret), "报文头未携带库区代码");
        String stockHouseId = AesUtil.decryptBase64(UserSecurity.loginUser().getAppSecret(), stockHouseIdSecret);

        // 校验该应用是否授权了这个库区
        AppApiStockHouse appApiStockHouse = appApiStockHouseService.getByAppStockHouse(login.getId(), stockHouseId);
        Assert.notNull(appApiStockHouse, "该应用未授权这个库区");

        // 比较参数企业ID和当前app库区的企业ID是否一致 -- 只允许传输本企业的数据
        String dataCompanyId = apiParam.getBuildCompanyId();
        boolean companyOwner = isEmpty(dataCompanyId) || dataCompanyId.equals(appApiStockHouse.getEnterpriseId());
        Assert.state(companyOwner, "数据归属企业校验失败：上传数据与该企业无关");

        // 比较参数库区ID和当前app库区的库区ID是否一致 -- 只允许传输本库区的数据
        String dataStockHouseId = apiParam.getBuildStockHouseId();
        boolean stockHouseOwner = isEmpty(dataStockHouseId) || isEmpty(stockHouseId) || dataStockHouseId.equals(stockHouseId);
        Assert.state(stockHouseOwner, "数据归属库区校验失败：上传数据与该库区无关");

        // 登录身份临时切换为app库区所属的【企业、库区、行政区划、国、省、市、区】
        login.setOrganizeId(appApiStockHouse.getEnterpriseId())
                .setStockHouseId(appApiStockHouse.getStockHouseId())
                .setRegionId(appApiStockHouse.getRegionId())
                .setCountryId(appApiStockHouse.getCountryId())
                .setProvinceId(appApiStockHouse.getProvinceId())
                .setCityId(appApiStockHouse.getCityId())
                .setAreaId(appApiStockHouse.getAreaId());
    }
}
