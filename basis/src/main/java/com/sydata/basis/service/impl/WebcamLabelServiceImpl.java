package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.WebcamLabel;
import com.sydata.basis.mapper.WebcamLabelMapper;
import com.sydata.basis.param.WebcamLabelPageParam;
import com.sydata.basis.service.IStockHouseService;
import com.sydata.basis.service.IWebcamLabelService;
import com.sydata.basis.vo.WebcamLabelVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.WebcamLabelApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.basis.annotation.DataBindWebcamLabel;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.WebcamLabelReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static com.sydata.collect.api.enums.DataApiEnum.WEBCAM_LABEL;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 库区图视频监控设备点位标注Service业务层处理
 *
 * @author lzq
 * @date 2022-10-11
 */
@CacheConfig(cacheNames = WebcamLabelServiceImpl.CACHE_NAME)
@Service("webcamLabelService")
public class WebcamLabelServiceImpl
        extends BaseDataImpl<WebcamLabelApiParam, WebcamLabelMapper, WebcamLabel, WebcamLabelReportParam>
        implements IWebcamLabelService {

    final static String CACHE_NAME = "basis:webcamLabel";

    @Resource
    private WebcamLabelMapper webcamLabelMapper;

    @Resource
    private IStockHouseService stockHouseService;

    @Cacheable(key = "'id='+#id")
    @Override
    public WebcamLabel getById(Serializable id) {
        return super.getById(id);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'stockHouseId='+#entity.kqdm")
    })
    @Override
    public boolean save(WebcamLabel entity) {
        return super.save(entity);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'stockHouseId='+#entity.kqdm")
    })
    @Override
    public boolean updateById(WebcamLabel entity) {
        return super.updateById(entity);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'stockHouseId='+#entity.kqdm")
    })
    @Override
    public boolean removeById(WebcamLabel entity) {
        return super.removeById(entity);
    }

    @Override
    public Page<WebcamLabelVo> pages(WebcamLabelPageParam pageParam) {
        Page<WebcamLabel> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), WebcamLabel::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), WebcamLabel::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), WebcamLabel::getStockHouseId, pageParam.getStockHouseId())
                .likeRight(isNotEmpty(pageParam.getSpjksbid()), WebcamLabel::getSpjksbid, pageParam.getSpjksbid())
                .likeRight(isNotEmpty(pageParam.getSpjksbmc()), WebcamLabel::getSpjksbmc, pageParam.getSpjksbmc())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), WebcamLabel::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), WebcamLabel::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(WebcamLabel::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, WebcamLabelVo.class);
    }

    @Override
    public WebcamLabelVo detail(String id) {
        IWebcamLabelService webcamLabelService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(webcamLabelService.getById(id), WebcamLabelVo.class);
    }

    @Cacheable(key = "'stockHouseId='+#stockHouseId")
    @Override
    public List<WebcamLabel> listByStockHouseId(String stockHouseId) {
        return super.lambdaQuery()
                .select(WebcamLabel::getSpjksbmc, WebcamLabel::getSpjksbxdwz, WebcamLabel::getHkCameraIndexCode)
                .eq(WebcamLabel::getStockHouseId, stockHouseId)
                .list();
    }

    @DataBindService(strategy = DataBindWebcamLabel.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, webcamLabelMapper);
    }

    @Override
    public DataApiEnum api() {
        return WEBCAM_LABEL;
    }
}