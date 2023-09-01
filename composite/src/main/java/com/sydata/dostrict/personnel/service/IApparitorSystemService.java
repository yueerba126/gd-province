package com.sydata.dostrict.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.personnel.domain.ApparitorSystem;
import com.sydata.dostrict.personnel.param.ApparitorSystemPageParam;
import com.sydata.dostrict.personnel.param.ApparitorSystemSaveParam;
import com.sydata.dostrict.personnel.vo.ApparitorSystemVo;

import java.util.List;

/**
 * 行政管理-人员制度管理Service接口
 *
 * @author fuql
 * @date 2023-04-24
 */
public interface IApparitorSystemService extends IService<ApparitorSystem> {

    /**
     * 行政管理-人员制度管理新增
     *
     * @param param param
     * @return 人员制度管理id
     */
    String saveData(ApparitorSystemSaveParam param);

    /**
     * 行政管理-人员制度管理page列表
     *
     * @param param param
     * @return 人员制度管理page列表
     */
    Page<ApparitorSystemVo> pageData(ApparitorSystemPageParam param);

    /**
     * 行政管理-人员制度管理修改
     *
     * @param param param
     * @return 人员制度管理id
     */
    String updateData(ApparitorSystemSaveParam param);

    /**
     * 行政管理-人员制度管理删除
     *
     * @param ids param
     * @return 人员制度管理id
     */
    Boolean removeData(List<String> ids);

    /**
     * 获取行政管理-人员制度管理详情
     *
     * @param id id
     * @return 人员制度管理
     */
    ApparitorSystemVo getDataById(Long id);

    /**
     * 发布行政管理-人员制度管理
     *
     * @param id id
     * @return 人员制度管理
     */
    boolean pushDataById(Long id);


    /**
     * 下载附件
     *
     * @param fileId fileId
     */
    void download(String fileId);
}
