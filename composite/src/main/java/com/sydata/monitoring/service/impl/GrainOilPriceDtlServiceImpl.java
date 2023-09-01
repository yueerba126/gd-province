package com.sydata.monitoring.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.monitoring.dto.GrainOilPriceDtlQueryDto;
import com.sydata.monitoring.entity.GrainOilPriceDtl;
import com.sydata.monitoring.mapper.GrainOilPriceDtlMapper;
import com.sydata.monitoring.service.IGrainOilPriceDtlService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.monitoring.vo.GrainOilPriceDtlVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 流通检测-粮油价格采集明细表 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Service
public class GrainOilPriceDtlServiceImpl extends ServiceImpl<GrainOilPriceDtlMapper, GrainOilPriceDtl> implements IGrainOilPriceDtlService {

    @Resource
    private IGrainOilPriceDtlService grainOilPriceDtlService;

    @Override
    public Page<GrainOilPriceDtlVo> pagination(GrainOilPriceDtlQueryDto dtlQueryDto) {
        Page<GrainOilPriceDtl> page = grainOilPriceDtlService.lambdaQuery()
                .in(CollectionUtils.isNotEmpty(dtlQueryDto.getMainIds()), GrainOilPriceDtl::getMainId, dtlQueryDto.getMainIds())
                .eq(StringUtils.isNoneBlank(dtlQueryDto.getLspzdm()), GrainOilPriceDtl::getLspzdm, dtlQueryDto.getLspzdm())
                .eq(dtlQueryDto.getTaxPrice() != null, GrainOilPriceDtl::getTaxPrice, dtlQueryDto.getTaxPrice())
                .between(dtlQueryDto.getBeginBillDate() != null && dtlQueryDto.getEndBillDate() != null,
                        GrainOilPriceDtl::getBillDate, dtlQueryDto.getBeginBillDate(), dtlQueryDto.getEndBillDate())
                .page(new Page<>(dtlQueryDto.pageNum, dtlQueryDto.getPageSize()));

        List<GrainOilPriceDtl> records = page.getRecords();

        Page<GrainOilPriceDtlVo> grainOilPriceDtlVoPage = BeanUtils.copyToPage(page, GrainOilPriceDtlVo.class);

        if (CollectionUtils.isNotEmpty(records)) {
            return grainOilPriceDtlVoPage;
        }

        List<GrainOilPriceDtlVo> voList = records.stream()
                .map(GrainOilPriceDtlVo::new)
                .collect(Collectors.toList());

        grainOilPriceDtlVoPage.setRecords(voList);

        return grainOilPriceDtlVoPage;
    }
}
