package com.sydata.reserve.scale.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.excel.ZtExcelBuildUtil;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.reserve.scale.domain.PlanReserveScale;
import com.sydata.reserve.scale.domain.PlanReserveScaleLog;
import com.sydata.reserve.scale.dto.PlanReserveScaleDto;
import com.sydata.reserve.scale.mapper.PlanReserveScaleMapper;
import com.sydata.reserve.scale.param.PlanReserveScaleLogPageParam;
import com.sydata.reserve.scale.param.PlanReserveScalePageParam;
import com.sydata.reserve.scale.service.IPlanReserveScaleLogService;
import com.sydata.reserve.scale.service.IPlanReserveScaleService;
import com.sydata.reserve.scale.vo.PlanReserveScaleLogVo;
import com.sydata.reserve.scale.vo.PlanReserveScalePageVo;
import com.sydata.reserve.scale.vo.PlanReserveScaleVo;
import lombok.SneakyThrows;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备计划管理-储备规模Service业务层处理
 *
 * @author fuql
 * @date 2023-05-17
 */
@Service("planReserveScaleService")
public class PlanReserveScaleServiceImpl extends ServiceImpl<PlanReserveScaleMapper, PlanReserveScale> implements IPlanReserveScaleService {

    @Resource
    private PlanReserveScaleMapper planReserveScaleMapper;

    @Resource
    private IPlanReserveScaleLogService planReserveScaleLogService;

    private final static Integer ONE = 1;
    private final static String PROVINCE = "440000";
    private final static String CACHE_COUNTRY = "000156";
    private final static String PROVINCE_NAME = "省级";
    private final static String REGION_NAME = "市本级";
    /**
     * 东莞市
     */
    private final static String REGION_DGS = "441900";
    /**
     * 中山市
     */
    private final static String REGION_ZSS = "442000";


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(PlanReserveScale param) {
        PlanReserveScale scale = super.lambdaQuery()
                .in(PlanReserveScale::getRegionCode, param.getRegionCode())
                .one();
        Assert.state(Objects.isNull(scale), "行政区划"+param.getRegionCode() + "已有储备规模数据！");
        super.save(param);
        return param.getId();
    }

    @Override
    public PlanReserveScalePageVo pageData(PlanReserveScalePageParam param) {
        PlanReserveScalePageVo vo = new PlanReserveScalePageVo();
        LoginUser loginUser = UserSecurity.loginUser();
        String regionId = loginUser.getRegionId();
        boolean status = StringUtils.isEmpty(param.getRegionParentCode()) && !(PROVINCE.equals(regionId) || CACHE_COUNTRY.equals(regionId));
        if (status){
            param.setRegionParentCode(regionId);
        }
        Page<PlanReserveScale> page;
        if (CACHE_COUNTRY.equals(param.getRegionParentCode()) || PROVINCE.equals(param.getRegionParentCode()) || !isNotEmpty(param.getRegionParentCode())){
            page = planReserveScaleMapper.pageScaleData(new Page<>(param.getPageNum(), param.getPageSize()) , param);
        } else {
            page = super.lambdaQuery()
                    .in(CollectionUtils.isNotEmpty(param.getIds()) , PlanReserveScale::getId ,param.getIds() )
                    .and(isNotEmpty(param.getRegionParentCode()) , sql ->
                            sql.eq(isNotEmpty(param.getRegionParentCode()), PlanReserveScale::getRegionParentCode, param.getRegionParentCode())
                            .or()
                            .eq(isNotEmpty(param.getRegionParentCode()), PlanReserveScale::getRegionCode, param.getRegionParentCode()))
                    .orderByAsc(PlanReserveScale::getRegionCode)
                    .page(new Page<>(param.getPageNum(), param.getPageSize()));
        }
        Page<PlanReserveScaleVo> scaleVoPage = BeanUtils.copyToPage(page, PlanReserveScaleVo.class);
        DataBindHandleBootstrap.dataHandConvert(scaleVoPage);
        if (PROVINCE.equals(param.getRegionParentCode())) {
            if (CollectionUtils.isNotEmpty(scaleVoPage.getRecords())) {
                List<PlanReserveScaleVo> records = scaleVoPage.getRecords();
                records.forEach(sc->{
                    if (PROVINCE.equals(sc.getRegionCode())) {
                        sc.setRegionName(PROVINCE_NAME);
                    }
                });
                scaleVoPage.setRecords(records);
            }
        } else if (!isNotEmpty(param.getRegionParentCode())) {
            if (CollectionUtils.isNotEmpty(scaleVoPage.getRecords())) {
                getAllRegionCodePage(scaleVoPage);
            }
        } else {
            if (CollectionUtils.isNotEmpty(scaleVoPage.getRecords())) {
                List<PlanReserveScaleVo> records = scaleVoPage.getRecords();
                records.forEach(sc->{
                    if (REGION_DGS.equals(sc.getRegionCode()) || REGION_ZSS.equals(sc.getRegionCode())) {
                        return;
                    }
                    if (PROVINCE.equals(sc.getRegionParentCode())) {
                        sc.setRegionName(REGION_NAME);
                    }
                });
                scaleVoPage.setRecords(records);
            }
        }
        List<PlanReserveScale> list = super.lambdaQuery()
                .eq(PROVINCE.equals(param.getRegionParentCode()) , PlanReserveScale::getRegionCode, param.getRegionParentCode())
                .and(!PROVINCE.equals(param.getRegionParentCode()) && isNotEmpty(param.getRegionParentCode()) ,sql ->
                        sql.eq(PlanReserveScale::getRegionParentCode, param.getRegionParentCode())
                        .or()
                        .eq(isNotEmpty(param.getRegionParentCode()), PlanReserveScale::getRegionCode, param.getRegionParentCode()))
                .list();
        if (CollectionUtils.isNotEmpty(list)) {
            BigDecimal grainTotal = StreamEx.of(list)
                    .map(PlanReserveScale::getGrainAmount)
                    .nonNull()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal oilTotal = StreamEx.of(list)
                    .map(PlanReserveScale::getOilAmount)
                    .nonNull()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            vo.setGrainTotal(grainTotal).setOilTotal(oilTotal);
        } else {
            vo.setGrainTotal(BigDecimal.ZERO).setOilTotal(BigDecimal.ZERO);
        }
        return vo.setScaleVo(scaleVoPage);
    }

