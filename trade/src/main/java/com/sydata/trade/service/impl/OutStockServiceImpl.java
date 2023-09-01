package com.sydata.trade.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.OutStockApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.trade.annotation.DataBindOutStock;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.report.api.param.trade.OutStockReportParam;
import com.sydata.trade.domain.OutStock;
import com.sydata.trade.mapper.OutStockMapper;
import com.sydata.trade.param.OutStockPageParam;
import com.sydata.trade.service.IOutStockService;
import com.sydata.trade.vo.OutStockVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import static com.sydata.common.constant.ValidConstant.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 粮油购销-出库信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-22
 */
@CacheConfig(cacheNames = OutStockServiceImpl.CACHE_NAME)
@Service("outStockService")
public class OutStockServiceImpl
        extends BaseDataImpl<OutStockApiParam, OutStockMapper, OutStock, OutStockReportParam>
        implements IOutStockService {

    final static String CACHE_NAME = "trade:outStock";

    @Resource
    private OutStockMapper outStockMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public OutStock getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(OutStock entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(OutStock entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(OutStock entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<OutStockVo> pages(OutStockPageParam pageParam) {
        Page<OutStock> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), OutStock::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), OutStock::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getHwdm()), OutStock::getHwdm, pageParam.getHwdm())
                .eq(isNotEmpty(pageParam.getCkywdh()), OutStock::getCkywdh, pageParam.getCkywdh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), OutStock::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), OutStock::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(OutStock::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, OutStockVo.class);
    }


    @DataBindFieldConvert
    @Override
    public OutStockVo detail(String id) {
        IOutStockService outStockService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(outStockService.getById(id), OutStockVo.class);
    }

    @DataBindService(strategy = DataBindOutStock.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, outStockMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.OUT_STOCK;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, OutStockApiParam param) {
        //获取入库单号格式
        String ckywdh = String.format("%s%s", CK, LocalDateTimeUtil.format(param.getYwrq(), YY_MM_DD));
        dataIssueDto.append(param.getCkywdh().startsWith(ckywdh), OutStockApiParam::getCkywdh, "出库业务单号第3-8位与业务日期不一致");

        //校验合同业务类型
        dataIssueDto.append(!CG_HT.equals(param.getHtywlx()), OutStockApiParam::getHth, "出库数据不能关联采购合同");


        //一车一结模式校验净重和出库结算数量
        if (isNotEmpty(param.getCkjsdh())) {
            dataIssueDto.append(StringUtils.equals(param.getHth(), param.getCkjsdHth()),
                    OutStockApiParam::getHth, "一车一结模式:出库单合同号不等于出库结算单合同号");

            dataIssueDto.append(param.getJz().compareTo(param.getJssl()) == 0,
                    OutStockApiParam::getJz, "一车一结模式:出库净重必须等于出库结算数量");
        }

        dataIssueDto.append(param.getDjsj().isBefore(param.getPzjlsj())
                        && param.getPzjlsj().isBefore(param.getMzjlsj())
                        && param.getMzjlsj().isBefore(param.getCmsj()),
                OutStockApiParam::getCmsj, "出库数据时间顺序须为登记时间->皮重时间->毛重时间->出门时间");

        //出库信息: 净重=毛重-皮重+扣量
        BigDecimal kzl = nonNull(param.getKzl()) ? param.getKzl() : ZERO;
        BigDecimal netWeight = param.getMz().subtract(param.getPz()).add(kzl);
        dataIssueDto.append(param.getJz().compareTo(netWeight) == 0,
                OutStockApiParam::getJz, "净重应该等于毛重-皮重+扣量");
    }

}
