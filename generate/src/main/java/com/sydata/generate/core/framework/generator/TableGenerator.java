package com.sydata.generate.core.framework.generator;

import cn.hutool.core.collection.CollectionUtil;
import com.sydata.generate.core.framework.domain.BasisInfo;
import com.sydata.generate.core.framework.service.GeneratorService;
import com.sydata.generate.core.framework.utils.TableUtil;
import com.sydata.generate.core.framework.utils.DataPackagingUtil;

import java.sql.SQLException;

import static com.sydata.generate.core.framework.utils.TableUtil.FTL_FILE_URL;
import static com.sydata.generate.core.framework.utils.TableUtil.JAVA_FILE_URL;
/**
 * <p>说明： 按表生成对应的代码（主从表是分开生成的与MAIN_TABLE_ID_ADD有关）</P>
 */
public class TableGenerator {
	public static void main(String[] args) {
		try {
			BasisInfo basisInfo = TableUtil.generateBasisInfo();
			DataPackagingUtil.fillBasisInfo(basisInfo);
			GeneratorService.createEntity(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createPageParam(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createSaveParam(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createVo(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createExcelVo(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			if(CollectionUtil.isNotEmpty(basisInfo.getTreeList())){
				GeneratorService.createTreeVo(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			}
			GeneratorService.createFileVo(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createDataBind(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createDao(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createDaoImpl(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createService(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createServiceImpl(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createBusService(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createBusServiceImpl(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
			GeneratorService.createController(JAVA_FILE_URL, FTL_FILE_URL, basisInfo).toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
