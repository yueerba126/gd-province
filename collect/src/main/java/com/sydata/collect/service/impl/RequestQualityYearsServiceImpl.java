package com.sydata.collect.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.domain.RequestQualityDay;
import com.sydata.collect.domain.RequestQualityYears;
import com.sydata.collect.mapper.RequestQualityYearsMapper;
import com.sydata.collect.param.QualityYearsPageParam;
import com.sydata.collect.service.IRequestQualityDayService;
import com.sydata.collect.service.IRequestQualityYearsService;
import com.sydata.collect.vo.CollectQualityVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.framework.orm.FrameworkSqlHelper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * 数据收集-请求数据质量年报Service业务层处理
 *
 * @author lzq
 * @date 2022-10-27
 */
@Service("requestQualityYearsService")
public class RequestQualityYearsServiceImpl extends ServiceImpl<RequestQualityYearsMapper, RequestQualityYears>
        implements IRequestQualityYearsService {

    @Resource
    private RequestQualityYearsMapper requestQualityYearsMapper;

    @Resource
    private IRequestQualityDayService requestQualityDayService;

    @DataBindFieldConvert
    @Override
    public Page<CollectQualityVo> report(QualityYearsPageParam pageParam) {
        return requestQualityYearsMapper.report(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()), pageParam);
    }

    @Override
    public List<RequestQualityYears> listByStockHouseIds(List<String> stockHouseIds, List<String> apiCodes) {
        return requestQualityYearsMapper.listByStockHouseIds(stockHouseIds, apiCodes);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @XxlJob("requestQualityYearsHandle")
    public void requestQualityYearsHandle() {
        // 拉取昨天请求数据质量日报的企业列表
        LocalDate days = LocalDate.now();
        List<String> enterpriseIds = requestQualityDayService.enterpriseIdsByDays(days);
        XxlJobHelper.log("请求数据质量日报企业列表总数:{}", enterpriseIds.size());
        if (CollectionUtils.isEmpty(enterpriseIds)) {
            return;
        }

        // 分片拉取数据
        List<String> shards = FrameworkJobHelper.shardList(enterpriseIds);
        if (CollectionUtils.isEmpty(shards)) {
            return;
        }

        // 质量日报数据转换为质量年报数据
        Map<String, RequestQualityYears> idMap = dayDataToYearData(days, shards);

        // 查询数据库ID是否已存在-- 查数据库根据ID查ID,而后根据ID移除idMap中的key
        List<RequestQualityYears> updates = super.lambdaQuery()
                .select(RequestQualityYears::getId)
                .in(RequestQualityYears::getId, idMap.keySet())
                .list()
                .stream()
                .map(RequestQualityYears::getId)
                .map(idMap::remove)
                .collect(Collectors.toList());

        // 批量新增
        super.saveBatch(idMap.values());

        // 批量修改
        FrameworkSqlHelper.executeBatchUpdate(this, updates, requestQualityYearsMapper::statistics);

        // 根据指定日期和企业设置数据为已处理
        requestQualityDayService.handleByDays(days, shards);
    }

    /**
     * 质量日报数据转换为质量年报数据
     *
     * @param date          日期
     * @param enterpriseIds 企业集合
     * @return 质量年报数据Map
     */
    private Map<String, RequestQualityYears> dayDataToYearData(LocalDate date, List<String> enterpriseIds) {
        List<RequestQualityDay> days = requestQualityDayService.listByDays(date, enterpriseIds);
        Map<String, RequestQualityYears> idMap = MapUtil.newHashMap();

        // 根据联合主键分组计算总值
        StreamEx.of(days).groupingBy(t -> {
            String years = String.valueOf(t.getDays().getYear());
            String enterpriseId = t.getEnterpriseId();
            String stockHouseId = t.getStockHouseId();
            String apiCode = t.getApiCode();
            return new StringJoiner(DASH).add(years).add(enterpriseId).add(stockHouseId).add(apiCode).toString();
        }).forEach((id, list) -> {
            int total = StreamEx.of(list).mapToInt(RequestQualityDay::getTotal).sum();
            int success = StreamEx.of(list).mapToInt(RequestQualityDay::getSuccess).sum();
            int fail = StreamEx.of(list).mapToInt(RequestQualityDay::getFail).sum();

            RequestQualityDay day = list.get(0);

            String years = String.valueOf(day.getDays().getYear());
            RequestQualityYears requestQualityYears = new RequestQualityYears()
                    .setId(id)
                    .setYears(years)
                    .setEnterpriseId(day.getEnterpriseId())
                    .setStockHouseId(day.getStockHouseId())
                    .setApiCode(day.getApiCode())
                    .setTotal(total)
                    .setSuccess(success)
                    .setFail(fail)
                    .setCountryId(day.getCountryId())
                    .setProvinceId(day.getProvinceId())
                    .setCityId(day.getCityId())
                    .setAreaId(day.getAreaId());
            idMap.put(id, requestQualityYears);
        });
        return idMap;
    }
}
