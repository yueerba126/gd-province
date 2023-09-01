package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.CheckGroupPersonnel;
import com.sydata.safe.asess.param.CheckGroupPersonnelSaveParam;

import java.util.List;

/**
 * 实地抽查人员Service接口
 *
 * @author system
 * @date 2023-02-13
 */
public interface ICheckGroupPersonnelService extends IService<CheckGroupPersonnel> {

    /**
     * 根据小组查询成员
     *
     * @param checkGroupId 检查小组ID
     * @return 实地抽查人员列表
     */
    List<CheckGroupPersonnel> listByCheckGroupId(String checkGroupId);

    /**
     * 设置小组成员
     *
     * @param checkGroupId       检查小组ID
     * @param groupPersonnelList 抽查小组人员新增参数
     * @return 删除结果
     */
    boolean setUpByCheckGroupId(String checkGroupId, List<CheckGroupPersonnelSaveParam> groupPersonnelList);

    /**
     * 根据小组删除成员
     *
     * @param checkGroupId 检查小组ID
     * @return 删除结果
     */
    boolean removeByCheckGroupId(String checkGroupId);
}