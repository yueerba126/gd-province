package com.sydata.manage.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.service.IStockHouseService;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.manage.GranaryVideoImageApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.common.manage.annotation.DataBindGranaryVideoImage;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.manage.domain.GranaryVideoImage;
import com.sydata.manage.mapper.GranaryVideoImageMapper;
import com.sydata.manage.param.GranaryVideoImagePageParam;
import com.sydata.manage.service.IGranaryVideoImageService;
import com.sydata.manage.vo.GranaryVideoImageVo;
import com.sydata.report.api.param.manage.GranaryVideoImageReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.GRANARY_VIDEO_IMAGE;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮库管理-仓内视频图像Service业务层处理
 *
 * @author lzq
 * @date 2022-07-25
 */
@CacheConfig(cacheNames = GranaryVideoImageServiceImpl.CACHE_NAME)
@Service("granaryVideoImageService")
public class GranaryVideoImageServiceImpl
        extends BaseDataImpl<GranaryVideoImageApiParam, GranaryVideoImageMapper, GranaryVideoImage, GranaryVideoImageReportParam>
        implements IGranaryVideoImageService {
    final static String CACHE_NAME = "manage:granaryVideoImage";

    @Resource
    private GranaryVideoImageMapper granaryVideoImageMapper;

    @Resource
    private IFileStorageService fileStorageService;

    @Resource
    private IStockHouseService stockHouseService;

    @Cacheable(key = "'id='+#id")
    @Override
    public GranaryVideoImage getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(GranaryVideoImage entity) {
        boolean state = super.save(entity);
        // 使用文件
        fileStorageService.useFile(entity.getFileStorageId());
        return state;
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(GranaryVideoImage entity) {
        boolean state = super.updateById(entity);

        // 当文件存储ID发生变更时,使用新文件弃用老文件
        GranaryVideoImage oldEntity = oldEntity();
        if (!oldEntity.getFileStorageId().equals(entity.getFileStorageId())) {
            fileStorageService.useFile(entity.getFileStorageId());
            fileStorageService.discardFile(entity.getFileStorageId());
        }
        return state;
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(GranaryVideoImage entity) {
        // 弃用文件
        fileStorageService.discardFile(entity.getFileStorageId());
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<GranaryVideoImageVo> pages(GranaryVideoImagePageParam pageParam) {
        Page<GranaryVideoImage> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), GranaryVideoImage::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), GranaryVideoImage::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), GranaryVideoImage::getStockHouseId, pageParam.getStockHouseId())
                .likeRight(isNotEmpty(pageParam.getSpjksbid()), GranaryVideoImage::getSpjksbid, pageParam.getSpjksbid())
                .ge(isNotEmpty(pageParam.getBeginZpsj()), GranaryVideoImage::getZpsj, pageParam.getBeginZpsj())
                .le(isNotEmpty(pageParam.getEndZpsj()), GranaryVideoImage::getZpsj, pageParam.getEndZpsj())
                .eq(isNotEmpty(pageParam.getYzwbh()), GranaryVideoImage::getYzwbh, pageParam.getYzwbh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), GranaryVideoImage::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), GranaryVideoImage::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(GranaryVideoImage::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, GranaryVideoImageVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GranaryVideoImageVo detail(String id) {
        IGranaryVideoImageService locationService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(locationService.getById(id), GranaryVideoImageVo.class);
    }

    @DataBindService(strategy = DataBindGranaryVideoImage.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, granaryVideoImageMapper);
    }

    @Override
    public DataApiEnum api() {
        return GRANARY_VIDEO_IMAGE;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, GranaryVideoImageApiParam param) {
        // 校验文件存储类型与图像文件后缀名是否相符
        dataIssueDto.append(StringUtils.equals(param.getFileStorageType(), param.getTxwjhzm()),
                GranaryVideoImageApiParam::getTxwjhzm, "文件存储类型与图像文件后缀名不相符");
    }

    @Override
    public GranaryVideoImageReportParam toReport(GranaryVideoImageApiParam param) {
        String hexString = fileStorageService.toHexString(param.getFileStorageId());
        return super.toReport(param).setCntxwjl(hexString);
    }
}
