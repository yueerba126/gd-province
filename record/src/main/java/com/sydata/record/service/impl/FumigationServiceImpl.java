package com.sydata.record.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.record.FumigationApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.UserSecurity;
import com.sydata.record.domain.Fumigation;
import com.sydata.record.domain.FumigationDtl;
import com.sydata.record.domain.FumigationPeople;
import com.sydata.record.domain.FumigationWay;
import com.sydata.record.enums.ApprovalStateEnum;
import com.sydata.record.mapper.FumigationMapper;
import com.sydata.record.param.FumigationApprovedParam;
import com.sydata.record.param.FumigationPageParam;
import com.sydata.record.service.IFumigationDtlService;
import com.sydata.record.service.IFumigationPeopleService;
import com.sydata.record.service.IFumigationService;
import com.sydata.record.service.IFumigationWayService;
import com.sydata.record.vo.FumigationDetailsVo;
import com.sydata.record.vo.FumigationDtlVo;
import com.sydata.record.vo.FumigationPageVo;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.sydata.collect.api.enums.DataApiEnum.FUMIGATION;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 备案管理-熏蒸Service业务层处理
 *
 * @author system
 * @date 2022-12-10
 */
@CacheConfig(cacheNames = FumigationServiceImpl.CACHE_NAME)
@Service("fumigationService")
public class FumigationServiceImpl extends BaseDataImpl<FumigationApiParam, FumigationMapper, Fumigation, Object>
        implements IFumigationService {

    final static String CACHE_NAME = "record:fumigation";

    @Resource
    private FumigationMapper fumigationMapper;

    @Resource
    private IFumigationDtlService fumigationDtlService;

    @Resource
    private IFumigationPeopleService fumigationPeopleService;

    @Resource
    private IFumigationWayService fumigationWayService;

    @Resource
    private IFileStorageService fileStorageService;

    @Cacheable(key = "'id='+#id")
    @Override
    public Fumigation getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Fumigation entity) {
        // 使用文件
        fileStorageService.useFile(entity.getFileStorageId());
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Fumigation entity) {
        // 当文件存储ID发生变更时,使用新文件弃用老文件
        Fumigation oldEntity = oldEntity();
        if (!Objects.equals(oldEntity.getFileStorageId(), entity.getFileStorageId())) {
            fileStorageService.useFile(entity.getFileStorageId());
            fileStorageService.discardFile(entity.getFileStorageId());
        }
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Fumigation entity) {
        // 弃用文件
        fileStorageService.discardFile(entity.getFileStorageId());
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<FumigationPageVo> pages(FumigationPageParam pageParam) {
        Page<Fumigation> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Fumigation::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Fumigation::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), Fumigation::getStockHouseId, pageParam.getStockHouseId())
                .ge(isNotEmpty(pageParam.getBeginTbrq()), Fumigation::getTbrq, pageParam.getBeginTbrq())
                .le(isNotEmpty(pageParam.getEndTbrq()), Fumigation::getTbrq, pageParam.getEndTbrq())
                .ge(isNotEmpty(pageParam.getBeginSqxzrq()), Fumigation::getSqxzrq, pageParam.getBeginSqxzrq())
                .le(isNotEmpty(pageParam.getEndSqxzrq()), Fumigation::getSqxzrq, pageParam.getEndSqxzrq())
                .eq(isNotEmpty(pageParam.getShzt()), Fumigation::getShzt, pageParam.getShzt())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Fumigation::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Fumigation::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Fumigation::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, FumigationPageVo.class);
    }

    @DataBindFieldConvert
    @Override
    public FumigationDetailsVo detail(String id) {
        IFumigationService fumigationService = SpringUtil.getBean(this.getClass());

        return BeanUtils.copyByClass(fumigationService.getById(id), FumigationDetailsVo.class)
                .setDtls(BeanUtils.copyToList(fumigationDtlService.listByFumigationId(id), FumigationDtlVo.class))
                .setPeoples(fumigationPeopleService.listByFumigationId(id))
                .setWays(fumigationWayService.listByFumigationId(id));
    }

    @CacheEvict(key = "'id='+#param.id")
    @Override
    public boolean approved(FumigationApprovedParam param) {
        return super.lambdaUpdate()
                .set(Fumigation::getShrs, UserSecurity.userName())
                .set(Fumigation::getShyj, param.getShyj())
                .set(Fumigation::getShsj, LocalDateTime.now())
                .set(Fumigation::getShzt, param.getShzt())
                .eq(Fumigation::getId, param.getId())
                .eq(Fumigation::getShzt, ApprovalStateEnum.WAIT.getState())
                .update();
    }

    @Override
    public DataApiEnum api() {
        return FUMIGATION;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, FumigationApiParam param) {
        // 校验新增修改的时候明细、人员、方式必填
        boolean delete = CzBzEnum.D.getCzBz().equals(param.getCzbz());
        dataIssueDto.append(delete || isNotEmpty(param.getDtls()), FumigationApiParam::getDtls,
                "新增或修改时熏蒸明细列表必填");

        dataIssueDto.append(delete || isNotEmpty(param.getPeoples()), FumigationApiParam::getPeoples,
                "新增或修改时熏蒸人员列表必填");

        dataIssueDto.append(delete || isNotEmpty(param.getWays()), FumigationApiParam::getWays,
                "新增或修改时熏蒸方式列表必填");

        // 校验熏蒸明细中的库区代码是否对得上主表中的库区代码
        Set<String> stockHouse = StreamEx.of(emptyIfNull(param.getDtls())).map(FumigationApiParam.Dtl::getKqdm).toSet();
        stockHouse.remove(param.getKqdm());
        dataIssueDto.append(isEmpty(stockHouse), FumigationApiParam.Dtl::getCfdm, () -> {
            Set<String> warehouseCodes = StreamEx.of(emptyIfNull(param.getDtls()))
                    .filter(v -> !param.getKqdm().equals(v.getKqdm()))
                    .map(FumigationApiParam.Dtl::getCfdm)
                    .toSet();
            return String.format("仓房或油罐编码%s所属库区编码非%s", warehouseCodes, param.getKqdm());
        });
    }

    @Override
    protected void collectAfter(FumigationApiParam param, Fumigation fumigation) {
        String id = fumigation.getId();

        // 设置熏蒸明细
        List<FumigationDtl> dtls = StreamEx.of(emptyIfNull(param.getDtls()))
                .map(v -> BeanUtils.copyByClass(v, FumigationDtl.class))
                .toList();
        fumigationDtlService.build(id, dtls);

        // 设置熏蒸人员
        List<FumigationPeople> peoples = StreamEx.of(emptyIfNull(param.getPeoples()))
                .map(v -> BeanUtils.copyByClass(v, FumigationPeople.class))
                .toList();
        fumigationPeopleService.build(id, peoples);

        // 设置熏蒸方式
        List<FumigationWay> ways = StreamEx.of(emptyIfNull(param.getWays()))
                .map(v -> BeanUtils.copyByClass(v, FumigationWay.class))
                .toList();
        fumigationWayService.build(id, ways);
    }
}