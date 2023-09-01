/**
 * @filename:GradedExpertManageService 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.service;

import com.sydata.grade.domain.GradedExpertManage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.grade.domain.GradedExpertManage;
import com.sydata.grade.param.FileStorageUploadParam;
import com.sydata.grade.param.GradedExpertManageExportParam;
import com.sydata.grade.param.GradedExpertManageSaveParam;
import com.sydata.grade.param.GradedExpertManagePageParam;
import com.sydata.grade.vo.GradedExpertManageVo;
import com.sydata.framework.databind.domain.DataBindQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
/**   
 * @Description:TODO(等级粮库评定管理-专家管理服务层)
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface IGradedExpertManageService extends IService<GradedExpertManage> {

      /**
      * 分页列表
      *
      * @param pageParam 分页参数
      * @return 分页列表
      */
      Page<GradedExpertManageVo> pages(GradedExpertManagePageParam pageParam);

      /**
       * 分页列表合并
       *
       * @param pageParam 分页参数
       * @return 分页列表
       */
      Page<GradedExpertManageVo> pagesMerge(GradedExpertManagePageParam pageParam);

      /**
      * 详情
      *
      * @param id 主键ID
      * @return 详情
      */
      GradedExpertManageVo detail(String id);

      /**
      * 数据绑定
      *
      * @param dataBindQuerys 数据查询列表
      */
      void dataBind(Collection<DataBindQuery> dataBindQuerys);

      /**
      * 新增
      *
      * @param param 参数
      * @return 主键ID
      */
      String saveData(GradedExpertManageSaveParam param);

      /**
      * 修改
      *
      * @param param 参数
      * @return 主键ID
      */
      String updateData(GradedExpertManageSaveParam param);

      /**
      * 删除
      *
      * @param ids 参数
      * @return 主键ID
      */
      Boolean removeData(List<String> ids);

      /**
       * 导出
       *
       * @param pageParam 参数
       */
      void exportData(GradedExpertManagePageParam pageParam);

      /**
       * 导入
       *
       * @param file,isUpdate 参数
       */
      void importData(MultipartFile file);

      /**
       * 唯一性
       *
       * @param expertName,pdnx 参数
       * @return 主键ID
       */
      GradedExpertManage getByUnx(String expertName, String pdnx);

      /**
       * 唯一性更新
       *
       * @param saveParam 参数
       * @return 主键ID
       */
      Boolean updateOrSaveByUnx(GradedExpertManageSaveParam saveParam);
}