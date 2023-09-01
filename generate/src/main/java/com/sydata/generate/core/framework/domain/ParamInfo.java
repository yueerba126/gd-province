package com.sydata.generate.core.framework.domain;

import com.sydata.generate.core.TableRun;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.List;

/**
 * @program: multiple
 * @description:
 * @author: lzq
 * @create: 2023-06-19 10:47
 */
@Data
@ToString
@NoArgsConstructor
public class ParamInfo {
    public String PROJECT;
    public String MODULE;
    public String REQUEST_MAPPING_PREFIX;
    public String BASE_URL;
    public String TABLE;
    public String CLASS_COMMENT;
    public String DATE;
    // 实体类中要排除的字段
    public String ENTITY_EXCLUDE;
    // 在此处添加，分页页面的查询参数
    public String PAGE_PARAM_ADD;
    // 在此处添加，页面中需要翻译的id，在该字段后会默认添加Name
    public List<PropertyInfo> VO_ADD_LIST;
    // 确定该表的每条数据唯一的联合键
    public String PRIMARY_KEY_ADD;
    // 是否从表 是否有主表id，没有不填
    public String MAIN_TABLE_ID_ADD;
    // 导出Excel要排除的字段
    public String EXCEL_EXCLUDE;

    @SneakyThrows
    public ParamInfo(String className){
        Class clazz = Class.forName(className);
        this.PROJECT = (String) clazz.getDeclaredField("PROJECT").get(null);
        this.MODULE = (String) clazz.getDeclaredField("MODULE").get(null);
        this.REQUEST_MAPPING_PREFIX = (String) clazz.getDeclaredField("REQUEST_MAPPING_PREFIX").get(null);
        this.BASE_URL = (String) clazz.getDeclaredField("BASE_URL").get(null);
        this.TABLE = (String) clazz.getDeclaredField("TABLE").get(null);
        this.CLASS_COMMENT = (String) clazz.getDeclaredField("CLASS_COMMENT").get(null);
        this.DATE = (String) clazz.getDeclaredField("DATE").get(null);
        this.ENTITY_EXCLUDE = (String) clazz.getDeclaredField("ENTITY_EXCLUDE").get(null);
        this.PAGE_PARAM_ADD = (String) clazz.getDeclaredField("PAGE_PARAM_ADD").get(null);
        this.VO_ADD_LIST = (List<PropertyInfo>) clazz.getDeclaredField("VO_ADD_LIST").get(null);
        this.PRIMARY_KEY_ADD = (String) clazz.getDeclaredField("PRIMARY_KEY_ADD").get(null);
        this.MAIN_TABLE_ID_ADD = (String) clazz.getDeclaredField("MAIN_TABLE_ID_ADD").get(null);
        this.EXCEL_EXCLUDE = (String) clazz.getDeclaredField("EXCEL_EXCLUDE").get(null);

        TableRun.PROJECT = (String) clazz.getDeclaredField("PROJECT").get(null);
        TableRun.MODULE = (String) clazz.getDeclaredField("MODULE").get(null);
        TableRun.REQUEST_MAPPING_PREFIX = (String) clazz.getDeclaredField("REQUEST_MAPPING_PREFIX").get(null);
        TableRun.BASE_URL = (String) clazz.getDeclaredField("BASE_URL").get(null);
        TableRun.TABLE = (String) clazz.getDeclaredField("TABLE").get(null);
        TableRun.CLASS_COMMENT = (String) clazz.getDeclaredField("CLASS_COMMENT").get(null);
        TableRun.DATE = (String) clazz.getDeclaredField("DATE").get(null);
        TableRun.ENTITY_EXCLUDE = (String) clazz.getDeclaredField("ENTITY_EXCLUDE").get(null);
        TableRun.PAGE_PARAM_ADD = (String) clazz.getDeclaredField("PAGE_PARAM_ADD").get(null);
        TableRun.VO_ADD_LIST = (List<PropertyInfo>) clazz.getDeclaredField("VO_ADD_LIST").get(null);
        TableRun.PRIMARY_KEY_ADD = (String) clazz.getDeclaredField("PRIMARY_KEY_ADD").get(null);
        TableRun.MAIN_TABLE_ID_ADD = (String) clazz.getDeclaredField("MAIN_TABLE_ID_ADD").get(null);
        TableRun.EXCEL_EXCLUDE = (String) clazz.getDeclaredField("EXCEL_EXCLUDE").get(null);
    }
}
