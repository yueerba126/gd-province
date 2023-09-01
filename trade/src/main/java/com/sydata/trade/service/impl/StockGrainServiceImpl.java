package com.sydata.trade.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.config.BasisConfig;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.StockGrainApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.trade.annotation.DataBindStockGrain;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.organize.domain.Region;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IFoodOwnerService;
import com.sydata.organize.service.IRegionService;
import com.sydata.report.api.config.ReportConfig;
import com.sydata.report.api.param.trade.StockGrainReportParam;
import com.sydata.trade.domain.StockGrain;
import com.sydata.trade.mapper.StockGrainMapper;
import com.sydata.trade.param.StockGrainPageParam;
import com.sydata.trade.service.IStockGrainNewestService;
import com.sydata.trade.service.IStockGrainService;
import com.sydata.trade.vo.StockGrainVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.sydata.common.constant.ValidConstant.*;
import static java.math.BigDecimal.ZERO;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.logging.log4j.util.Strings.EMPTY;

/**
 * 粮油购销-粮食库存Service业务层处理
 *
 * @author lzq
 * @date 2022-07-22
 */
@CacheConfig(cacheNames = StockGrainServiceImpl.CACHE_NAME)
@Service("stockGrainService")
public class StockGrainServiceImpl
        extends BaseDataImpl<StockGrainApiParam, StockGrainMapper, StockGrain, StockGrainReportParam>
        implements IStockGrainService {
    final static String CACHE_NAME = "trade:stockGrain";

    @Resource
    private StockGrainMapper stockGrainMapper;

    @Resource
    private ReportConfig reportConfig;

    @Resource
    private BasisConfig basisConfig;

    @Resource
    private IFoodOwnerService foodOwnerService;

    @Resource
    private IRegionService regionService;

    @Resource
    private IStockGrainNewestService stockGrainNewestService;

    @Cacheable(key = "'id='+#id")
    @Override
    public StockGrain getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(StockGrain entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(StockGrain entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(StockGrain entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<StockGrainVo> pages(StockGrainPageParam pageParam) {
        Page<StockGrain> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), StockGrain::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), StockGrain::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getLqgsdwdm()), StockGrain::getLqgsdwdm, pageParam.getLqgsdwdm())
                .eq(isNotEmpty(pageParam.getHwdm()), StockGrain::getHwdm, pageParam.getHwdm())
                .eq(isNotEmpty(pageParam.getLspzdm()), StockGrain::getLspzdm, pageParam.getLspzdm())
                .eq(isNotEmpty(pageParam.getLsxzdm()), StockGrain::getLsxzdm, pageParam.getLsxzdm())
                .ge(isNotEmpty(pageParam.getBeginrcsj()), StockGrain::getRcsj, pageParam.getBeginrcsj())
                .le(isNotEmpty(pageParam.getEndrcsj()), StockGrain::getRcsj, pageParam.getEndrcsj())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), StockGrain::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), StockGrain::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(StockGrain::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, StockGrainVo.class);
    }

    @DataBindFieldConvert
    @Override
    public StockGrainVo detail(String id) {
        IStockGrainService stockGrainService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(stockGrainService.getById(id), StockGrainVo.class);
    }

    @DataBindService(strategy = DataBindStockGrain.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, stockGrainMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.STOCK_GRAIN;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, StockGrainApiParam param) {
        Region region = regionService.getById("000" + param.getGb());
        dataIssueDto.append(isNotEmpty(region), StockGrainApiParam::getGb,
                "国别代码不存在，请参考参照 GB/T 2659-2000 中世界各国和地区名称代码表中的数字代码");


        //计价数量是否为0
        boolean valuationState = param.getJjsl().compareTo(ZERO) == 1;
        //实际数量是否为0
        boolean actuallyState = param.getSjsl().compareTo(ZERO) == 1;

        // 空仓校验数量
        if (KC.equals(param.getHwzt())) {
            dataIssueDto.append(!valuationState && !actuallyState,
                    StockGrainApiParam::getHwzt, "货位状态为空仓时，实际数量和计价数量应都为0");
        } else if (FC.equals(param.getHwzt())) {
            dataIssueDto.append(valuationState && actuallyState,
                    StockGrainApiParam::getHwzt, "货位状态为封仓时，实际数量和计价数量不应为0");
        } else if (RKZ.equals(param.getHwzt())) {
            dataIssueDto.append(isEmpty(param.getFcrq()),
                    StockGrainApiParam::getHwzt, "若货位状态为入库中，封仓日期应该为空");
        }

        //校验储粮方式为包装时，且计价数量和实际数量不为0时，包存粮包数不能为0
        if (BCL.equals(param.getClfs()) && valuationState && actuallyState) {
            dataIssueDto.append(param.getBclbs() > 0,
                    StockGrainApiParam::getBclbs, "储粮方式为包装时,包存粮包数不应为0");
        }

        // 封仓日期不得早于入仓时间，若货位为入库中封仓日期应该为空
        if (param.getFcrq() != null) {
            LocalDateTime endFcrq = param.getFcrq().plusDays(1).atStartOfDay();
            dataIssueDto.append(param.getRcsj().isBefore(endFcrq), StockGrainApiParam::getFcrq, "封仓日期不得早于入仓时间");
        }

        // 粮食性质代码不为空时进行粮权校验
        if (isNotBlank(param.getLsxzdm())) {

            // 储备粮校验粮权行政区划代码所在省份必须是本省 商品粮校验不需要填写粮权行政区划代码
            if (basisConfig.getReservesFoodNatureCodes().contains(param.getLsxzdm())) {
                dataIssueDto.append(isNotEmpty(param.getLqxzqhdm()),
                        StockGrainApiParam::getLqxzqhdm, "粮食性质为储备粮时粮权行政区划代码必填");

                boolean state = reportConfig.getProvinceId().equals(param.getLqxzqhdmProvinceId());
                dataIssueDto.append(state, StockGrainApiParam::getLqxzqhdm,
                        "粮权行政区划代码所属省份必须是" + reportConfig.getProvinceName());

                // 校验储备性质归属行政区划代码问题
                String type = basisConfig.getReservesFoodNatureCodeTypeMap().get(param.getLqxzqhdm());
                dataIssueDto.append(StringUtils.isBlank(type) || type.equals(param.getLqxzqhdmType()),
                        StockGrainApiParam::getLqxzqhdm, () -> type + "储粮权行政区划代码类型必须是" + type);
            } else {
                dataIssueDto.append(isBlank(param.getLqxzqhdm()),
                        StockGrainApiParam::getLqxzqhdm, "商品粮不需要填写粮权行政区划代码");
            }
        }

        // 校验出仓完成时间应晚于封仓日期
        if (isNotEmpty(param.getCcwcsj()) && isNotEmpty(param.getFcrq())) {
            boolean before = param.getFcrq().isBefore(param.getCcwcsj().toLocalDate());
            dataIssueDto.append(before, StockGrainApiParam::getCcwcsj, "出仓完成时间不得早于封仓日期");
        }

        // 当货位状态为空仓时才需要填写出仓完成时间
        boolean ccwcsj = KC.equals(param.getHwzt()) || param.getCcwcsj() == null;
        dataIssueDto.append(ccwcsj, StockGrainApiParam::getHwzt, "当货位状态为空仓时才需要填写出仓完成时间");
    }

    @Override
    protected void collectAfter(StockGrainApiParam param, StockGrain entity) {
        // 获取本条库存最大的最后更新时间再构建ID查询库存数据后同步到粮食库存最新数据中
        LocalDateTime lastTime = stockGrainMapper.lastUpdateTime(param.getHwdm(), param.getLspzdm(), param.getRcsj());
        StockGrain lastStock = Optional.ofNullable(lastTime).map(param::buildId).map(super::getById).orElse(null);
        stockGrainNewestService.stockSyn(param, lastStock);

        // 储备粮同步粮权关系
        String regionId = param.getLqxzqhdm();
        String companyId = param.getLqgsdwdm();
        if (isNotBlank(regionId) && isNotBlank(companyId)) {
            LoginUser loginUser = UserSecurity.loginUser();
            foodOwnerService.saveByBuffer(regionId, companyId, loginUser.getOrganizeId(), loginUser.getStockHouseId());
        }
    }

    @Override
    public StockGrainReportParam toReport(StockGrainApiParam param) {
        StockGrainReportParam stockGrainReportParam = super.toReport(param);

        // 时间类型转换为string，目的是为了置空国家平台的字段
        String ccwcsj = param.getCcwcsj() == null ? EMPTY : LocalDateTimeUtil.formatNormal(param.getCcwcsj());
        return stockGrainReportParam.setCcwcsj(ccwcsj);
    }

    @Override
    public void dataQualityCustomCheck(DataQualityProcessContext context) {
        // 数据质量校验：库存计价数量=入库+倒入-出库-倒出-损溢
        List<String> ids = stockGrainMapper.qualityCheck(context.getDataIds());

        ids.forEach(id -> {
            DataIssueDto dataIssue = context.issueRecord(id);

            String issueRemark = "计价数量不等于入库信息中净重之和(含倒入)减去出库信息中净重之和(含倒出)减去损溢信息中净重损溢数量";
            dataIssue.append(StockGrainApiParam::getJjsl, issueRemark);
        });
    }
}
