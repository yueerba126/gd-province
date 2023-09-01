package com.sydata.generate.core.framework.generator;

import com.sydata.generate.core.framework.domain.BasisInfo;
import com.sydata.generate.core.framework.service.GeneratorService;
import com.sydata.generate.core.framework.utils.BusUtil;

import java.sql.SQLException;

import static com.sydata.generate.core.BusRun.*;
import static com.sydata.generate.core.framework.utils.BusUtil.FTL_FILE_URL;
import static com.sydata.generate.core.framework.utils.BusUtil.JAVA_FILE_URL;

/**
 * Copyright: Copyright (c) 2019
 *
 * <p>说明： 业务代码生成工具</P>
 * <p>源码地址：https://gitee.com/flying-cattle/mybatis-dsc-generator</P>
 */
public class BusGenerator {
	public static void main(String[] args) {
        try {
            BasisInfo basisInfo = BusUtil.generateBasisInfo();
            basisInfo.setMethodInfoList(METHOD_INFO_LIST);
            basisInfo.setEntityName(ENTITY_NAME);
            basisInfo.setEntityComment(ENTITY_COMMENT);
            GeneratorService.createSelectParam(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
            GeneratorService.createVo(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
            GeneratorService.createDao(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
            GeneratorService.createDaoImpl(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
            GeneratorService.createService(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
            GeneratorService.createServiceImpl(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
            GeneratorService.createController(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
