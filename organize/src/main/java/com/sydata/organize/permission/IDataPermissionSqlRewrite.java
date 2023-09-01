package com.sydata.organize.permission;

import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 * @author pangbohuan
 * @describe 数据权限SQL重写器
 * @date 2022/10/21 20:02
 */
public interface IDataPermissionSqlRewrite {

    /**
     * 是否重写该SQL
     *
     * @param table 表名
     * @return 是与否
     */
    boolean isProcess(String table);

    /**
     * 处理
     *
     * @param plainSelect 详细查询SQL
     */
    void processSelect(PlainSelect plainSelect);
}
