/**
 * @filename:GradedEnterpriseFlowService 2023年05月24日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.service;

import com.sydata.grade.domain.GradedEnterpriseFlow;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.grade.domain.GradedEnterpriseFlow;
import com.sydata.grade.param.GradedEnterpriseFlowInitParam;
import com.sydata.grade.param.GradedEnterpriseFlowSaveParam;
import com.sydata.grade.param.GradedEnterpriseFlowPageParam;
import com.sydata.grade.param.GradedEnterpriseSelfAssessmentSaveParam;
import com.sydata.grade.vo.GradedEnterpriseFlowVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;
/**   
 * @Description:TODO(等级粮库评定管理-企业申报流程表服务层)
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface IGradedEnterpriseFlowService extends IService<GradedEnterpriseFlow> {

      /**
      * 分页列表
      *
      * @param pageParam 分页参数
      * @return 分页列表
      */
      Page<GradedEnterpriseFlowVo> pages(GradedEnterpriseFlowPageParam pageParam);

      /**
       * 列表
       *
       * @param qyid 参数
       * @return 列表
       */
      List<GradedEnterpriseFlowVo> list(String qyid);
      /**
       * 列表
       *
       * @param qyid 参数
       * @return 列表
       */
      List<GradedEnterpriseFlowSaveParam> listByQyid(String qyid);
      /**
       * 列表
       *
       * @param qyid 参数
       * @return 列表
       */
      List<GradedEnterpriseFlowSaveParam> pickNode1(String qyid);
      /**
       * 列表
       *
       * @param qyid 参数
       * @return 列表
       */
      List<GradedEnterpriseFlowSaveParam> pickNode2(String qyid);
      /**
       * 验证
       *
       * @param qyid 参数
       * @return 列表
       */
      List<GradedEnterpriseFlowVo> verifyNotPass(String qyid);
      /**
      * 详情
      *
      * @param id 主键ID
      * @return 详情
      */
      GradedEnterpriseFlowVo detail(String id);

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
      String saveData(GradedEnterpriseFlowSaveParam param);
      /**
       * 初始化
       *
       * @param param 参数
       * @return 主键ID
       */
      Boolean initData(GradedEnterpriseFlowInitParam param);

      /**
       * 批量新增或修改
       *
       * @param params 参数
       */
      Boolean saveOrUpdateBatchData(List<GradedEnterpriseFlowSaveParam> params);

      /**
       * 获取初始化流程信息
       *
       * @param param 参数
       * @return 主键ID
       */
      List<GradedEnterpriseFlow> getInitData(GradedEnterpriseFlowInitParam param);
      /**
      * 修改
      *
      * @param param 参数
      * @return 主键ID
      */
      String updateData(GradedEnterpriseFlowSaveParam param);

      /**
      * 删除
      *
      * @param ids 参数
      * @return 主键ID
      */
      Boolean removeData(List<String> ids);

}