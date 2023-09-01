package com.sydata.trade.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.InStockApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.trade.annotation.DataBindInStock;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.report.api.param.trade.InStockReportParam;
import com.sydata.trade.domain.InStock;
import com.sydata.trade.mapper.InStockMapper;
import com.sydata.trade.param.InStockPageParam;
import com.sydata.trade.service.IInStockService;
import com.sydata.trade.vo.InStockVo;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import static com.sydata.common.constant.ValidConstant.*;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮油购销-入库信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-22
 */
@CacheConfig(cacheNames = InStockServiceImpl.CACHE_NAME)
@Service("inStockService")
public class InStockServiceImpl
        extends BaseDataImpl<InStockApiParam, InStockMapper, InStock, InStockReportParam>
        implements IInStockService {

    final static String CACHE_NAME = "trade:inStock";

    @Resource
    private InStockMapper inStockMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public InStock getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(InStock entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(InStock entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(InStock entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<InStockVo> pages(InStockPageParam pageParam) {
        Page<InStock> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), InStock::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), InStock::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getHwdm()), InStock::getHwdm, pageParam.getHwdm())
                .eq(isNotEmpty(pageParam.getRkywdh()), InStock::getRkywdh, pageParam.getRkywdh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), InStock::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), InStock::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(InStock::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, InStockVo.class);
    }


    @DataBindFieldConvert
    @Override
    public InStockVo detail(String id) {
        IInStockService stockInService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(stockInService.getById(id), InStockVo.class);
    }

    @DataBindService(strategy = DataBindInStock.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, inStockMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.IN_STOCK;
    }


    @Override
    public void validated(DataIssueDto dataIssueDto, InStockApiParam param) {
        //获取入库单号格式
        String rkywdh = String.format("%s%s", RK, LocalDateTimeUtil.format(param.getYwrq(), YY_MM_DD));
        Assert.state(param.getRkywdh().startsWith(rkywdh), "入库业务单号第3-8位与业务日期不一致");
        if ((param.getQtkl()) != null && param.getQtkl().compareTo(BigDecimal.ZERO) != 0) {
            dataIssueDto.append(isNotEmpty(param.getKlyy()), InStockApiParam::getKlyy, "其他扣量不为0时，需注明扣量原因");
        }
        if ((param.getZkj()) != null && param.getZkj().compareTo(BigDecimal.ZERO) != 0) {
            dataIssueDto.append(isNotEmpty(param.getZkhyy()), InStockApiParam::getZkhyy, "增扣价不为0时，需注明原因");
        }

        //校验合同业务类型
        dataIssueDto.append(!XS_HT.equals(param.getHtywlx()), InStockApiParam::getHth, "入库数据不能关联销售合同");

        //一车一结模式校验净重和出库结算数量、校验合同号是否对等
        if (isNotEmpty(param.getRkjsdh())) {
            dataIssueDto.append(StringUtils.equals(param.getHth(), param.getRkjsdHth()),
                    InStockApiParam::getHth, "一车一结模式:入库单合同号不等于入库结算单合同号");

            dataIssueDto.append(param.getJz().compareTo(param.getJssl()) == 0,
                    InStockApiParam::getJz, "一车一结模式:入库净重必须等于入库结算数量");

            dataIssueDto.append(StringUtils.equals(param.getHwdm(), param.getRkjsdHwdm()), InStockApiParam::getHwdm,
                    "一车一结模式:入库货位代码不等于入库结算货位代码");
        }

        dataIssueDto.append(param.getDjsj().isBefore(param.getMzjlsj())
                        && param.getMzjlsj().isBefore(param.getPzjlsj())
                        && param.getPzjlsj().isBefore(param.getCmsj()),
                InStockApiParam::getCmsj, "入库数据时间顺序须为登记时间->毛重时间->皮重时间->出门时间");

        //质检扣量（小计）= 其中(水分增扣量+杂质增扣量+不完善粒扣量+互混扣量+生霉粒扣量+整精米粒扣量+谷外糙米扣量+黄粒米扣量+其他扣量)
        boolean quality = StreamEx.of(param.getQzsfzkl())
                .append(param.getQzzzzkl())
                .append(param.getQzbwslkl())
                .append(param.getQzhhkl())
                .append(param.getQzsmlkl())
                .append(param.getQzzjmlkl())
                .append(param.getQzgwcmkl())
                .append(param.getQzhlmkl())
                .append(param.getQzqtkl())
                .nonNull()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .compareTo(param.getZjklxj()) == 0;
        dataIssueDto.append(quality, InStockApiParam::getZjklxj,
                "质检扣量应该等于其中(水分增扣量+杂质增扣量+不完善粒扣量+互混扣量+生霉粒扣量+整精米粒扣量+谷外糙米扣量+黄粒米扣量+其他扣量)");

        //入库信息：净重=毛重-皮重+扣量
        BigDecimal deduction = StreamEx.of(param.getZjklxj())
                .append(param.getZlfyzkl())
                .append(param.getBzwkl())
                .append(param.getQtkl())
                .append(param.getXckl())
                .nonNull()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal netWeight = param.getMz().subtract(param.getPz()).add(deduction);
        dataIssueDto.append(param.getJz().compareTo(netWeight) == 0, InStockApiParam::getJz,
                "净重应该等于毛重-皮重+质检扣量+整理费用折扣量+包装物扣量+其他扣量+现场扣量");
    }

}
