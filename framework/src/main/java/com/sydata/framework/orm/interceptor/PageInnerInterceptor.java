package com.sydata.framework.orm.interceptor;

import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.SetUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.baomidou.mybatisplus.core.toolkit.Constants.MYBATIS_PLUS;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.DOT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * @author lzq
 * @description 分页拦截器 1根据条件查询count 2根据条件查询id 3根据id查询数据
 * @date 2022/11/22 19:00
 */
public class PageInnerInterceptor extends PaginationInnerInterceptor {

    private static final Column ID_COLUMN = new Column().withColumnName("id");

    private static final List<SelectItem> SELECT_ID_ITEMS = singletonList(
            new SelectExpressionItem(ID_COLUMN).withAlias(new Alias("id"))
    );

    private static final Set<String> LIMITS = SetUtils.hashSet("mybatis_plus_first", "mybatis_plus_second");


    public PageInnerInterceptor(DbType dbType) {
        super(dbType);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter,
                            RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        IPage page = ParameterUtils.findPage(parameter).orElse(null);
        if (null == page) {
            return;
        }

        // 先执行父类查询 -- 组装好SQL
        super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);

        // 排除不需要优化的
        String id = ms.getId();

        // 类排除
        int classIndex = id.lastIndexOf(DOT);
        String className = id.substring(0, classIndex);
        Class mapperClassType = Class.forName(className);
        boolean classExclude = mapperClassType.isAnnotationPresent(PageOptimizationExclude.class);
        if (classExclude) {
            return;
        }

        // 方法排除
        Method[] methods = ClassUtil.getPublicMethods(mapperClassType);
        Map<String, List<Method>> methodNameMap = StreamEx.of(methods).groupingBy(Method::getName);
        String methodName = id.substring(classIndex + 1);
        boolean methodExclude = methodNameMap.get(methodName).get(0).isAnnotationPresent(PageOptimizationExclude.class);
        if (methodExclude) {
            return;
        }

        // 查询ID集合
        List<Object> ids = selectIds(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // SQL拼上条件in id集合
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        Select select = (Select) CCJSqlParserUtil.parse(mpBs.sql());
        PlainSelect selectBody = (PlainSelect) select.getSelectBody();

        ExpressionList expressionList = StreamEx.of(ids)
                .map(Object::toString)
                .map(StringValue::new)
                .map(s -> (Expression) s)
                .toListAndThen(ExpressionList::new);
        InExpression inExpression = new InExpression(ID_COLUMN, expressionList);
        Expression oldWhere = selectBody.getWhere();
        Expression where = oldWhere == null ? inExpression : new AndExpression(oldWhere, inExpression);
        selectBody.setWhere(where);

        // 去除mybatisPlus拼接的limit参数
        List<ParameterMapping> oldParamMaps = mpBs.parameterMappings();
        List<ParameterMapping> paramMaps = CollectionUtils.isEmpty(oldParamMaps) ? emptyList() : oldParamMaps;
        List<ParameterMapping> parameterMappings = StreamEx.of(paramMaps)
                .filter(p -> !LIMITS.contains(p.getProperty()))
                .toList();
        mpBs.parameterMappings(parameterMappings);
        selectBody.setLimit(null);

        // 设置执行SQL
        mpBs.sql(selectBody.toString());
    }

    /**
     * 查询ID集合
     */
    @SneakyThrows(Throwable.class)
    private List<Object> selectIds(Executor executor, MappedStatement ms, Object parameter,
                                   RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {

        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        Select select = (Select) CCJSqlParserUtil.parse(mpBs.sql());
        PlainSelect selectBody = (PlainSelect) select.getSelectBody();
        selectBody.setSelectItems(SELECT_ID_ITEMS);
        String idSql = selectBody.toString();

        MappedStatement mpIdsMs = buildAutoIdsMappedStatement(ms);
        BoundSql selectIdSql = new BoundSql(mpIdsMs.getConfiguration(), idSql, mpBs.parameterMappings(), parameter);
        PluginUtils.setAdditionalParameter(selectIdSql, mpBs.additionalParameters());

        CacheKey cacheKey = executor.createCacheKey(mpIdsMs, parameter, rowBounds, selectIdSql);
        return executor.query(mpIdsMs, parameter, rowBounds, resultHandler, cacheKey, selectIdSql);
    }

    /**
     * 创建查询ID的MappedStatement
     *
     * @param ms 分页的MappedStatement
     * @return 查询ID的MappedStatement
     */
    private MappedStatement buildAutoIdsMappedStatement(MappedStatement ms) {
        final String countId = ms.getId() + "_mpIds";
        final Configuration configuration = ms.getConfiguration();
        return CollectionUtils.computeIfAbsent(countMsCache, countId, key -> {
            MappedStatement.Builder builder = new MappedStatement
                    .Builder(configuration, key, ms.getSqlSource(), ms.getSqlCommandType());

            ResultMap resultMap = new ResultMap.Builder(configuration, MYBATIS_PLUS, Object.class, emptyList()).build();

            builder.resource(ms.getResource());
            builder.fetchSize(ms.getFetchSize());
            builder.statementType(ms.getStatementType());
            builder.timeout(ms.getTimeout());
            builder.parameterMap(ms.getParameterMap());
            builder.resultMaps(singletonList(resultMap));
            builder.resultSetType(ms.getResultSetType());
            builder.cache(ms.getCache());
            builder.flushCacheRequired(ms.isFlushCacheRequired());
            builder.useCache(ms.isUseCache());
            return builder.build();
        });
    }
}
