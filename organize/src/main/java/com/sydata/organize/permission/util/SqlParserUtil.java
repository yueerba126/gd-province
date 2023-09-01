package com.sydata.organize.permission.util;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * <p>
 * SQL解析工具类
 * </p>
 *
 * @author pangbohuan
 * @date 2022/10/21 20:33
 */
public final class SqlParserUtil {

    public static String changeSql(String sql, Predicate<List<SQLExprTableSource>> predicate) {
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, DbType.mysql);
        List<SQLExprTableSource> sqlExprTableSource = changeSql(statementList);
        boolean test = predicate.test(sqlExprTableSource);
        return test ? SQLUtils.toSQLString(statementList, DbType.mysql) : sql;
    }

    public static boolean changeSql(List<SQLStatement> statementList, Predicate<List<SQLExprTableSource>> predicate) {
        return predicate.test(changeSql(statementList));
    }

    private static List<SQLExprTableSource> changeSql(List<SQLStatement> statementList) {
        List<SQLExprTableSource> sqlExprTableSources = new ArrayList<>();
        statementList.forEach(sqlStatement -> handSqlStatement(sqlExprTableSources, sqlStatement));
        return sqlExprTableSources;
    }

    private static void handSqlStatement(List<SQLExprTableSource> sqlExprTableSources, SQLStatement sqlStatement) {
        if (sqlStatement instanceof SQLSelectStatement) {
            SQLSelect select = ((SQLSelectStatement) sqlStatement).getSelect();
            handSqlSelectQuery(sqlExprTableSources, select.getQuery());
        }
    }

    private static void handSqlSelectQuery(List<SQLExprTableSource> sqlExprTableSources, SQLSelectQuery query) {
        if (query instanceof SQLSelectQueryBlock) {
            handSqlSelectQueryBlock(sqlExprTableSources, (SQLSelectQueryBlock) query);
        } else if (query instanceof SQLUnionQuery) {
            handSqlUnionQuery(sqlExprTableSources, (SQLUnionQuery) query);
        }
    }

    private static void handSqlUnionQuery(List<SQLExprTableSource> sqlExprTableSources, SQLUnionQuery sqlUnionQuery) {
        handSqlSelectQuery(sqlExprTableSources, sqlUnionQuery.getLeft());
        handSqlSelectQuery(sqlExprTableSources, sqlUnionQuery.getRight());
    }

    private static void handSqlSelectQueryBlock(List<SQLExprTableSource> sqlExprTableSources, SQLSelectQueryBlock sqlSelectQueryBlock) {
        sqlSelectQueryBlock.getSelectList().forEach(sqlSelectItem -> handSqlExpr(sqlExprTableSources, sqlSelectItem.getExpr()));
        SQLTableSource from = sqlSelectQueryBlock.getFrom();
        handSqlTableSource(sqlExprTableSources, from);
    }

    private static void handSqlTableSource(List<SQLExprTableSource> sqlExprTableSources, SQLTableSource sqlTableSource) {
        if (sqlTableSource instanceof SQLExprTableSource) {
            sqlExprTableSources.add(((SQLExprTableSource) sqlTableSource));
        } else if (sqlTableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource sqlJoinTableSource = (SQLJoinTableSource) sqlTableSource;
            handSqlTableSource(sqlExprTableSources, sqlJoinTableSource.getLeft());
            handSqlTableSource(sqlExprTableSources, sqlJoinTableSource.getRight());
            handSqlExpr(sqlExprTableSources, sqlJoinTableSource.getCondition());
        } else if (sqlTableSource instanceof SQLSubqueryTableSource) {
            SQLSubqueryTableSource sqlSubqueryTableSource = (SQLSubqueryTableSource) sqlTableSource;
            handSqlSelectQuery(sqlExprTableSources, sqlSubqueryTableSource.getSelect().getQuery());
        } else if (sqlTableSource instanceof SQLWithSubqueryClause) {
            SQLWithSubqueryClause sqlWithSubqueryClause = (SQLWithSubqueryClause) sqlTableSource;
            sqlWithSubqueryClause.getEntries().forEach(entry -> handSqlSelectQuery(sqlExprTableSources, entry.getSubQuery().getQuery()));
        }
    }

    private static void handSqlExpr(List<SQLExprTableSource> sqlExprTableSources, SQLExpr sqlExpr) {
        if (sqlExpr != null) {
            List<SQLObject> children = sqlExpr.getChildren();
            if (CollectionUtils.isNotEmpty(children)) {
                children.forEach(sqlObject -> {
                    if (sqlObject instanceof SQLExpr) {
                        handSqlExpr(sqlExprTableSources, (SQLExpr) sqlObject);
                    }
                });
            }
            if (sqlExpr instanceof SQLQueryExpr) {
                handSqlQueryExpr(sqlExprTableSources, (SQLQueryExpr) sqlExpr);
            }
        }
    }

    private static void handSqlQueryExpr(List<SQLExprTableSource> sqlExprTableSources, SQLQueryExpr sqlQueryExpr) {
        SQLSelect subQuery = sqlQueryExpr.getSubQuery();
        handSqlSelectQuery(sqlExprTableSources, subQuery.getQuery());
    }

}
