/**
 * @filename:GradedEnterpriseMaterialService 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.grade.domain.GradedEnterpriseMaterial;
import com.sydata.grade.param.GradedEnterpriseMaterialPageParam;
import com.sydata.grade.param.GradedEnterpriseMaterialSaveParam;
import com.sydata.grade.vo.GradedEnterpriseMaterialVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-企业申报证明材料服务层)
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IGradedEnterpriseMaterialService extends IService<GradedEnterpriseMaterial> {

    /**
     * @Author lzq
     * @Description 上传证明材料
     * @Date 18:46 2023/5/30
     * @Param [fileStorageUploadParam]
     * @return void
     **/
    String uploadUse(MultipartFile file);

    /**
     * @Author lzq
     * @Description 下载证明材料
     * @Date 18:46 2023/5/30
     * @Param [fileStorageUploadParam]
     * @return void
     **/
    void download(String fileStorageId);

    /**
     * @Author lzq
     * @Description 批量下载证明材料
     * @Date 18:46 2023/5/30
     * @Param [fileStorageUploadParam]
     * @return void
     **/
    void downloadToZip(String fileStorageIds);

    /**
     * @Author lzq
     * @Description 下载证明材料
     * @Date 18:46 2023/5/30
     * @Param [fileStorageUploadParam]
     * @return void
     **/
    String getFileBytes(String fileStorageId);

    /**
     * @Author lzq
     * @Description 下载证明材料
     * @Date 18:46 2023/5/30
     * @Param [fileStorageUploadParam]
     * @return void
     **/
    InputStream getInputStream(String fileStorageId);

    /**
     * 批量新增或修改
     *
     * @param params 参数
     */
    Boolean saveOrUpdateBatchData(List<GradedEnterpriseMaterialSaveParam> params);

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GradedEnterpriseMaterialVo> pages(GradedEnterpriseMaterialPageParam pageParam);

    /**
     * 列表
     *
     * @param qyid 主键ID
     * @return 列表
     */
    List<GradedEnterpriseMaterialVo> list(String qyid);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    GradedEnterpriseMaterialVo detail(String id);

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
    String saveData(GradedEnterpriseMaterialSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(GradedEnterpriseMaterialSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);

}