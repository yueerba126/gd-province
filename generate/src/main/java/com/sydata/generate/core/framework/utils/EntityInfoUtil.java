package com.sydata.generate.core.framework.utils;

import com.sydata.generate.core.framework.domain.PropertyInfo;
import com.sydata.generate.core.framework.domain.BasisInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: multiple
 * @description: 数据库连接工具
 * @author: lzq
 * @create: 2023-06-17 22:53
 */
public class EntityInfoUtil {
    public EntityInfoUtil() {
    }

    public static BasisInfo getInfo(BasisInfo bi) throws SQLException {
        List<PropertyInfo> columns = new ArrayList();
        Connection con = null;
        PreparedStatement pstemt = null;
        ResultSet rs = null;
        String sql = "select ordinal_position id,column_name,data_type,replace(replace(replace(column_type,data_type,''),'(',''),')','') column_accuracy,is_nullable,column_comment from information_schema.columns where table_schema='" + bi.getDatabase() + "' and table_name='" + bi.getTable() + "'";

        BasisInfo var26;
        try {
            con = DriverManager.getConnection(bi.getDbUrl(), bi.getDbName(), bi.getDbPassword());
            pstemt = con.prepareStatement(sql);
            rs = pstemt.executeQuery();

            while(rs.next()) {
                String id = rs.getString(1);
                String column = rs.getString(2);
                String jdbcType = rs.getString(3);
                String columnAccuracy = rs.getString(4);
                String isNullable = rs.getString(5);
                String comment  = rs.getString(6);
                PropertyInfo ci = new PropertyInfo();
                ci.setColumn(column);
                if (jdbcType.equalsIgnoreCase("int")) {
                    ci.setJdbcType("Integer");
                } else if (jdbcType.equalsIgnoreCase("datetime")) {
                    ci.setJdbcType("timestamp");
                } else {
                    ci.setJdbcType(jdbcType);
                }

                ci.setId(Integer.valueOf(id));
                ci.setColumnAccuracy(columnAccuracy);
                ci.setIsNullable(isNullable);
                ci.setComment(comment);
                ci.setProperty(MySqlToJavaUtil.changeToJavaFiled(column));
                ci.setJavaType(MySqlToJavaUtil.jdbcTypeToJavaType(jdbcType));
                if (column.equalsIgnoreCase("id")) {
                    bi.setIdType(ci.getJavaType());
                    bi.setIdJdbcType(ci.getJdbcType());
                }

                columns.add(ci);
                Set<String> pkgs = bi.getPkgs();
                pkgs.add(MySqlToJavaUtil.jdbcTypeToJavaTypePck(jdbcType));
                bi.setPkgs(pkgs);
            }

            bi.setCis(columns);
            rs.close();
            pstemt.close();
            con.close();
            if (null == columns || columns.size() == 0) {
                throw new RuntimeException("未能读取到表或表中的字段。请检查链接url，数据库账户，数据库密码，查询的数据名、是否正确。");
            }

            var26 = bi;
        } catch (Exception var24) {
            var24.printStackTrace();
            throw new RuntimeException("自动生成实体类错误：" + var24.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException var23) {
            }

            try {
                if (pstemt != null) {
                    pstemt.close();
                }
            } catch (SQLException var22) {
            }

            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException var21) {
                var21.printStackTrace();
            }

        }

        return var26;
    }
}
