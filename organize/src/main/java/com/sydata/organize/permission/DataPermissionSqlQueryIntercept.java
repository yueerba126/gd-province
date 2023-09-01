package com.sydata.organize.permission;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DOT;
import static java.lang.Boolean.TRUE;

/**
 * @author lzq
 * @description 数据权限SQL查询拦截器
 * @date 2022/10/21 20:02
 */
@Component
@Slf4j
public class DataPermissionSqlQueryIntercept extends JsqlParserSupport implements InnerInterceptor {

    @Resource
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    @Resource
    private List<IDataPermissionSqlRewrite> sqlRewrites;

    private static final ThreadLocal<Boolean> RELEASE_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 放行状态设置
     */
    public static void release() {
        RELEASE_THREAD_LOCAL.set(TRUE);
    }

    /**
     * 清理状态位
     */
    public static void clear() {
        RELEASE_THREAD_LOCAL.remove();
    }

    /**
     * 放行执行
     *
     * @param callable 执行Callable方法
     * @return 返回值
     */
    @SneakyThrows(Throwable.class)
    public static <V> V releaseRun(Callable<V> callable) {
        try {
            release();
            return callable.call();
        } finally {
            clear();
        }
    }


    /**
     * 放行执行
     *
     * @param runnable 执行Runnable方法
     */
    public static void releaseRun(Runnable runnable) {
        try {
            release();
            runnable.run();
        } finally {
            clear();
        }
    }

    @PostConstruct
    public void init() {
        List<InnerInterceptor> interceptors = new ArrayList<>(mybatisPlusInterceptor.getInterceptors());
        interceptors.add(0, this);
        mybatisPlusInterceptor.setInterceptors(interceptors);
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        List<IDataPermissionSqlRewrite> sqlRewrites = (List<IDataPermissionSqlRewrite>) obj;
        PlainSelect selectBody = (PlainSelect) select.getSelectBody();
        sqlRewrites.forEach(sqlRewrite -> sqlRewrite.processSelect(selectBody));
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter,
                            RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        // 有设置放行令牌的不拦
        Boolean release = RELEASE_THREAD_LOCAL.get();
        if (release != null && release) {
            return;
        }

        // 获取不到用户信息不拦截
        LoginUser loginUser = UserSecurity.loginUser();
        if (loginUser == null) {
            return;
        }

        // 需要排除的SQL不拦截《类级别排除、方法级别排除》
        String id = ms.getId();
        Class<?> mapperClassType = Class.forName(id.substring(0, id.lastIndexOf(DOT)));
        if (mapperClassType.isAnnotationPresent(DataPermissionExclude.class)) {
            return;
        }

        Method method = ReflectUtil.getMethodByName(mapperClassType, id.substring(id.lastIndexOf(DOT) + 1));
        if (method.isAnnotationPresent(DataPermissionExclude.class)) {
            return;
        }

        // 获取需要执行的SQL
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        String sql = mpBs.sql();

        // 分析表和数据权限SQL重写器映射关系
        List<String> tables = new TablesNamesFinder().getTableList(CCJSqlParserUtil.parse(sql));
        Map<String, List<IDataPermissionSqlRewrite>> tableRewriteMap = loopIntercept(tables);
        if (MapUtil.isEmpty(tableRewriteMap)) {
            return;
        }

        // 目前都是单表,直接获取第一个表过滤数据权限
        List<IDataPermissionSqlRewrite> sqlRewrites = tableRewriteMap.get(tables.get(0));
        String newSql = super.parserSingle(sql, sqlRewrites);

        // 替换SQL
        mpBs.sql(newSql);
    }

    /**
     * 轮询查询的表是否需要拦截
     *
     * @param tables 表集合
     * @return 表和数据权限SQL重写器映射关系
     */
    private Map<String, List<IDataPermissionSqlRewrite>> loopIntercept(List<String> tables) {
        if (CollectionUtils.isEmpty(tables)) {
            return Collections.emptyMap();
        }

        Map<String, List<IDataPermissionSqlRewrite>> tableRewriteMap = MapUtil.newHashMap();
        tables.forEach(table -> {
            List<IDataPermissionSqlRewrite> rewrites = StreamEx.of(sqlRewrites)
                    .filter(sqlRewrite -> sqlRewrite.isProcess(table))
                    .toList();
            if (CollectionUtils.isNotEmpty(rewrites)) {
                tableRewriteMap.put(table, rewrites);
            }
        });
        return tableRewriteMap;
    }
}
