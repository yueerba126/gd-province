package com.sydata.generate.core.framework.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.sydata.generate.core.framework.domain.BasisInfo;
import com.sydata.generate.core.framework.domain.PropertyInfo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.sydata.generate.core.TableRun.*;

/**
 * @program: multiple
 * @description:
 * @author: lzq
 * @create: 2023-06-18 02:00
 */
public class DataPackagingUtil {
    public static void fillBasisInfo(BasisInfo basisInfo) {
        List<PropertyInfo> entityExcludeList = basisInfo.getCis().stream().filter(e-> Arrays.asList(ENTITY_EXCLUDE.split(",")).contains(e.getProperty())).collect(Collectors.toList());
        List<PropertyInfo> entityExcludeAfterList = basisInfo.getCis().stream().filter(e->!Arrays.asList(ENTITY_EXCLUDE.split(",")).contains(e.getProperty())).collect(Collectors.toList());
        List<PropertyInfo> pageParamInfoList = basisInfo.getCis().stream().filter(e->Arrays.asList(PAGE_PARAM_ADD.split(",")).contains(e.getProperty())).collect(Collectors.toList());
        fillParamSelectType(pageParamInfoList);
        List<PropertyInfo> voInfoList = DataPackagingUtil.getVoInfoList(basisInfo);
        List<PropertyInfo> primaryKeyInfoList = basisInfo.getCis().stream().filter(e->Arrays.asList(PRIMARY_KEY_ADD.split(",")).contains(e.getProperty())).collect(Collectors.toList());
        List<PropertyInfo> mainTableIdList = basisInfo.getCis().stream().filter(e->Arrays.asList(MAIN_TABLE_ID_ADD.split(",")).contains(e.getProperty())).collect(Collectors.toList());
        List<PropertyInfo> excelVoExcludeAfterList = basisInfo.getCis().stream().filter(e->!Arrays.asList(EXCEL_EXCLUDE.split(",")).contains(e.getProperty())).collect(Collectors.toList());
        List<PropertyInfo> upDownList = basisInfo.getCis().stream().filter(e->Arrays.asList(UD_ADD.split(",")).contains(e.getProperty())).collect(Collectors.toList());
        List<PropertyInfo> treeList = basisInfo.getCis().stream().filter(e->Arrays.asList(TREE_ADD.split(",")).contains(e.getProperty())).collect(Collectors.toList());

        basisInfo.setEntityExcludeList(entityExcludeList);
        basisInfo.setEntityExcludeAfterList(entityExcludeAfterList);
        basisInfo.setPageParamInfoList(pageParamInfoList);
        basisInfo.setVoInfoList(voInfoList);
        basisInfo.setPrimaryKeyInfoList(primaryKeyInfoList);
        basisInfo.setMainTableIdList(mainTableIdList);
        basisInfo.setExcelVoExcludeAfterList(excelVoExcludeAfterList);
        basisInfo.setUpDownList(upDownList);
        basisInfo.setTreeList(treeList);
    }

    private static void fillParamSelectType(List<PropertyInfo> pageParamInfoList) {
        for (int i = 0; i < pageParamInfoList.size(); i++) {
            PropertyInfo propertyInfo = pageParamInfoList.get(i);
            if(propertyInfo.getComment().contains("名称")){
                propertyInfo.setParamSelectType("like");
            }if(propertyInfo.getComment().contains("姓名")){
                propertyInfo.setParamSelectType("like");
            }else{
                propertyInfo.setParamSelectType("eq");
            }

        }
    }

    public static List<PropertyInfo> getVoInfoList(BasisInfo basisInfo) {
        List<PropertyInfo> voInfoList = Lists.newArrayList();
        if(CollectionUtil.isNotEmpty(VO_ADD_LIST)){
            List<String> voList = VO_ADD_LIST.stream().map(e->e.getProperty()).collect(Collectors.toList());
            voInfoList = basisInfo.getCis().stream().filter(e->voList.contains(e.getProperty())).collect(Collectors.toList());
            for (int i = 0; i < VO_ADD_LIST.size(); i++) {
                PropertyInfo voAddItem = VO_ADD_LIST.get(i);
                for (int j = 0; j < voInfoList.size(); j++) {
                    PropertyInfo voInfo = voInfoList.get(j);
                    if(StringUtils.equals(voAddItem.getProperty(),voInfo.getProperty())){
                        voInfo.setBindAnnotation(voAddItem.getBindAnnotation());
                        voInfo.setBindSourceFieldCombination(voAddItem.getBindSourceFieldCombination());
                    }
                }
            }
        }
        return voInfoList;
    }
}
