package com.sydata.common.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.common.file.domain.FileStorage;
import com.sydata.common.file.enums.FileStateEnum;
import com.sydata.common.file.param.FileStoragePageParam;
import com.sydata.common.file.vo.FileStorageVo;
import com.sydata.framework.databind.domain.DataBindQuery;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * 文件存储Service接口
 *
 * @author lzq
 * @date 2022-06-23
 */
public interface IFileStorageService extends IService<FileStorage> {

    /**
     * 分页查询
     *
     * @param pageParam 文件存储分页参数
     * @return 分页列表
     */
    Page<FileStorageVo> pages(FileStoragePageParam pageParam);


    /**
     * 上传文件
     *
     * @param file          文件
     * @param organizeId    组织ID
     * @param stockHouseId  库区ID
     * @param fileStateEnum 文件状态枚举
     * @param fileType      文件类型
     * @return 文件存储ID
     */
    String upload(MultipartFile file, String organizeId, String stockHouseId,
                  FileStateEnum fileStateEnum, String fileType);

    /**
     * 上传使用文件
     *
     * @param file         文件
     * @param organizeId   组织ID
     * @param stockHouseId 库区ID
     * @return 文件存储ID
     */
    String uploadUse(MultipartFile file, String organizeId, String stockHouseId);

    /**
     * 上传未使用文件
     *
     * @param file         文件
     * @param organizeId   组织ID
     * @param stockHouseId 库区ID
     * @return 文件存储ID
     */
    String uploadNotUse(MultipartFile file, String organizeId, String stockHouseId);

    /**
     * 文件下载
     *
     * @param fileStorageId 文件存储ID
     */
    void download(String fileStorageId);

    /**
     * 文件下载 用自定义的文件名
     *
     * @param fileStorageId 文件存储ID
     */
    void downloadWithFileName(String fileStorageId, String fileName);

    /**
     * 下载多个文件转zip压缩包
     *
     * @param fileStorageIds 文件存储ID集合
     * @param fileNames      文件名集合
     * @param zipName        压缩包名称
     */
    void downloadToZip(List<String> fileStorageIds, List<String> fileNames, String zipName);

    /**
     * 使用文件
     *
     * @param fileStorageId 文件存储ID
     */
    void useFile(String fileStorageId);

    /**
     * 弃用文件
     *
     * @param fileStorageId 文件存储ID
     * @return 状态
     */
    Boolean discardFile(String fileStorageId);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

    /**
     * 文件流
     *
     * @param id 文件存储ID
     * @return 文件流
     */
    InputStream fileStreamById(String id);

    /**
     * 文件字节数组
     *
     * @param id 文件存储ID
     * @return 字节数组
     */
    byte[] fileBytes(String id);

    /**
     * 文件流十六进制字符
     *
     * @param id 文件存储ID
     * @return 文件流十六进制字符
     */
    String toHexString(String id);
}