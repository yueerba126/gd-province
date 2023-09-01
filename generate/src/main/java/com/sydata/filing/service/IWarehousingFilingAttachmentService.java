package com.sydata.filing.service;

import com.sydata.filing.domain.WarehousingFilingAttachment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import com.sydata.filing.domain.WarehousingFilingAttachment;
import com.sydata.filing.param.WarehousingFilingAttachmentSaveParam;
import com.sydata.filing.param.WarehousingFilingAttachmentPageParam;
import com.sydata.filing.vo.WarehousingFilingAttachmentVo;
import com.sydata.filing.vo.FileVo;
import com.sydata.framework.databind.domain.DataBindQuery;
import java.util.Collection;
import java.util.List;

/**   
 * @Description:TODO(仓储备案-仓储附件服务层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface IWarehousingFilingAttachmentService extends IService<WarehousingFilingAttachment> {

    /**
    * 分页列表
    *
    * @param pageParam 分页参数
    * @return 分页列表
    */
    Page<WarehousingFilingAttachmentVo> pages(WarehousingFilingAttachmentPageParam pageParam);

    /**
    * 详情
    *
    * @param id 主键ID
    * @return 详情
    */
    WarehousingFilingAttachmentVo detail(String id);

    /**
    * 详情列表
    *
    * @param ids 主键ID集合
    * @return 详情
    */
    List<WarehousingFilingAttachmentVo> detailList(List<String> ids);

    /**
    * 新增
    *
    * @param param 参数
    * @return 主键ID
    */
    String saveData(WarehousingFilingAttachmentSaveParam param);

    /**
    * 修改
    *
    * @param param 参数
    * @return 主键ID
    */
    String updateData(WarehousingFilingAttachmentSaveParam param);

    /**
    * 删除
    *
    * @param ids 参数
    * @return 主键ID
    */
    Boolean removeData(List<String> ids);

    /**
    * 批量新增或修改（这里需要我们生成ID）
    *
    * @param params 参数
    */
    Boolean saveOrUpdateBatchData(List<WarehousingFilingAttachmentSaveParam> params);

    /**
    * 其他系统批量新增或修改（这里不需要我们生成ID）
    *
    * @param params 参数
    */
    Boolean saveOrUpdateBatchDataByOtherSystem(List<WarehousingFilingAttachmentSaveParam> params);

    /**
    * 导入
    *
    * @param file
    */
    void importData(MultipartFile file);

    /**
    * 导出
    *
    * @param pageParam 参数
    */
    void exportData(WarehousingFilingAttachmentPageParam pageParam);

    /**
    * 根据主表ID，查询从表列表
    *
    * @return 列表
    */
    List<WarehousingFilingAttachmentVo> listByMainId(String companyId);

    /**
    * 根据主表ID，删除从表数据
    *
    * @return 列表
    */
    Boolean deleteByMainId(String companyId);

    /**
    * 获取保存参数
    *
    * @return 列表
    */
    List<WarehousingFilingAttachmentSaveParam> listByMainIdWithSaveParam(String companyId);

    /**
    * 根据主表ID，查询从表列表（包含历史）
    *
    * @return 列表
    */
    List<WarehousingFilingAttachmentVo> listByMainIdWithHistory(String companyId);

    /**
    * 获取保存参数（包含历史）
    *
    * @return 列表
    */
    List<WarehousingFilingAttachmentSaveParam> listByMainIdWithSaveParamWithHistory(String companyId);

    /**
    * 唯一性
    */
    WarehousingFilingAttachment getByUnx(String companyId,String fileName);

    /**
    * 唯一性更新
    *
    * @param saveParam 参数
    * @return 主键ID
    */
    Boolean updateOrSaveByUnx(WarehousingFilingAttachmentSaveParam saveParam);

    /**
    * @Description 上传文件
    *
    * @Param file
    * @return FileVo
    **/
    FileVo uploadUse(MultipartFile file);

    /**
    * @Description 批量上传文件
    *
    * @Param [files]
    * @return List<FileVo>
    **/
    List<FileVo> batchUploadUse(MultipartFile[] files);

    /**
    * @Description 下载文件
    *
    * @Param fileVo
    * @return void
    **/
    void download(FileVo fileVo);

    /**
    * @Description 批量下载文件
    *
    * @Param fileStorageIdList
    * @return void
    **/
    void downloadToZip(List<FileVo> FileVos);

    /**
    * 数据绑定
    *
    * @param dataBindQuerys 数据查询列表
    */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

}