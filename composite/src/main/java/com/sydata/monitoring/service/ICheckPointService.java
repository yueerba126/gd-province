package com.sydata.monitoring.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.dto.CheckPointAddDto;
import com.sydata.monitoring.dto.CheckPointConfigDto;
import com.sydata.monitoring.dto.CheckPointQueryDto;
import com.sydata.monitoring.dto.CheckPointRemoveDto;
import com.sydata.monitoring.entity.CheckPoint;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.monitoring.vo.CheckPointConfigVo;
import com.sydata.monitoring.vo.CheckPointVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 流通检测-监测点配置表 服务类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
public interface ICheckPointService extends IService<CheckPoint> {

    /**
     * 分页查询
     *
     * @param queryDto 查询参数
     * @return 结果
     */
    Page<CheckPointVo> pagination(CheckPointQueryDto queryDto);

    /**
     * 新增
     *
     * @param addDto 新增参数
     * @return 结果
     */
    Boolean add(CheckPointAddDto addDto);

    /**
     * 配置
     *
     * @param configDto 配置参数
     * @return 结果
     */
    Boolean config(CheckPointConfigDto configDto);

    /**
     * 删除
     *
     * @param removeDto 参数
     * @return 结果
     */
    Boolean remove(CheckPointRemoveDto removeDto);

    /**
     * 当前登陆人分配的监测点
     *
     * @return 结果
     */
    CheckPointVo currentPoint();

    /**
     * 检查点配置详情
     *
     * @param configDto 查询参数
     * @return 结果
     */
    CheckPointConfigVo configDetail(CheckPointConfigDto configDto);

    /**
     * 根据监测点ID列表查询
     *
     * @param pointIds 监测点ID列表
     * @return 结果
     */
    List<CheckPointVo> selectByIds(Set<String> pointIds);
}
