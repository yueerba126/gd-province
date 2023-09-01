package com.sydata.manage.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.enums.QualityCheckTypeEnum;
import com.sydata.collect.api.param.manage.QualityInspectionApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.manage.annotation.DataBindQualityInspection;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.manage.domain.QualityInspection;
import com.sydata.manage.mapper.QualityInspectionMapper;
import com.sydata.manage.param.QualityInspectionPageParam;
import com.sydata.manage.service.IQualityInspectionService;
import com.sydata.manage.vo.QualityInspectionVo;
import com.sydata.report.api.param.manage.QualityInspectionReportParam;
import org.apache.commons.collections4.SetUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import static com.sydata.collect.api.enums.DataApiEnum.QUALITY_INSPECTION;
import static jodd.util.StringPool.COMMA;
import static jodd.util.StringPool.HASH;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * <p>
 * 质检信息表 服务实现类
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@CacheConfig(cacheNames = QualityInspectionServiceImpl.CACHE_NAME)
@Service("qualityInspectionService")
public class QualityInspectionServiceImpl
        extends BaseDataImpl<QualityInspectionApiParam, QualityInspectionMapper, QualityInspection, QualityInspectionReportParam>
        implements IQualityInspectionService {

    final static String CACHE_NAME = "manage:qualityInspection";

    @Resource
    private QualityInspectionMapper qualityInspectionMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public QualityInspection getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(QualityInspection entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(QualityInspection entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(QualityInspection entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<QualityInspectionVo> pages(QualityInspectionPageParam pageParam) {
        Page<QualityInspection> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), QualityInspection::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), QualityInspection::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getHwdm()), QualityInspection::getHwdm, pageParam.getHwdm())
                .ge(isNotEmpty(pageParam.getBeginQfrq()), QualityInspection::getQfrq, pageParam.getBeginQfrq())
                .le(isNotEmpty(pageParam.getEndQfrq()), QualityInspection::getQfrq, pageParam.getEndQfrq())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), QualityInspection::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), QualityInspection::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(QualityInspection::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, QualityInspectionVo.class);
    }

    @DataBindFieldConvert
    @Override
    public QualityInspectionVo detail(String id) {
        IQualityInspectionService locationService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(locationService.getById(id), QualityInspectionVo.class);
    }

    @DataBindService(strategy = DataBindQualityInspection.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, qualityInspectionMapper);
    }

    @Override
    public DataApiEnum api() {
        return QUALITY_INSPECTION;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, QualityInspectionApiParam param) {
        int length = param.getZblb().split(HASH).length;

        // 校验扦样时间不得晚于检验时间、入库日期不得晚于检验时间
        if (param.getJysj() != null) {
            dataIssueDto.append(param.getQysj().isBefore(param.getJysj()),
                    QualityInspectionApiParam::getQysj, "扦样时间不得晚于检验时间");

            if (param.getRkrq() != null) {
                dataIssueDto.append(param.getRkrq().isBefore(param.getJysj().toLocalDate()),
                        QualityInspectionApiParam::getRkrq, "入库日期不得晚于检验时间");
            }
            if (param.getQfrq() != null) {
                dataIssueDto.append(param.getJysj().toLocalDate().isBefore(param.getQfrq())
                                || param.getJysj().toLocalDate().isEqual(param.getQfrq()),
                        QualityInspectionApiParam::getQfrq, "签发日期不得早于检验时间");
            }
        }

        // 校验入库日期不得晚于扦样时间
        if (param.getRkrq() != null) {
            dataIssueDto.append(param.getRkrq().isBefore(param.getQysj().toLocalDate()),
                    QualityInspectionApiParam::getRkrq, "入库日期不得晚于扦样时间");
        }

        //指标判定结果数组
        Set<String> zblbNames = SetUtils.hashSet(param.getZblbName().split(HASH));
        String[] zbjgpds = param.getZbjgpd().split(HASH);
        dataIssueDto.append(length == zblbNames.size(),
                QualityInspectionApiParam::getZblb, "指标类别对应不上国标类别");

        dataIssueDto.append(length == zbjgpds.length,
                QualityInspectionApiParam::getZbjgpd, "指标结果判定与指标类别个数未保持一致");


        dataIssueDto.append(param.getJyxmName().split(COMMA).length == param.getJyxm().split(COMMA).length,
                QualityInspectionApiParam::getJyxmName,
                "检验项目中存在非国家标准或重复的检验项目代码，请参考LS/T1704.1-2004粮食信息分类与编码粮食检验");

        for (String zbjg : zbjgpds) {
            dataIssueDto.append(zblbNames.contains(QualityCheckTypeEnum.getByDescription(zbjg)),
                    QualityInspectionApiParam::getZbjgpd,
                    "指标结果判定与指标类别对应：" +
                            "1、质量指标填写“达标”、”不达标”" +
                            "2、储存品质指标填写“宜存”、“轻度不宜存”、“重度不宜存” " +
                            "3、食品安全指标填写“合格”、“不合格”注：大型货位综合检验结果需增加“综合判定”描述，" +
                            "例如：达标|综合判定。多项指标时以#分隔，例如：达标|综合判定#宜存#合格");
        }
    }
}