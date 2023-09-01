package com.sydata.manage.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.manage.ExceptionMonitoringEventApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.common.manage.annotation.DataBindExceptionMonitoringEvent;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.manage.domain.ExceptionMonitoringEvent;
import com.sydata.manage.mapper.ExceptionMonitoringEventMapper;
import com.sydata.manage.param.ExceptionMonitoringEventPageParam;
import com.sydata.manage.service.IExceptionMonitoringEventService;
import com.sydata.manage.vo.ExceptionMonitoringEventVo;
import com.sydata.report.api.param.manage.ExceptionMonitoringEventReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.collect.api.enums.DataApiEnum.EXCEPTION_MONITORING_EVENT;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮库管理-库区视频监控异常事件告警Service业务层处理
 *
 * @author lzq
 * @date 2022-07-25
 */
@CacheConfig(cacheNames = ExceptionMonitoringEventServiceImpl.CACHE_NAME)
@Service("exceptionMonitoringEventService")
public class ExceptionMonitoringEventServiceImpl extends BaseDataImpl<ExceptionMonitoringEventApiParam,
        ExceptionMonitoringEventMapper, ExceptionMonitoringEvent, ExceptionMonitoringEventReportParam>
        implements IExceptionMonitoringEventService {

    final static String CACHE_NAME = "manage:exceptionMonitoringEvent";

    @Resource
    private ExceptionMonitoringEventMapper exceptionMonitoringEventMapper;

    @Resource
    private IFileStorageService fileStorageService;

    @Cacheable(key = "'id='+#id")
    @Override
    public ExceptionMonitoringEvent getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(ExceptionMonitoringEvent entity) {
        boolean state = super.save(entity);
        // 使用文件
        fileStorageService.useFile(entity.getFileStorageId());
        return state;
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(ExceptionMonitoringEvent entity) {
        boolean state = super.updateById(entity);

        // 当文件存储ID发生变更时,使用新文件弃用老文件
        ExceptionMonitoringEvent oldEntity = oldEntity();
        if (!oldEntity.getFileStorageId().equals(entity.getFileStorageId())) {
            fileStorageService.useFile(entity.getFileStorageId());
            fileStorageService.discardFile(entity.getFileStorageId());
        }
        return state;
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(ExceptionMonitoringEvent entity) {
        // 弃用文件
        fileStorageService.discardFile(entity.getFileStorageId());
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<ExceptionMonitoringEventVo> pages(ExceptionMonitoringEventPageParam pageParam) {
        Page<ExceptionMonitoringEvent> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), ExceptionMonitoringEvent::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), ExceptionMonitoringEvent::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), ExceptionMonitoringEvent::getStockHouseId, pageParam.getStockHouseId())
                .likeRight(isNotEmpty(pageParam.getSpjksbid()), ExceptionMonitoringEvent::getSpjksbid, pageParam.getSpjksbid())
                .eq(isNotEmpty(pageParam.getAzwzlx()), ExceptionMonitoringEvent::getAzwzlx, pageParam.getAzwzlx())
                .ge(isNotEmpty(pageParam.getBeginGjsj()), ExceptionMonitoringEvent::getGjsj, pageParam.getBeginGjsj())
                .le(isNotEmpty(pageParam.getEndGjsj()), ExceptionMonitoringEvent::getGjsj, pageParam.getEndGjsj())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), ExceptionMonitoringEvent::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), ExceptionMonitoringEvent::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(ExceptionMonitoringEvent::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ExceptionMonitoringEventVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ExceptionMonitoringEventVo detail(String id) {
        IExceptionMonitoringEventService exceptionMonitoringEventService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(exceptionMonitoringEventService.getById(id), ExceptionMonitoringEventVo.class);
    }

    @DataBindService(strategy = DataBindExceptionMonitoringEvent.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, exceptionMonitoringEventMapper);
    }

    @Override
    public DataApiEnum api() {
        return EXCEPTION_MONITORING_EVENT;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, ExceptionMonitoringEventApiParam param) {
        // 校验文件存储类型与视频文件后缀名是否相符
        dataIssueDto.append(StringUtils.equals(param.getFileStorageType(), param.getSpwjhzm()),
                ExceptionMonitoringEventApiParam::getSpwjhzm, "文件存储类型与视频文件后缀名不相符");
    }


    @Override
    public ExceptionMonitoringEventReportParam toReport(ExceptionMonitoringEventApiParam param) {
        String hexString = fileStorageService.toHexString(param.getFileStorageId());
        return super.toReport(param).setSpwjl(hexString);
    }

}