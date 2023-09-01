package com.sydata.generate.core.framework.service;

import com.sydata.generate.core.framework.domain.PropertyInfo;
import com.sydata.generate.core.framework.domain.ResultJson;
import com.sydata.generate.core.framework.domain.EnumInfo;
import com.sydata.generate.core.framework.domain.BasisInfo;
import com.sydata.generate.core.framework.utils.FreemarkerUtil;

import java.util.Iterator;
import java.util.List;
/**
 * @author Asus
 * @Description 生成代码Service层
 * @Date 12:40 2023/5/10
 * @Param
 * @return
 **/
public class GeneratorService {

    public static ResultJson createEnum(String url, String ftlUrl,EnumInfo enumInfo) {
        String fileUrl = getGeneratorFileUrl(url, enumInfo.getEnumUrl(), enumInfo.getEnumName(), "enum");
        return FreemarkerUtil.createCustomFile(enumInfo, "enum.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createEntity(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityUrl(), bi.getEntityName(), "entity");
        return FreemarkerUtil.createFile(bi, "entity.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createPageParam(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getPageParamUrl(), bi.getEntityName(), "pageParam");
        return FreemarkerUtil.createFile(bi, "pageParam.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createSelectParam(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getPageParamUrl(), bi.getEntityName(), "selectParam");
        return FreemarkerUtil.createFile(bi, "selectParam.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createVo(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getVoUrl(), bi.getEntityName(), "vo");
        return FreemarkerUtil.createFile(bi, "vo.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createExcelVo(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getVoUrl(), bi.getEntityName(), "excelVo");
        return FreemarkerUtil.createFile(bi, "excelVo.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createTreeVo(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getVoUrl(), bi.getEntityName(), "treeVo");
        return FreemarkerUtil.createFile(bi, "treeVo.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createFileVo(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getVoUrl(), bi.getEntityName(), "fileVo");
        return FreemarkerUtil.createFile(bi, "fileVo.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createSaveParam(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getSaveParamUrl(), bi.getEntityName(), "saveParam");
        return FreemarkerUtil.createFile(bi, "saveParam.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createUpdateParam(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getSaveParamUrl(), bi.getEntityName(), "updateParam");
        return FreemarkerUtil.createFile(bi, "updateParam.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createDataBind(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getDataBindUrl(), bi.getEntityName(), "dataBind");
        return FreemarkerUtil.createFile(bi, "dataBind.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createDao(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getDaoUrl(), bi.getEntityName(), "dao");
        return FreemarkerUtil.createFile(bi, "dao.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createDaoImpl(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getMapperUrl(), bi.getEntityName(), "daoImpl");
        List<PropertyInfo> list = bi.getCis();
        String agile = "";

        PropertyInfo propertyInfo;
        for(Iterator var5 = list.iterator(); var5.hasNext(); agile = agile + propertyInfo.getColumn() + ", ") {
            propertyInfo = (PropertyInfo)var5.next();
        }

        agile = agile.substring(0, agile.length() - 2);
        bi.setAgile(agile);
        return FreemarkerUtil.createFile(bi, "mapper.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createService(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getServiceUrl(), bi.getEntityName(), "service");
        return FreemarkerUtil.createFile(bi, "service.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createServiceImpl(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getServiceImplUrl(), bi.getEntityName(), "serviceImpl");
        return FreemarkerUtil.createFile(bi, "serviceImpl.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createBusService(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getServiceUrl(), bi.getEntityName(), "busService");
        return FreemarkerUtil.createFile(bi, "busService.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createBusServiceImpl(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getServiceImplUrl(), bi.getEntityName(), "busServiceImpl");
        return FreemarkerUtil.createFile(bi, "busServiceImpl.ftl", fileUrl, ftlUrl);
    }

    public static ResultJson createController(String url, String ftlUrl, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getControllerUrl(), bi.getEntityName(), "controller");
        return FreemarkerUtil.createFile(bi, "controller.ftl", fileUrl, ftlUrl);
    }

    public static String getGeneratorFileUrl(String url, String packageUrl, String entityName, String type) {
        if (type.equals("entity")) {
            return url + pageToUrl(packageUrl) + entityName + ".java";
        } else if (type.equals("pageParam")) {
            return url + pageToUrl(packageUrl) + entityName + "PageParam.java";
        } else if (type.equals("selectParam")) {
            return url + pageToUrl(packageUrl) + entityName + "SelectParam.java";
        } else if (type.equals("saveParam")) {
            return url + pageToUrl(packageUrl) + entityName + "SaveParam.java";
        } else if (type.equals("updateParam")) {
            return url + pageToUrl(packageUrl) + entityName + "UpdateParam.java";
        } else if (type.equals("vo")) {
            return url + pageToUrl(packageUrl) + entityName + "Vo.java";
        } else if (type.equals("excelVo")) {
            return url + pageToUrl(packageUrl) + entityName + "ExcelVo.java";
        } else if (type.equals("treeVo")) {
            return url + pageToUrl(packageUrl) + entityName + "TreeVo.java";
        } else if (type.equals("fileVo")) {
            return url + pageToUrl(packageUrl) + "FileVo.java";
        } else if (type.equals("enum")) {
            return url + pageToUrl(packageUrl) + entityName + "Enum.java";
        } else if (type.equals("dataBind")) {
            return url + pageToUrl(packageUrl) + "DataBind" + entityName + ".java";
        } else if (type.equals("dao")) {
            return url + pageToUrl(packageUrl) + entityName + "Mapper.java";
        } else if (type.equals("daoImpl")) {
            return url + pageToUrl(packageUrl) + entityName + "Mapper.xml";
        } else if (type.equals("service")) {
            return url + pageToUrl(packageUrl) + "I" +entityName + "Service.java";
        } else if (type.equals("serviceImpl")) {
            return url + pageToUrl(packageUrl) + entityName + "ServiceImpl.java";
        } else if (type.equals("busService")) {
            return url + pageToUrl(packageUrl) + "IBus" +entityName + "Service.java";
        } else if (type.equals("busServiceImpl")) {
            return url + pageToUrl(packageUrl) + "Bus" +entityName + "ServiceImpl.java";
        }  else if (type.equals("controller")) {
            return url + pageToUrl(packageUrl) + entityName + "Controller.java";
        } else {
            return type.equals("swaggerConfig") ? url + pageToUrl(packageUrl) + entityName + "Config.java" : null;
        }
    }

    public static String pageToUrl(String url) {
        return url.replace(".", "/") + "/";
    }

}
