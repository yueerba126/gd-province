/**
 * @filename: MyGeneratorService.java 2019-10-16
 * @project v0.0.1  V1.0
 * Copyright(c) 2018 BianPeng Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.generate.core.framework.generator;

import com.sydata.generate.core.framework.domain.EnumInfo;
import com.sydata.generate.core.framework.domain.EnumItemInfo;
import com.sydata.generate.core.framework.service.GeneratorService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import jodd.util.StringUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sydata.generate.core.framework.utils.TableUtil.*;
import static com.sydata.generate.core.TableRun.*;

/**
 * Copyright: Copyright (c) 2019
 *
 * <p>说明： 单表带参数生成工具</P>
 * <p>源码地址：https://gitee.com/flying-cattle/mybatis-dsc-generator</P>
 */
public class EnumGenerator {

	private static JdbcTemplate jdbcTemplate;

	static {
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName(DRIVER);
		dataSource.setUrl(DB_URL);
		dataSource.setUsername(DB_NAME);
		dataSource.setPassword(DB_PASSWORD);
		jdbcTemplate=new JdbcTemplate(dataSource);
	}

	public static void main(String[] args) {
		List<EnumInfo> enumInfoList = Lists.newArrayList();
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(ENUM_SQL);
		List<String> dictTypes = maps.stream().map(e->e.get("dict_type").toString()).distinct().collect(Collectors.toList());
		for (int i = 0; i < dictTypes.size(); i++) {
			String dictType = dictTypes.get(i);
			List<Map<String, Object>> items = maps.stream().
					filter(e-> StringUtil.equals(dictType,e.get("dict_type").toString())).collect(Collectors.toList());
			EnumInfo enumInfo = new EnumInfo();
			List<EnumItemInfo> enumItemInfos = Lists.newArrayList();
			enumInfo.setEnumName(StrUtil.upperFirst(StrUtil.toCamelCase(dictType)));
			enumInfo.setEnumComment("");
			for (int j = 0; j < items.size(); j++) {
				EnumItemInfo enumItemInfo = new EnumItemInfo();
				enumItemInfo.setEnumName(items.get(j).get("dict_value").toString());
				enumItemInfo.setEnumValue(items.get(j).get("dict_key").toString());
				enumItemInfos.add(enumItemInfo);
			}
			enumInfo.setEnumItemInfos(enumItemInfos);
			enumInfo.setEnumUrl(ENUM_URL);
			enumInfo.setProject(PROJECT);
			enumInfo.setModule(MODULE);
			enumInfo.setAuthor(AUTHOR);
			enumInfo.setVersion(VERSION);
			enumInfo.setCreateDate(DATE);
			enumInfoList.add(enumInfo);
		}
		for (int i = 0; i < enumInfoList.size(); i++) {
			GeneratorService.createEnum(JAVA_FILE_URL, FTL_FILE_URL, enumInfoList.get(i)).toString();
		}
	}
}
