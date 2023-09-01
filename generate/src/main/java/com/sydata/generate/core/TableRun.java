package com.sydata.generate.core;

import com.sydata.generate.core.framework.domain.ParamInfo;
import com.sydata.generate.core.framework.domain.PropertyInfo;
import com.sydata.generate.core.framework.generator.EnumGenerator;
import com.sydata.generate.core.framework.generator.TableGenerator;
import com.sydata.generate.core.framework.utils.PackageUtil;

import java.util.List;
import java.util.Set;

/**
 * @program: multiple
 * @description:
 * @author: lzq
 * @create: 2023-06-18 00:31
 */
public class TableRun {
    // 基础信息：项目名、作者、版本、是否开启参数初始化
    public static final String AUTHOR = "lzq";
    public static final String VERSION = "V1.0";
    public static final String AGILE = System.currentTimeMillis() + "";
    // 数据库连接信息：连接URL、用户名、秘密、数据库名
    public static final String DB_URL = "jdbc:mysql://192.168.0.36:3306/gd-province-platform?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    public static final String DB_NAME = "root";
    public static final String DB_PASSWORD = "Password-888";
    public static final String DB_DATABASE = "gd-province-platform";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // 上传下载需要填写的依次是fileId,fileName,fileUrl(具体表中的字段自己定义)，没有则不填
    public static final String UD_ADD = "fileId,fileName,fileUrl";
    public static final String TREE_ADD = "parentId";
    // 要生成的枚举（枚举只用改动此处）
    public static final String ENUM_PREFIX = "filing_";
    public static final String ENUM_SQL ="select * from basis_dict where dict_type like concat('"+ENUM_PREFIX+"','%') order by dict_sort;";
    /**
     * @Description 此处开始为常改项
     **/
    // 类信息：类名、对象名（一般是【类名】的首字母小些）、类说明、时间
    public static String PROJECT = null;
    public static String MODULE = null;
    public static String REQUEST_MAPPING_PREFIX = null;
    public static String BASE_URL = null;
    public static String TABLE = null;
    public static String CLASS_COMMENT = null;
    public static String DATE = null;
    // 实体类中要排除的字段
    public static String ENTITY_EXCLUDE = null;
    // 在此处添加，分页页面的查询参数
    public static String PAGE_PARAM_ADD = null;
    // 在此处添加，页面中需要翻译的id，在该字段后会默认添加Name
    public static List<PropertyInfo> VO_ADD_LIST = null;
    // 确定该表的每条数据唯一的联合键
    public static String PRIMARY_KEY_ADD = null;
    // 是否从表 是否有主表id，没有不填
    public static String MAIN_TABLE_ID_ADD = null;
    // 导出Excel要排除的字段
    public static String EXCEL_EXCLUDE = null;

    public static void main(String[] args) {
        String packageName = "com.sydata.generate.core.table.filing"; // 把这个包名下的所有信息实例化
        Set<String> tableSet = PackageUtil.getClassName(packageName,true);
        for (int i = 0; i < tableSet.toArray().length; i++) {
            String item = (String) tableSet.toArray()[i];
            new ParamInfo(item);
            TableGenerator.main(args);
            if(i==0){
                EnumGenerator.main(args); // 枚举只生成一次
            }
        }
    }
}