    private void getAllRegionCodePage(Page<PlanReserveScaleVo> scaleVoPage) {
        List<PlanReserveScaleVo> records = scaleVoPage.getRecords();
        List<String> regionCodes = StreamEx.of(records)
                .map(PlanReserveScale::getRegionCode)
                .collect(Collectors.toList());
        List<PlanReserveScale> list = super.lambdaQuery()
                .in(PlanReserveScale::getRegionParentCode, regionCodes)
                .list();
        Map<String, List<PlanReserveScale>> regionParentCodeMap = StreamEx.of(list)
                .groupingBy(PlanReserveScale::getRegionParentCode);
        records.forEach(sc->{
            if (PROVINCE.equals(sc.getRegionCode())) {
                sc.setRegionName(PROVINCE_NAME);
            } else {
                if (Objects.nonNull(regionParentCodeMap) && CollectionUtils.isNotEmpty(regionParentCodeMap.get(sc.getRegionCode()))) {
                    BigDecimal grainAmount = StreamEx.of(regionParentCodeMap.get(sc.getRegionCode()))
                            .map(PlanReserveScale::getGrainAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal oilAmount = StreamEx.of(regionParentCodeMap.get(sc.getRegionCode()))
                            .map(PlanReserveScale::getOilAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal amountOil = Objects.isNull(sc.getOilAmount()) ? BigDecimal.ZERO:sc.getOilAmount();
                    BigDecimal amountGrain = Objects.isNull(sc.getGrainAmount()) ? BigDecimal.ZERO:sc.getGrainAmount();
                    sc.setGrainAmount(grainAmount.add(amountGrain))
                            .setOilAmount(oilAmount.add(amountOil));
                }
            }
        });
        scaleVoPage.setRecords(records);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(PlanReserveScale param) {
        PlanReserveScale scale = super.lambdaQuery()
                .eq(PlanReserveScale::getRegionCode, param.getRegionCode())
                .ne(PlanReserveScale::getId, param.getId())
                .one();
        Assert.state(Objects.isNull(scale), "行政区划"+param.getRegionCode() + "已有储备规模数据！");
        PlanReserveScaleLog log = BeanUtils.copyByClass(scale, PlanReserveScaleLog.class);
        log.setAdjustGrainAmount(param.getGrainAmount());
        log.setAdjustOilAmount(param.getGrainAmount());
        planReserveScaleLogService.save(log);
        super.updateById(param);
        return param.getId();
    }

    @Override
    public Boolean removeData(List<String> ids) {
        return super.lambdaUpdate()
                .in(PlanReserveScale::getId, ids)
                .remove();
    }

    @Override
    public PlanReserveScaleVo getDataById(Long id) {
        return BeanUtils.copyByClass(getById(id), PlanReserveScaleVo.class);
    }

    @Override
    public void templateExport() {
        List<PlanReserveScaleDto> dtos = planReserveScaleMapper.queryScaleDtos();
        ZtExcelBuildUtil.buildExcelExport(PlanReserveScaleDto.class, "储备规模数据模板").setData(dtos).doWebExport();
    }

    @DataBindFieldConvert
    @SneakyThrows
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public List<PlanReserveScaleVo> imports(MultipartFile file, Boolean isUpdate) {
        LoginUser loginUser = UserSecurity.loginUser();
        ImportParams params = new ImportParams();
        params.setTitleRows(ONE);
        List<PlanReserveScaleDto> dtos = ExcelImportUtil.importExcel(file.getInputStream(), PlanReserveScaleDto.class, params);
        Assert.state(CollectionUtils.isNotEmpty(dtos), "导入数据为空，请重新导入！");
        List<PlanReserveScale> planReserveScales = BeanUtils.copyToList(dtos, PlanReserveScale.class);
        List<String> regionCodes = StreamEx.of(planReserveScales)
                .map(PlanReserveScale::getRegionCode)
                .collect(Collectors.toList());
        if (isUpdate) {
            List<PlanReserveScale> list = super.lambdaQuery()
                    .in(PlanReserveScale::getRegionCode, regionCodes)
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return BeanUtils.copyToList(list, PlanReserveScaleVo.class);
            }
        }
        List<PlanReserveScaleLog> logs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(regionCodes)) {
            List<PlanReserveScale> list = super.lambdaQuery()
                    .in(PlanReserveScale::getRegionCode, regionCodes)
                    .list();
            Map<String, PlanReserveScale> logMap = StreamEx.of(planReserveScales)
                    .toMap(PlanReserveScale::getRegionCode, identity());
            list.forEach(sc->{
                PlanReserveScaleLog log = BeanUtils.copyByClass(sc, PlanReserveScaleLog.class);
                if (Objects.nonNull(logMap) && Objects.nonNull(logMap.get(sc.getRegionCode()))) {
                    PlanReserveScale scale = logMap.get(sc.getRegionCode());
                    log.setAdjustGrainAmount(scale.getGrainAmount());
                    log.setAdjustOilAmount(scale.getOilAmount());
                    log.setCreateBy(loginUser.getName());
                    log.setCreateTime(LocalDateTime.now());
                }
                logs.add(log);
            });
        }
        super.lambdaUpdate()
                .in(PlanReserveScale::getRegionCode, regionCodes)
                .remove();
        super.saveBatch(planReserveScales);
        if (CollectionUtils.isNotEmpty(logs)) {
            planReserveScaleLogService.saveBatch(logs);
        }
        return null;
    }

    @Override
    public void export(PlanReserveScalePageParam param) {
        List<PlanReserveScaleVo> records = pageData(param).getScaleVo().getRecords();
        List<PlanReserveScaleDto> planReserveScales = BeanUtils.copyToList(records, PlanReserveScaleDto.class);
        ZtExcelBuildUtil.buildExcelExport(PlanReserveScaleDto.class, "储备规模数据").setData(planReserveScales).doWebExport();
    }

    @DataBindFieldConvert
    @Override
    public Page<PlanReserveScaleLogVo> getLogInfo(PlanReserveScaleLogPageParam param) {
        Page<PlanReserveScaleLog> page = planReserveScaleLogService.lambdaQuery()
                .and(isNotEmpty(param.getRegionParentCode()) , sql ->
                        sql.eq(!PROVINCE.equals(param.getRegionParentCode()),PlanReserveScaleLog::getRegionParentCode, param.getRegionParentCode())
                                .or()
                                .eq(PlanReserveScaleLog::getRegionCode, param.getRegionParentCode()))
                .orderByDesc(PlanReserveScaleLog::getCreateTime)
                .page(new Page<>(param.getPageNum(), param.getPageSize()));
        return BeanUtils.copyToPage(page, PlanReserveScaleLogVo.class);
    }
}
