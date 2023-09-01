package com.sydata.organize.permission.rewrite;

import com.sydata.organize.permission.IDataPermissionSqlRewrite;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DOT;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.EMPTY;
import static com.sydata.organize.enums.DataViewEnum.ASCRIPTION;
import static com.sydata.organize.enums.OrganizeKindEnum.ADMIN;
import static java.util.Collections.emptySet;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author lzq
 * @description 行政区域sql改写器
 * @date 2022/10/21 20:12
 */
@Component
public class RegionSqlRewrite implements IDataPermissionSqlRewrite {

    private static final List<String> REGIONS = Arrays.asList("country_id", "province_id", "city_id", "area_id");

    @Override
    public boolean isProcess(String table) {
        LoginUser loginUser = UserSecurity.loginUser();
        String dataView = UserSecurity.getDataView();
        String organizeKind = loginUser.getOrganizeKind();
        boolean allow = StockHouseSqlRewrite.refuse() == null;
        Set<String> stockHouseIds = allow ? UserSecurity.getStockHouseIds() : emptySet();

        return ASCRIPTION.getView().equals(dataView) &&
                ADMIN.getKind().equals(organizeKind) &&
                isEmpty(stockHouseIds);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void processSelect(PlainSelect plainSelect) {
        // 获取查询的表信息
        Table table = (Table) plainSelect.getFromItem();
        String tableAlias = Optional.ofNullable(table.getAlias()).map(s -> s + DOT).orElse(EMPTY);

        // 组装SQL--加上国、省、市、区and条件
        LoginUser loginUser = UserSecurity.loginUser();
        List<String> regions = StreamEx.of(loginUser.getCountryId())
                .append(loginUser.getProvinceId())
                .append(loginUser.getCityId())
                .append(loginUser.getAreaId())
                .filter(ObjectUtils::isNotEmpty)
                .toList();

        StringJoiner condition = new StringJoiner(" and ");
        for (int i = 0; i < regions.size(); i++) {
            String column = REGIONS.get(i);
            String value = regions.get(i);
            condition.add(tableAlias + column + String.format(" = '%s'", value));
        }
        Expression expression = CCJSqlParserUtil.parseCondExpression(condition.toString());

        Expression oldWhere = plainSelect.getWhere();
        Expression where = oldWhere == null ? expression : new AndExpression(oldWhere, expression);
        plainSelect.setWhere(where);
    }
}
