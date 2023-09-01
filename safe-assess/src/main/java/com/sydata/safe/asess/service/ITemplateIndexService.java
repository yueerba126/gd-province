package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.TemplateIndex;
import com.sydata.safe.asess.param.IndexRemoveParam;
import com.sydata.safe.asess.vo.TemplateIndexTreeVo;

import java.util.List;

/**
 * 粮食安全考核-考核模板指标Service接口
 *
 * @author system
 * @date 2023-02-13
 */
public interface ITemplateIndexService extends IService<TemplateIndex> {

    /**
     * 列表
     *
     * @param templateId 考核模板ID
     * @return 列表
     */
    List<TemplateIndex> listByTemplateId(String templateId);

    /**
     * 树形列表
     *
     * @param templateId 考核模板ID
     * @return 树形列表
     */
    List<TemplateIndexTreeVo> treeByTemplateId(String templateId);

    /**
     * 根据模板删除指标
     *
     * @param templateId 考核模板ID
     * @return 删除结果
     */
    Boolean removeByTemplateId(String templateId);

    /**
     * 删除
     *
     * @param removeParam 考核指标删除参数
     * @return 删除结果
     */
    Boolean remove(IndexRemoveParam removeParam);

}