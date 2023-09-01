package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Warehouse;
import com.sydata.basis.dto.WarehouseQualityCheckDto;
import com.sydata.basis.mapper.WarehouseMapper;
import com.sydata.basis.param.WarehousePageParam;
import com.sydata.basis.service.IWarehouseService;
import com.sydata.basis.vo.WarehouseVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.WarehouseApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.WarehouseReportParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static com.sydata.collect.api.enums.DataApiEnum.WAREHOUSE;
import static com.sydata.common.constant.ValidConstant.PFC;
import static com.sydata.common.constant.ValidConstant.QYC;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 基础信息-仓房信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = WarehouseServiceImpl.CACHE_NAME)
@Service("warehouseService")
public class WarehouseServiceImpl
        extends BaseDataImpl<WarehouseApiParam, WarehouseMapper, Warehouse, WarehouseReportParam>
        implements IWarehouseService {

    final static String CACHE_NAME = "basis:warehouse";

    @Resource
    private WarehouseMapper warehouseMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Warehouse getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Warehouse entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Warehouse entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Warehouse entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<WarehouseVo> pages(WarehousePageParam pageParam) {
        Page<Warehouse> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Warehouse::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Warehouse::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getStockHouseId()), Warehouse::getStockHouseId, pageParam.getStockHouseId())
                .likeRight(isNotEmpty(pageParam.getCfdm()), Warehouse::getId, pageParam.getCfdm())
                .likeRight(isNotEmpty(pageParam.getCfmc()), Warehouse::getCfmc, pageParam.getCfmc())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Warehouse::getUpdateTime, pageParam.getEndUpdateTime())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Warehouse::getUpdateTime, pageParam.getBeginUpdateTime())
                .orderByDesc(Warehouse::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, WarehouseVo.class);
    }

    @DataBindFieldConvert
    @Override
    public WarehouseVo detail(String id) {
        IWarehouseService warehouseService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(warehouseService.getById(id), WarehouseVo.class);
    }

    @DataBindService(strategy = DataBindWarehouse.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, warehouseMapper);
    }

    @Override
    public DataApiEnum api() {
        return WAREHOUSE;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, WarehouseApiParam param) {
        if (nonNull(param.getCwc()) && nonNull(param.getCnc())) {
            dataIssueDto.append(param.getCwc().compareTo(param.getCnc()) >= 0,
                    WarehouseApiParam::getCwc, "仓外长应大于等于仓内长");
        }

        if (nonNull(param.getCwk()) && nonNull(param.getCnk())) {
            dataIssueDto.append(param.getCwk().compareTo(param.getCnk()) >= 0,
                    WarehouseApiParam::getCwk, "仓外宽应大于等于仓内宽");
        }

        if (nonNull(param.getCwdg()) && nonNull(param.getCwyg())) {
            dataIssueDto.append(param.getCwdg().compareTo(param.getCwyg()) >= 0,
                    WarehouseApiParam::getCwdg, "仓外顶高应大于等于仓外檐高");
        }

        dataIssueDto.append(param.getTcwj().compareTo(param.getTcnj()) >= 0,
                WarehouseApiParam::getTcwj, "筒仓外径应大于等于筒仓内经");


        if (nonNull(param.getCnyg()) && nonNull(param.getCnzlxg())) {
            dataIssueDto.append(param.getCnyg().compareTo(param.getCnzlxg()) >= 0,
                    WarehouseApiParam::getCnyg, "仓内檐高应大于等于仓内装粮线高");
        }

        if (param.getCflxdm().startsWith(PFC)) {
            if (nonNull(param.getCwc())) {
                dataIssueDto.append(param.getCwc().compareTo(ZERO) == 1,
                        WarehouseApiParam::getCwc, "平房仓仓外长不能小于等于0");
            }
            if (nonNull(param.getCwk())) {
                dataIssueDto.append(param.getCwk().compareTo(ZERO) == 1,
                        WarehouseApiParam::getCwk, "平房仓仓外宽不能小于等于0");
            }
            if (nonNull(param.getCnc())) {
                dataIssueDto.append(param.getCnc().compareTo(ZERO) == 1,
                        WarehouseApiParam::getCnc, "平房仓仓内长不能小于等于0");
            }
            if (nonNull(param.getCnk())) {
                dataIssueDto.append(param.getCnc().compareTo(ZERO) == 1,
                        WarehouseApiParam::getCnk, "平房仓仓内宽不能小于等于0");
            }

            dataIssueDto.append(param.getTcwj().compareTo(ZERO) == 0,
                    WarehouseApiParam::getTcwj, "平房仓筒仓外径应为0");
            dataIssueDto.append(param.getTcnj().compareTo(ZERO) == 0,
                    WarehouseApiParam::getTcnj, "平房仓筒仓内径应为0");

        } else if (param.getCflxdm().startsWith(QYC)) {
            if (nonNull(param.getCwc())) {
                dataIssueDto.append(param.getCwc().compareTo(ZERO) == 0,
                        WarehouseApiParam::getCwc, "浅圆仓仓外长应为0");
            }
            if (nonNull(param.getCwk())) {
                dataIssueDto.append(param.getCwk().compareTo(ZERO) == 0,
                        WarehouseApiParam::getCwk, "浅圆仓仓外宽应为0");
            }
            if (nonNull(param.getCnc())) {
                dataIssueDto.append(param.getCnc().compareTo(ZERO) == 0,
                        WarehouseApiParam::getCnc, "浅圆仓仓内长应为0");
            }

            if (nonNull(param.getCnk())) {
                dataIssueDto.append(param.getCnk().compareTo(ZERO) == 0,
                        WarehouseApiParam::getCnk, "浅圆仓仓内宽应为0");
            }

            dataIssueDto.append(param.getTcwj().compareTo(ZERO) == 1,
                    WarehouseApiParam::getTcwj, "仓房类型为浅圆仓时筒仓外径不能小于等于0");

            dataIssueDto.append(param.getTcnj().compareTo(ZERO) == 1,
                    WarehouseApiParam::getTcnj, "仓房类型为浅圆仓时筒仓内径不能小于等于0");
        }
    }

    @Override
    public void dataQualityCustomCheck(DataQualityProcessContext context) {
        // 数据质量校验：是否上传了廒间信息
        List<WarehouseQualityCheckDto> checks = warehouseMapper.qualityCheck(context.getDataIds());

        checks.forEach(check -> {
            DataIssueDto dataIssue = context.issueRecord(check.getId());
            dataIssue.append(check.getAjs() > 0, WarehouseApiParam::getCfmc, "该仓房下未上传廒间信息");
        });
    }
}