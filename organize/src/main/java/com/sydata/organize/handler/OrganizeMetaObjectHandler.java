package com.sydata.organize.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

import static com.sydata.framework.util.StringUtils.isNotEmpty;

/**
 * 组织架构元数据填充处理
 *
 * @author lzq
 * @date 2022-06-28
 */
@Slf4j
@Component
public class OrganizeMetaObjectHandler implements MetaObjectHandler {

    public static final String ID = "id";
    public static final String CZBZ = "czbz";
    public static final String CREATE_BY = "createBy";
    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_BY = "updateBy";
    public static final String UPDATE_TIME = "updateTime";

    public static final String REGION_ID = "regionId";
    public static final String COUNTRY_ID = "countryId";
    public static final String PROVINCE_ID = "provinceId";
    public static final String CITY_ID = "cityId";
    public static final String AREA_ID = "areaId";
    public static final String ENTERPRISE_ID = "enterpriseId";
    public static final String STOCK_HOUSE_ID = "stockHouseId";
    public static final String ORGANIZE_ID = "organizeId";

    public static final String DEFAULT_ID = "-1";

    /**
     * 获取登录用户值（无则使用默认值）
     *
     * @param loginUser 登录用户
     * @param field     获取方法
     * @return 获取值
     */
    public static String getFieldVal(LoginUser loginUser, Function<LoginUser, String> field) {
        String fieldVal = field.apply(loginUser);
        return isNotEmpty(fieldVal) ? fieldVal : DEFAULT_ID;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser loginUser = UserSecurity.loginUser();
        if (loginUser == null) {
            return;
        }

        String userName = loginUser.getName();
        LocalDateTime now = LocalDateTime.now();

        if (this.getFieldValByName(CREATE_BY, metaObject) == null) {
            this.strictInsertFill(metaObject, CREATE_BY, String.class, userName);
        }

        if (this.getFieldValByName(CREATE_TIME, metaObject) == null) {
            this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, now);
        }

        if (this.getFieldValByName(UPDATE_BY, metaObject) == null) {
            this.strictInsertFill(metaObject, UPDATE_BY, String.class, userName);
        }

        if (this.getFieldValByName(UPDATE_TIME, metaObject) == null) {
            this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime.class, now);
        }

        if (this.getFieldValByName(REGION_ID, metaObject) == null) {
            String regionId = isNotEmpty(loginUser.getRegionId()) ? loginUser.getRegionId() : DEFAULT_ID;
            this.strictInsertFill(metaObject, REGION_ID, String.class, regionId);
        }

        if (this.getFieldValByName(COUNTRY_ID, metaObject) == null) {
            String countryId = isNotEmpty(loginUser.getCountryId()) ? loginUser.getCountryId() : DEFAULT_ID;
            this.strictInsertFill(metaObject, COUNTRY_ID, String.class, countryId);
        }

        if (this.getFieldValByName(PROVINCE_ID, metaObject) == null) {
            String provinceId = isNotEmpty(loginUser.getProvinceId()) ? loginUser.getProvinceId() : DEFAULT_ID;
            this.strictInsertFill(metaObject, PROVINCE_ID, String.class, provinceId);
        }

        if (this.getFieldValByName(CITY_ID, metaObject) == null) {
            String cityId = isNotEmpty(loginUser.getCityId()) ? loginUser.getCityId() : DEFAULT_ID;
            this.strictInsertFill(metaObject, CITY_ID, String.class, cityId);
        }

        if (this.getFieldValByName(AREA_ID, metaObject) == null) {
            String areaId = isNotEmpty(loginUser.getAreaId()) ? loginUser.getAreaId() : DEFAULT_ID;
            this.strictInsertFill(metaObject, AREA_ID, String.class, areaId);
        }

        if (this.getFieldValByName(ENTERPRISE_ID, metaObject) == null) {
            String organizeId = isNotEmpty(loginUser.getOrganizeId()) ? loginUser.getOrganizeId() : DEFAULT_ID;
            this.strictInsertFill(metaObject, ENTERPRISE_ID, String.class, organizeId);
        }

        if (this.getFieldValByName(STOCK_HOUSE_ID, metaObject) == null) {
            String stockHouseId = isNotEmpty(loginUser.getStockHouseId()) ? loginUser.getStockHouseId() : DEFAULT_ID;
            this.strictInsertFill(metaObject, STOCK_HOUSE_ID, String.class, stockHouseId);
        }

        if (this.getFieldValByName(ORGANIZE_ID, metaObject) == null) {
            String organizeId = isNotEmpty(loginUser.getOrganizeId()) ? loginUser.getOrganizeId() : DEFAULT_ID;
            this.strictInsertFill(metaObject, ORGANIZE_ID, String.class, organizeId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LoginUser loginUser = UserSecurity.loginUser();
        if (loginUser == null) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        String userName = loginUser.getName();

        if (this.getFieldValByName(UPDATE_BY, metaObject) == null) {
            this.strictUpdateFill(metaObject, UPDATE_BY, String.class, userName);
        }

        if (this.getFieldValByName(UPDATE_TIME, metaObject) == null) {
            this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime.class, now);
        }

        if (this.getFieldValByName(REGION_ID, metaObject) == null) {
            String regionId = isNotEmpty(loginUser.getRegionId()) ? loginUser.getRegionId() : DEFAULT_ID;
            this.strictUpdateFill(metaObject, REGION_ID, String.class, regionId);
        }

        if (this.getFieldValByName(COUNTRY_ID, metaObject) == null) {
            String countryId = isNotEmpty(loginUser.getCountryId()) ? loginUser.getCountryId() : DEFAULT_ID;
            this.strictUpdateFill(metaObject, COUNTRY_ID, String.class, countryId);
        }

        if (this.getFieldValByName(PROVINCE_ID, metaObject) == null) {
            String provinceId = isNotEmpty(loginUser.getProvinceId()) ? loginUser.getProvinceId() : DEFAULT_ID;
            this.strictUpdateFill(metaObject, PROVINCE_ID, String.class, provinceId);
        }

        if (this.getFieldValByName(CITY_ID, metaObject) == null) {
            String cityId = isNotEmpty(loginUser.getCityId()) ? loginUser.getCityId() : DEFAULT_ID;
            this.strictUpdateFill(metaObject, CITY_ID, String.class, cityId);
        }

        if (this.getFieldValByName(AREA_ID, metaObject) == null) {
            String areaId = isNotEmpty(loginUser.getAreaId()) ? loginUser.getAreaId() : DEFAULT_ID;
            this.strictUpdateFill(metaObject, AREA_ID, String.class, areaId);
        }

        if (this.getFieldValByName(ENTERPRISE_ID, metaObject) == null) {
            String organizeId = isNotEmpty(loginUser.getOrganizeId()) ? loginUser.getOrganizeId() : DEFAULT_ID;
            this.strictUpdateFill(metaObject, ENTERPRISE_ID, String.class, organizeId);
        }

        if (this.getFieldValByName(STOCK_HOUSE_ID, metaObject) == null) {
            String stockHouseId = isNotEmpty(loginUser.getStockHouseId()) ? loginUser.getStockHouseId() : DEFAULT_ID;
            this.strictUpdateFill(metaObject, STOCK_HOUSE_ID, String.class, stockHouseId);
        }

        if (this.getFieldValByName(ORGANIZE_ID, metaObject) == null) {
            String organizeId = isNotEmpty(loginUser.getOrganizeId()) ? loginUser.getOrganizeId() : DEFAULT_ID;
            this.strictInsertFill(metaObject, ORGANIZE_ID, String.class, organizeId);
        }
    }
}