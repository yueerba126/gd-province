package com.sydata.reserve.scale.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.reserve.scale.domain.PlanReserveScale;
import com.sydata.reserve.scale.param.PlanReserveScaleExportParam;
import com.sydata.reserve.scale.param.PlanReserveScaleLogPageParam;
import com.sydata.reserve.scale.param.PlanReserveScalePageParam;
import com.sydata.reserve.scale.vo.PlanReserveScaleLogVo;
import com.sydata.reserve.scale.vo.PlanReserveScalePageVo;
import com.sydata.reserve.scale.vo.PlanReserveScaleVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 储备计划管理-储备规模
 *
 * @author fuql
 * @date 2023-05-09
 */
public interface IPlanReserveScaleService extends IService<PlanReserveScale> {

    /**
     * 储备计划管理-储备规模新增
     *
     * @param param param
     * @return 储备规模id
     */
    String saveData(PlanReserveScale param);

    /**
     * 储备计划管理-储备规模page列表
     *
     * @param param param
     * @return 储备规模page列表
     */
    PlanReserveScalePageVo pageData(PlanReserveScalePageParam param);

    /**
     * 储备计划管理-储备规模修改
     *
     * @param param param
     * @return 储备规模id
     */
    String updateData(PlanReserveScale param);

    /**
     * 储备计划管理-储备规模删除
     *
     * @param ids param
     * @return 储备规模id
     */
    Boolean removeData(List<String> ids);

    /**
     * 获取储备计划管理-储备规模详情
     *
     * @param id id
     * @return 储备规模
     */
    PlanReserveScaleVo getDataById(Long id);

    /**
     * 导入模板下载
     */
    void templateExport();

    /**
     * 导入
     *
     * @param file 附件
     * @param isUpdate 校验是否直接覆盖新增
     * @return 操作状态
     */
    List<PlanReserveScaleVo> imports(MultipartFile file , Boolean isUpdate);

    /**
     * 导出
     *
     * @param param 参数
     */
    void export(PlanReserveScalePageParam param);

    /**
     * 获取调整日志列表
     *
     * @param param param
     * @return 调整日志列表
     */
    Page<PlanReserveScaleLogVo> getLogInfo(PlanReserveScaleLogPageParam param);
}
