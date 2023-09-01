package com.sydata.basis.service.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.config.BasisConfig;
import com.sydata.basis.domain.File;
import com.sydata.basis.mapper.FileMapper;
import com.sydata.basis.param.FilePageParam;
import com.sydata.basis.service.IFileService;
import com.sydata.basis.vo.FileVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.enums.FileTypeEnum;
import com.sydata.collect.api.param.basis.FileApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.basis.annotation.DataBindFile;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.FileReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.FILE;
import static com.sydata.collect.api.enums.FileCategoryEnum.PMT;
import static com.sydata.collect.api.enums.FileTypeEnum.AERIAL_VIEW_IMG;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * @author lzq
 * @description
 * @date 2022/10/11 16:24
 */
@CacheConfig(cacheNames = FileServiceImpl.CACHE_NAME)
@Service("fileService")
public class FileServiceImpl extends BaseDataImpl<FileApiParam, FileMapper, File, FileReportParam> implements IFileService {
    final static String CACHE_NAME = "basis:file";

    @Resource
    private FileMapper fileMapper;

    @Resource
    private IFileStorageService fileStorageService;

    @Resource
    private BasisConfig basisConfig;

    @Cacheable(key = "'id='+#id")
    @Override
    public File getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(File entity) {
        boolean state = super.save(entity);
        // 使用文件
        fileStorageService.useFile(entity.getFileStorageId());
        return state;
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(File entity) {
        boolean state = super.updateById(entity);
        // 当文件存储ID发生变更时,使用新文件弃用老文件
        File oldEntity = oldEntity();
        if (!oldEntity.getFileStorageId().equals(entity.getFileStorageId())) {
            fileStorageService.useFile(entity.getFileStorageId());
            fileStorageService.discardFile(entity.getFileStorageId());
        }
        return state;
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(File entity) {
        // 弃用文件
        fileStorageService.discardFile(entity.getFileStorageId());
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<FileVo> pages(FilePageParam pageParam) {
        Page<File> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), File::getId, pageParam.getId())
                .likeRight(isNotEmpty(pageParam.getWjmc()), File::getWjmc, pageParam.getWjmc())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), File::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), File::getStockHouseId, pageParam.getStockHouseId())
                .eq(isNotEmpty(pageParam.getWjlx()), File::getWjlx, pageParam.getWjlx())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), File::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), File::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(File::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, FileVo.class);
    }

    @DataBindFieldConvert
    @Override
    public FileVo detail(String id) {
        IFileService fileService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(fileService.getById(id), FileVo.class);
    }

    @Override
    public void downloadPmt(String stockHouseId) {
        String fileStorageId = super.lambdaQuery()
                .select(File::getFileStorageId)
                .eq(File::getWjmc, stockHouseId + PMT.getCategory())
                .eq(File::getKqdm, stockHouseId)
                .eq(File::getWjlx, AERIAL_VIEW_IMG.getType())
                .oneOpt()
                .map(File::getFileStorageId)
                .orElseThrow(() -> new NullPointerException("该库区并未上传库区鸟瞰图"));
        fileStorageService.download(fileStorageId);
    }

    @DataBindService(strategy = DataBindFile.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, fileMapper);
    }

    @Override
    public DataApiEnum api() {
        return FILE;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, FileApiParam param) {
        // 校验文件存储类型与实际业务类型是否匹配,比如说明明是入库图片偏偏跑去绑定视频
        FileTypeEnum typeEnum = FileTypeEnum.getByType(param.getWjlx());
        dataIssueDto.append(typeEnum.getFileType().equals(param.getFileStorageType()),
                FileApiParam::getWjlx, typeEnum.getMsg() + "只能上传" + typeEnum.getFileType());

        // 库区平面图校验图片像素是否为1920*1080
        if (AERIAL_VIEW_IMG.getFileType().equals(param.getWjlx())) {
            InputStream inputStream = null;
            try {
                byte[] bytes = fileStorageService.fileBytes(param.getFileStorageId());
                inputStream = new ByteArrayInputStream(bytes);
                BufferedImage image = ImgUtil.read(inputStream);

                int width = basisConfig.getAerialViewImgWidth();
                int height = basisConfig.getAerialViewImgHeight();
                boolean state = image.getWidth() == width && image.getHeight() == height;
                dataIssueDto.append(state, FileApiParam::getFileStorageId,
                        String.format("库区平面图分辨率只能为%s*%s", width, height));
            } finally {
                IoUtil.close(inputStream);
            }
        }
    }


    @Override
    public FileReportParam toReport(FileApiParam param) {
        String hexString = fileStorageService.toHexString(param.getFileStorageId());
        return super.toReport(param).setWjl(hexString);
    }
}
