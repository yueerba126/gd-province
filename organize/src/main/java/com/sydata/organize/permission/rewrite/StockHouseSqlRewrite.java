package com.sydata.organize.permission.rewrite;

import com.sydata.organize.permission.DataPermissionSqlQueryIntercept;
import com.sydata.organize.permission.IDataPermissionSqlRewrite;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IFoodOwnerService;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DOT;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.EMPTY;
import static com.sydata.organize.enums.DataViewEnum.STOCK;
import static com.sydata.organize.enums.OrganizeKindEnum.ADMIN;
import static com.sydata.organize.enums.OrganizeKindEnum.ENTERPRISE;
import static java.lang.Boolean.TRUE;
import static java.util.Collections.emptySet;

/**
 * @author lzq
 * @description 库区sql改写器
 * @date 2022/12/06 20:12
 */
@Component
public class StockHouseSqlRewrite implements IDataPermissionSqlRewrite {

    private static final String STOCK_HOUSE_ID_COLUMN = "stock_house_id";

    private static final ThreadLocal<Boolean> REFUSE_THREAD_LOCAL = new ThreadLocal<>();

    @Resource
    private IFoodOwnerService foodOwnerService;

    /**
     * 拒绝查询已选择的库区
     */
    public static void refuseSelect() {
        REFUSE_THREAD_LOCAL.set(TRUE);
    }

    /**
     * 清理状态位
     */
    public static void clear() {
        REFUSE_THREAD_LOCAL.remove();
    }

    /**
     * 获取状态位
     */
    public static Boolean refuse() {
        return REFUSE_THREAD_LOCAL.get();
    }

    @Override
    public boolean isProcess(String table) {
        String dataView = UserSecurity.getDataView();
        boolean allow = StockHouseSqlRewrite.refuse() == null;
        Set<String> stockHouseIds = allow ? UserSecurity.getStockHouseIds() : emptySet();

        // 数据视图为粮权关系或已选择库区
        return STOCK.getView().equals(dataView) || CollectionUtils.isNotEmpty(stockHouseIds);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void processSelect(PlainSelect plainSelect) {
        // 获取查询的表信息
        Table table = (Table) plainSelect.getFromItem();
        String tableAlias = Optional.ofNullable(table.getAlias()).map(s -> s + DOT).orElse(EMPTY);
        Column tableAliasColumn = new Column().withColumnName(tableAlias + STOCK_HOUSE_ID_COLUMN);

        // 指定了库区并且允许查询指定的库区就查指定的库区
        boolean allow = StockHouseSqlRewrite.refuse() == null;
        Set<String> stockHouseIds = allow ? UserSecurity.getStockHouseIds() : emptySet();

        // 数据视图为粮权关系且没有指定库区，就根据组织身份找粮权库区
        boolean isStockDataView = STOCK.getView().equals(UserSecurity.getDataView());
        if (isStockDataView && CollectionUtils.isEmpty(stockHouseIds)) {
            LoginUser loginUser = UserSecurity.loginUser();
            stockHouseIds = DataPermissionSqlQueryIntercept.releaseRun(() -> {
                if (ENTERPRISE.getKind().equals(loginUser.getOrganizeKind())) {
                    return foodOwnerService.stockHouseIdsByCompanyId(loginUser.getOrganizeId());
                } else if (ADMIN.getKind().equals(loginUser.getOrganizeKind())) {
                    return foodOwnerService.stockHouseIdsByRegionId(loginUser.getRegionId());
                }
                return emptySet();
            });
        }

        // 有库区就加库区条件查询,没有库区就拼上1!=1的条件查不出数据
        Expression condition;
        if (CollectionUtils.isNotEmpty(stockHouseIds)) {
            ExpressionList expressionList = StreamEx.of(stockHouseIds)
                    .map(StringValue::new)
                    .map(s -> (Expression) s)
                    .toListAndThen(ExpressionList::new);
            condition = new InExpression(tableAliasColumn, expressionList);
        } else {
            condition = CCJSqlParserUtil.parseCondExpression("1 != 1");
        }

        Expression oldWhere = plainSelect.getWhere();
        Expression where = oldWhere == null ? condition : new AndExpression(oldWhere, condition);
        plainSelect.setWhere(where);
    }
}
