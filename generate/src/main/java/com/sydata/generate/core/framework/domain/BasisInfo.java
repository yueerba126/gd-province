package com.sydata.generate.core.framework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: multiple
 * @description:
 * @author: lzq
 * @create: 2023-06-17 23:07
 */
@Data
@ToString
@NoArgsConstructor
public class BasisInfo implements Serializable {
    private String project;
    private String module;
    private String requestMappingPrefix;
    private String author;
    private String version;
    private String dbUrl;
    private String dbName;
    private String dbPassword;
    private String database;
    private String table;
    private String entityName;
    private String objectName;
    private String entityComment;
    private String createDate;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String agile;
    private String entityUrl;
    private String pageParamUrl;
    private String saveParamUrl;
    private String voUrl;
    private String enumUrl;
    private String dataBindUrl;
    private String daoUrl;
    private String mapperUrl;
    private String serviceUrl;
    private String serviceImplUrl;
    private String abstractControllerUrl;
    private String controllerUrl;
    private String swaggerConfigUrl;
    private String idType;
    private String idJdbcType;
    private List<PropertyInfo> cis;
    private String isSwagger = "true";
    private Set<String> pkgs = new HashSet();
    // 添加的
    private List<PropertyInfo> pageParamInfoList;
    private List<PropertyInfo> voInfoList;
    private List<MethodInfo> methodInfoList;
    private List<PropertyInfo> primaryKeyInfoList;
    private List<PropertyInfo> mainTableIdList;
    private List<PropertyInfo> upDownList;
    private List<PropertyInfo> entityExcludeList;
    private List<PropertyInfo> entityExcludeAfterList;
    private List<PropertyInfo> excelVoExcludeAfterList;
    private List<PropertyInfo> treeList;
}
