package com.sydata.organize.permission.rewrite;

import com.sydata.organize.enums.LoginDeviceEnum;
import com.sydata.organize.permission.IDataPermissionSqlRewrite;
import com.sydata.organize.permission.extend.ICompanyExtend;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DOT;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.EMPTY;
import static com.sydata.organize.enums.DataViewEnum.ASCRIPTION;
import static com.sydata.organize.enums.OrganizeKindEnum.ENTERPRISE;
import static java.util.Collections.emptySet;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author lzq
 * @description 企业单位sql改写器
 * @date 2022/10/21 20:12
 */
@Component
public class CompanySqlRewrite implements IDataPermissionSqlRewrite {

    private static final String ENTERPRISE_ID_COLUMN = "enterprise_id";

    @Resource
    private ICompanyExtend companyExtend;

    @Override
    public boolean isProcess(String table) {
        LoginUser loginUser = UserSecurity.loginUser();
        String dataView = UserSecurity.getDataView();
        String organizeKind = loginUser.getOrganizeKind();
        boolean allow = StockHouseSqlRewrite.refuse() == null;
        Set<String> stockHouseIds = allow ? UserSecurity.getStockHouseIds() : emptySet();

        return ASCRIPTION.getView().equals(dataView) &&
                ENTERPRISE.getKind().equals(organizeKind) &&
                isEmpty(stockHouseIds);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void processSelect(PlainSelect plainSelect) {
        // 获取查询的表信息
        Table table = (Table) plainSelect.getFromItem();
        String tableAlias = Optional.ofNullable(table.getAlias()).map(s -> s + DOT).orElse(EMPTY);
        Column tableAliasColumn = new Column().withColumnName(tableAlias + ENTERPRISE_ID_COLUMN);

        // 当前单位
        LoginUser loginUser = UserSecurity.loginUser();
        String companyId = loginUser.getOrganizeId();
        List<String> companyIds = new ArrayList<>();
        companyIds.add(companyId);

        // API对接账号不需要拼上子单位
        if (!LoginDeviceEnum.API.getDevice().equals(loginUser.getLoginDevice())) {
            List<String> sonIds = companyExtend.sonIds(companyId);
            if (CollectionUtils.isNotEmpty(sonIds)) {
                companyIds.addAll(sonIds);
            }
        }

        ExpressionList expressionList = StreamEx.of(companyIds)
                .map(StringValue::new)
                .map(s -> (Expression) s)
                .toListAndThen(ExpressionList::new);
        InExpression inExpression = new InExpression(tableAliasColumn, expressionList);

        Expression oldWhere = plainSelect.getWhere();
        Expression where = oldWhere == null ? inExpression : new AndExpression(oldWhere, inExpression);
        plainSelect.setWhere(where);
    }
}
