package com.sydata.dostrict.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.personnel.domain.ApparitorTitle;
import com.sydata.dostrict.personnel.param.ApparitorTitlePageParam;
import com.sydata.dostrict.personnel.vo.ApparitorTitleVo;

import java.util.List;

/**
 * 行政管理-荣誉称号管理Service接口
 *
 * @author fuql
 * @date 2023-04-25
 */
public interface IApparitorTitleService extends IService<ApparitorTitle> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorTitleVo> pages(ApparitorTitlePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 企业人员信息VO
     */
    ApparitorTitleVo detail(String id);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorTitle param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorTitle param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);

}
