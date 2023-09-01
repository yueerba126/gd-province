package com.sydata.generate.core.framework.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.sydata.generate.core.BusRun;
import com.sydata.generate.core.framework.domain.BasisInfo;
import com.sydata.generate.core.framework.domain.MethodInfo;
import com.sydata.generate.core.framework.domain.PropertyInfo;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.sydata.generate.core.BusRun.*;

/**
 * @program: multiple
 * @description:
 * @author: lzq
 * @create: 2023-06-18 00:26
 */
public class BusUtil {

    /**
     * 路径信息，分开路径方便聚合工程项目，微服务项目(此处无需修改)
     **/
    public static final String ENTITY_URL = BASE_URL+"domain";
    public static final String PAGE_PARAM_URL = BASE_URL+"param";
    public static final String SAVE_PARAM_URL = BASE_URL+"param";
    public static final String VO_URL = BASE_URL+"vo";
    public static final String DATA_BIND_URL = BASE_URL+"annotation";
    public static final String ENUM_URL = BASE_URL+"enums";
    public static final String DAO_URL = BASE_URL+"mapper";
    public static final String XML_URL = BASE_URL+"mapper.xml";
    public static final String SERVICE_URL = BASE_URL+"service";
    public static final String SERVICE_IMPL_URL = BASE_URL+"service.impl";
    public static final String CONTROLLER_URL = BASE_URL+"controller";
    /**
     * 文件路径
     **/
    public static final String SRC_MAIN_JAVA_PATH = "/src/main/java/";
    public static final String SRC_MAIN_RESOURCES_PATH = "/src/main/resources/templates/bus/";
    public static final String SRC_MODEL_PATH = "/"+MODULE;
    public static final String JAVA_FILE_URL = System.getProperty("user.dir")+SRC_MODEL_PATH+SRC_MAIN_JAVA_PATH;
    public static final String FTL_FILE_URL = System.getProperty("user.dir")+"/generate"+SRC_MAIN_RESOURCES_PATH;


    public static BasisInfo generateBasisInfo() throws SQLException {
        BasisInfo basisInfo = new BasisInfo();
        basisInfo.setProject(PROJECT);
        basisInfo.setModule(MODULE);
        basisInfo.setAuthor(AUTHOR);
        basisInfo.setVersion(VERSION);
        basisInfo.setRequestMappingPrefix(REQUEST_MAPPING_PREFIX);
        basisInfo.setCreateDate(DATE);
        basisInfo.setStartDate(DateUtil.format(new Date(),"yyyy-MM-dd"));
        basisInfo.setEndDate(DateUtil.format(DateUtil.nextWeek(),"yyyy-MM-dd"));
        basisInfo.setStartTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        basisInfo.setEndTime(DateUtil.format(DateUtil.nextWeek(),"yyyy-MM-dd HH:mm:ss"));
        basisInfo.setAgile(AGILE);
        basisInfo.setDbUrl(DB_URL);
        basisInfo.setDbName(DB_NAME);
        basisInfo.setDbPassword(DB_PASSWORD);
        basisInfo.setDatabase(DB_DATABASE);
        basisInfo.setEntityUrl(ENTITY_URL);
        basisInfo.setPageParamUrl(PAGE_PARAM_URL);
        basisInfo.setSaveParamUrl(SAVE_PARAM_URL);
        basisInfo.setVoUrl(VO_URL);
        basisInfo.setDataBindUrl(DATA_BIND_URL);
        basisInfo.setEnumUrl(ENUM_URL);
        basisInfo.setDaoUrl(DAO_URL);
        basisInfo.setMapperUrl(XML_URL);
        basisInfo.setServiceUrl(SERVICE_URL);
        basisInfo.setServiceImplUrl(SERVICE_IMPL_URL);
        basisInfo.setControllerUrl(CONTROLLER_URL);

        basisInfo.setTable(TABLE);
        basisInfo.setEntityName(MySqlToJavaUtil.getClassName(TABLE));
        basisInfo.setObjectName(MySqlToJavaUtil.changeToJavaFiled(TABLE));
        basisInfo.setEntityComment(CLASS_COMMENT);
        basisInfo = EntityInfoUtil.getInfo(basisInfo);
        return basisInfo;
    }

    private static void setPropertyInfoList(List<PropertyInfo> infoList, BasisInfo basisInfo,String method) {
        try {
            Class clazz = Class.forName("com.sydata.generate.core.framework.domain.BasisInfo");
            Method businessMethod = clazz.getMethod("set"+ StrUtil.upperFirst(method), String.class);
            businessMethod.invoke(basisInfo, infoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setMethodInfoList(List<MethodInfo> infoList, BasisInfo basisInfo,String method) {
        try {
            Class clazz = Class.forName("com.sydata.generate.core.framework.domain.BasisInfo");
            Method businessMethod = clazz.getMethod("set"+ StrUtil.upperFirst(method), String.class);
            businessMethod.invoke(basisInfo, infoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
