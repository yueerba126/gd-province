package com.sydata.trade.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.trade.InStockCheckApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.trade.annotation.DataBindInStockCheck;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.trade.InStockCheckReportParam;
import com.sydata.trade.domain.InStockCheck;
import com.sydata.trade.mapper.InStockCheckMapper;
import com.sydata.trade.param.InStockCheckPageParam;
import com.sydata.trade.service.IInStockCheckService;
import com.sydata.trade.vo.InStockCheckVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

import static com.sydata.common.constant.ValidConstant.RK;
import static com.sydata.common.constant.ValidConstant.YY_MM_DD;
import static jodd.util.StringPool.COMMA;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


/**
 * 粮油购销-入库检验信息Service业务层处理
 *
 * @author lzq
 * @date 2022-07-22
 */
@CacheConfig(cacheNames = InStockCheckServiceImpl.CACHE_NAME)
@Service("inStockCheckService")
public class InStockCheckServiceImpl
        extends BaseDataImpl<InStockCheckApiParam, InStockCheckMapper, InStockCheck, InStockCheckReportParam>
        implements IInStockCheckService {

    final static String CACHE_NAME = "trade:inStockCheck";

    @Resource
    private InStockCheckMapper inStockCheckMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public InStockCheck getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(InStockCheck entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(InStockCheck entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(InStockCheck entity) {
        return super.removeById(entity);
    }

    @Override
    @DataBindFieldConvert
    public Page<InStockCheckVo> pages(InStockCheckPageParam pageParam) {
        Page<InStockCheck> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), InStockCheck::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), InStockCheck::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getHwdm()), InStockCheck::getHwdm, pageParam.getHwdm())
                .eq(isNotEmpty(pageParam.getRkjydh()), InStockCheck::getRkjydh, pageParam.getRkjydh())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), InStockCheck::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), InStockCheck::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(InStockCheck::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, InStockCheckVo.class);
    }

    @DataBindFieldConvert
    @Override
    public InStockCheckVo detail(String id) {
        IInStockCheckService inStockCheckService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(inStockCheckService.getById(id), InStockCheckVo.class);
    }

    @DataBindService(strategy = DataBindInStockCheck.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, inStockCheckMapper);
    }

    @Override
    public DataApiEnum api() {
        return DataApiEnum.IN_STOCK_CHECK;
    }


    @Override
    public void validated(DataIssueDto dataIssueDto, InStockCheckApiParam param) {
        //获取入库检验单号格式
        String rkjydh = String.format("%s%s", RK, LocalDateTimeUtil.format(param.getJysj(), YY_MM_DD));
        dataIssueDto.append(param.getRkjydh().startsWith(rkjydh),
                InStockCheckApiParam::getRkjydh, "入库检验单号第3-8位与检验时间不一致");

        // 校验扦样时间不得晚于检验时间
        dataIssueDto.append(param.getQysj().isBefore(param.getJysj()),
                InStockCheckApiParam::getQysj, "扦样时间不得晚于检验时间");

        // 校验检验指标
        int length = param.getJyxm().split(COMMA).length;
        dataIssueDto.append(length == param.getJyxmName().split(COMMA).length,
                InStockCheckApiParam::getQysj, "检验项目中存在非国家标准或重复的检验项目代码");

        dataIssueDto.append(length == param.getJyz().split(COMMA).length,
                InStockCheckApiParam::getJyz, "检验值与检验项目个数不一致");

        dataIssueDto.append(length == param.getZkj().split(COMMA).length,
                InStockCheckApiParam::getZkj, "增扣价与检验项目个数不一致");

        dataIssueDto.append(length == param.getZkl().split(COMMA).length,
                InStockCheckApiParam::getZkl, "增扣量与检验项目个数不一致");
    }
}
