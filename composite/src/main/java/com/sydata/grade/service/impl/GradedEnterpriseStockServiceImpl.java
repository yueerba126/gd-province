/**
 * @filename:GradedEnterpriseStockServiceImpl 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2018 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.excel.ZtExcelBuildUtil;
import com.sydata.grade.dto.GradedEnterpriseStockDto;
import com.sydata.grade.enums.*;
import com.sydata.grade.http.GradedEnterpriseReviewFactoryService;
import com.sydata.grade.param.*;
import com.sydata.grade.service.IGradedEnterpriseFlowService;
import com.sydata.grade.service.IGradedEnterpriseProcessService;
import com.sydata.grade.service.IGradedEnterpriseReviewService;
import com.sydata.organize.domain.Region;
import com.sydata.organize.enums.RegionTypeEnum;
import com.sydata.organize.service.IRegionService;
import jodd.util.StringUtil;
import org.apache.commons.lang3.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.grade.domain.GradedEnterpriseStock;
import com.sydata.grade.mapper.GradedEnterpriseStockMapper;
import com.sydata.grade.service.IGradedEnterpriseStockService;
import com.sydata.grade.vo.GradedEnterpriseStockVo;
import com.sydata.grade.annotation.DataBindGradedEnterpriseStock;
import com.sydata.common.api.enums.CzBzEnum;
import org.springframework.util.Assert;

/**
 * @Description:TODO(等级粮库评定管理-企业等级库点服务实现)
 * @version: V1.0
 * @author: lzq
 */
@Service("gradedEnterpriseStockService")
public class GradedEnterpriseStockServiceImpl extends ServiceImpl<GradedEnterpriseStockMapper, GradedEnterpriseStock> implements IGradedEnterpriseStockService {

    final static String CACHE_NAME = "composite:gradedEnterpriseStock";

    @Resource
    private GradedEnterpriseStockMapper gradedEnterpriseStockMapper;
    @Resource
    private IGradedEnterpriseFlowService gradedEnterpriseFlowService;
    @Resource
    private IGradedEnterpriseProcessService gradedEnterpriseProcessService;
    @Resource
    private IGradedEnterpriseReviewService gradedEnterpriseReviewService;
    @Resource
    private GradedEnterpriseReviewFactoryService gradedEnterpriseReviewFactoryService;
    @Resource
    private IRegionService regionService;

    @DataBindFieldConvert
    @Override
    public Page<GradedEnterpriseStockVo> pages(GradedEnterpriseStockPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<String> regions = gradedEnterpriseReviewService.getXzqhdms(loginUser.getRegionId());
        Page<GradedEnterpriseStock> page = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedEnterpriseStock::getId, pageParam.getId())
                .like(ObjectUtils.isNotEmpty(pageParam.getKqmc()), GradedEnterpriseStock::getKqmc, pageParam.getKqmc())
                .like(ObjectUtils.isNotEmpty(pageParam.getQymc()), GradedEnterpriseStock::getQymc, pageParam.getQymc())
                .in(!StringUtils.equals(loginUser.getRegionType(), RegionTypeEnum.PROVINCE.getType()), GradedEnterpriseStock::getXzqhdm, regions)
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpnf()), GradedEnterpriseStock::getSpnf, pageParam.getSpnf())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpdj()), GradedEnterpriseStock::getSpdj, pageParam.getSpdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpzt()), GradedEnterpriseStock::getSpzt, pageParam.getSpzt())
                .in(CollectionUtil.isNotEmpty(pageParam.getSbnfList()), GradedEnterpriseStock::getSbnf, pageParam.getSbnfList())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbdj()), GradedEnterpriseStock::getSbdj, pageParam.getSbdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbzt()), GradedEnterpriseStock::getSbzt, pageParam.getSbzt())
                .ne(GradedEnterpriseStock::getCzbz, CzBzEnum.D.getCzBz())
                .orderByAsc(GradedEnterpriseStock::getSpzt)
                .orderByAsc(GradedEnterpriseStock::getProvinceSdzt,GradedEnterpriseStock::getCitySdzt)
                .orderByDesc(GradedEnterpriseStock::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        List<GradedEnterpriseStock> stockPage = page.getRecords();
        stockPage = generateButton(loginUser, stockPage, pageParam);
        page.setRecords(stockPage);
        page.setTotal(stockPage.size());
        return BeanUtils.copyToPage(page, GradedEnterpriseStockVo.class);
    }

    @DataBindFieldConvert
    @Override
    public Page<GradedEnterpriseStockVo> pagesTwo(GradedEnterpriseStockPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<String> regions = gradedEnterpriseReviewService.getXzqhdms(loginUser.getRegionId());
        Page<GradedEnterpriseStock> page = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedEnterpriseStock::getId, pageParam.getId())
                .like(ObjectUtils.isNotEmpty(pageParam.getKqmc()), GradedEnterpriseStock::getKqmc, pageParam.getKqmc())
                .like(ObjectUtils.isNotEmpty(pageParam.getQymc()), GradedEnterpriseStock::getQymc, pageParam.getQymc())
                .in(!StringUtils.equals(loginUser.getRegionType(), RegionTypeEnum.PROVINCE.getType()), GradedEnterpriseStock::getXzqhdm, regions)
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpnf()), GradedEnterpriseStock::getSpnf, pageParam.getSpnf())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpdj()), GradedEnterpriseStock::getSpdj, pageParam.getSpdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpzt()), GradedEnterpriseStock::getSpzt, pageParam.getSpzt())
                .in(CollectionUtil.isNotEmpty(pageParam.getSbnfList()), GradedEnterpriseStock::getSbnf, pageParam.getSbnfList())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbdj()), GradedEnterpriseStock::getSbdj, pageParam.getSbdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbzt()), GradedEnterpriseStock::getSbzt, pageParam.getSbzt())
                .eq(GradedEnterpriseStock::getSpzt, GradeAwardingEnum.PASS.getState())
                .ge(GradedEnterpriseStock::getSpdj, 1)
                .le(GradedEnterpriseStock::getSpdj, 2)
                .ne(GradedEnterpriseStock::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseStock::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        List<GradedEnterpriseStock> stockPage = page.getRecords();
        stockPage = generateButton(loginUser, stockPage, pageParam);
        page.setRecords(stockPage);
        page.setTotal(stockPage.size());
        return BeanUtils.copyToPage(page, GradedEnterpriseStockVo.class);
    }

    private List<GradedEnterpriseStock> generateButton(LoginUser loginUser, List<GradedEnterpriseStock> stockPage, GradedEnterpriseStockPageParam pageParam) {
        String regionId = loginUser.getRegionId();
        Region region = regionService.getById(regionId);
        for (int i = 0; i < stockPage.size(); i++) {
            GradedEnterpriseStock gradedEnterpriseStock = stockPage.get(i);
            if (StringUtil.equals(RegionTypeEnum.PROVINCE.getType(), region.getType())) {
                if (StringUtils.equals(gradedEnterpriseStock.getSpzt(), GradeAwardingEnum.PASS.getState())) {
                    if (StringUtils.equals(gradedEnterpriseStock.getSpdj(), GradeStockEnum.A.getState().toString())) {
                        gradedEnterpriseStock.setButtonStr("详情,摘除");
                    } else {
                        gradedEnterpriseStock.setButtonStr("详情,降级,摘除");
                    }
                } else {
                    gradedEnterpriseStock.setButtonStr("详情");
                }
            } else {
                gradedEnterpriseStock.setButtonStr("详情");
            }
        }
        if(!StringUtils.isEmpty(pageParam.getCzzt())){
            stockPage = stockPage.stream().filter(e->e.getButtonStr().contains(pageParam.getCzzt())).collect(Collectors.toList());
        }
        return stockPage;
    }

    @DataBindFieldConvert
    @Override
    public Page<GradedEnterpriseStockVo> pagesThree(GradedEnterpriseStockPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<String> regions = gradedEnterpriseReviewService.getXzqhdms(loginUser.getRegionId());
        Page<GradedEnterpriseStock> page = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedEnterpriseStock::getId, pageParam.getId())
                .like(ObjectUtils.isNotEmpty(pageParam.getKqmc()), GradedEnterpriseStock::getKqmc, pageParam.getKqmc())
                .like(ObjectUtils.isNotEmpty(pageParam.getQymc()), GradedEnterpriseStock::getQymc, pageParam.getQymc())
                .in(!StringUtils.equals(loginUser.getRegionType(), RegionTypeEnum.PROVINCE.getType()), GradedEnterpriseStock::getXzqhdm, regions)
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpnf()), GradedEnterpriseStock::getSpnf, pageParam.getSpnf())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpdj()), GradedEnterpriseStock::getSpdj, pageParam.getSpdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpzt()), GradedEnterpriseStock::getSpzt, pageParam.getSpzt())
                .in(CollectionUtil.isNotEmpty(pageParam.getSbnfList()), GradedEnterpriseStock::getSbnf, pageParam.getSbnfList())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbdj()), GradedEnterpriseStock::getSbdj, pageParam.getSbdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbzt()), GradedEnterpriseStock::getSbzt, pageParam.getSbzt())
                .eq(GradedEnterpriseStock::getSpzt, GradeAwardingEnum.PASS.getState())
                .ge(GradedEnterpriseStock::getSpdj, 3)
                .le(GradedEnterpriseStock::getSpdj, 5)
                .ne(GradedEnterpriseStock::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseStock::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        List<GradedEnterpriseStock> stockPage = page.getRecords();
        stockPage = generateButton(loginUser, stockPage, pageParam);
        page.setRecords(stockPage);
        page.setTotal(stockPage.size());
        return BeanUtils.copyToPage(page, GradedEnterpriseStockVo.class);
    }

    @Override
    public void exportDataAll(GradedEnterpriseStockPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<String> regions = gradedEnterpriseReviewService.getXzqhdms(loginUser.getRegionId());
        List<GradedEnterpriseStock> list = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedEnterpriseStock::getId, pageParam.getId())
                .like(ObjectUtils.isNotEmpty(pageParam.getKqmc()), GradedEnterpriseStock::getKqmc, pageParam.getKqmc())
                .like(ObjectUtils.isNotEmpty(pageParam.getQymc()), GradedEnterpriseStock::getQymc, pageParam.getQymc())
                .in(!StringUtils.equals(loginUser.getRegionType(), RegionTypeEnum.PROVINCE.getType()), GradedEnterpriseStock::getXzqhdm, regions)
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpnf()), GradedEnterpriseStock::getSpnf, pageParam.getSpnf())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpdj()), GradedEnterpriseStock::getSpdj, pageParam.getSpdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpzt()), GradedEnterpriseStock::getSpzt, pageParam.getSpzt())
                .in(CollectionUtil.isNotEmpty(pageParam.getSbnfList()), GradedEnterpriseStock::getSbnf, pageParam.getSbnfList())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbdj()), GradedEnterpriseStock::getSbdj, pageParam.getSbdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbzt()), GradedEnterpriseStock::getSbzt, pageParam.getSbzt())
                .in(CollectionUtil.isNotEmpty(pageParam.getIds()), GradedEnterpriseStock::getId, pageParam.getIds())
                .ne(GradedEnterpriseStock::getCzbz, CzBzEnum.D.getCzBz())
                .orderByAsc(GradedEnterpriseStock::getSpzt)
                .orderByAsc(GradedEnterpriseStock::getProvinceSdzt,GradedEnterpriseStock::getCitySdzt)
                .orderByDesc(GradedEnterpriseStock::getUpdateTime).list();
        list = generateButton(loginUser, list, pageParam);
        List<GradedEnterpriseStockVo> voList = translate(list);
        DataBindHandleBootstrap.dataHandConvert(voList, 2);
        List<GradedEnterpriseStockDto> gradedEnterpriseReviewDtos = BeanUtils.copyToList(voList, GradedEnterpriseStockDto.class);
        ZtExcelBuildUtil.buildExcelExport(GradedEnterpriseStockDto.class, "全省粮库信息").setData(gradedEnterpriseReviewDtos).doWebExport();
    }

    @Override
    public void exportData2A(GradedEnterpriseStockPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<String> regions = gradedEnterpriseReviewService.getXzqhdms(loginUser.getRegionId());
        List<GradedEnterpriseStock> list = super.lambdaQuery()
                .in(CollectionUtil.isNotEmpty(pageParam.getIds()), GradedEnterpriseStock::getId, pageParam.getIds())
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedEnterpriseStock::getId, pageParam.getId())
                .like(ObjectUtils.isNotEmpty(pageParam.getKqmc()), GradedEnterpriseStock::getKqmc, pageParam.getKqmc())
                .like(ObjectUtils.isNotEmpty(pageParam.getQymc()), GradedEnterpriseStock::getQymc, pageParam.getQymc())
                .in(!StringUtils.equals(loginUser.getRegionType(), RegionTypeEnum.PROVINCE.getType()), GradedEnterpriseStock::getXzqhdm, regions)
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpnf()), GradedEnterpriseStock::getSpnf, pageParam.getSpnf())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpdj()), GradedEnterpriseStock::getSpdj, pageParam.getSpdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpzt()), GradedEnterpriseStock::getSpzt, pageParam.getSpzt())
                .in(CollectionUtil.isNotEmpty(pageParam.getSbnfList()), GradedEnterpriseStock::getSbnf, pageParam.getSbnfList())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbdj()), GradedEnterpriseStock::getSbdj, pageParam.getSbdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbzt()), GradedEnterpriseStock::getSbzt, pageParam.getSbzt())
                .eq(GradedEnterpriseStock::getSpzt, GradeAwardingEnum.PASS.getState())
                .ge(GradedEnterpriseStock::getSpdj, 1)
                .le(GradedEnterpriseStock::getSpdj, 2)
                .ne(GradedEnterpriseStock::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseStock::getUpdateTime).list();
        list = generateButton(loginUser, list, pageParam);
        List<GradedEnterpriseStockVo> voList = translate(list);
        DataBindHandleBootstrap.dataHandConvert(voList, 2);
        List<GradedEnterpriseStockDto> gradedEnterpriseReviewDtos = BeanUtils.copyToList(voList, GradedEnterpriseStockDto.class);
        ZtExcelBuildUtil.buildExcelExport(GradedEnterpriseStockDto.class, "AA级以下粮库信息").setData(gradedEnterpriseReviewDtos).doWebExport();
    }

    @Override
    public void exportData3A(GradedEnterpriseStockPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<String> regions = gradedEnterpriseReviewService.getXzqhdms(loginUser.getRegionId());
        List<GradedEnterpriseStock> list = super.lambdaQuery()
                .in(CollectionUtil.isNotEmpty(pageParam.getIds()), GradedEnterpriseStock::getId, pageParam.getIds())
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedEnterpriseStock::getId, pageParam.getId())
                .like(ObjectUtils.isNotEmpty(pageParam.getKqmc()), GradedEnterpriseStock::getKqmc, pageParam.getKqmc())
                .like(ObjectUtils.isNotEmpty(pageParam.getQymc()), GradedEnterpriseStock::getQymc, pageParam.getQymc())
                .in(!StringUtils.equals(loginUser.getRegionType(), RegionTypeEnum.PROVINCE.getType()), GradedEnterpriseStock::getXzqhdm, regions)
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpnf()), GradedEnterpriseStock::getSpnf, pageParam.getSpnf())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpdj()), GradedEnterpriseStock::getSpdj, pageParam.getSpdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSpzt()), GradedEnterpriseStock::getSpzt, pageParam.getSpzt())
                .in(CollectionUtil.isNotEmpty(pageParam.getSbnfList()), GradedEnterpriseStock::getSbnf, pageParam.getSbnfList())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbdj()), GradedEnterpriseStock::getSbdj, pageParam.getSbdj())
                .eq(ObjectUtils.isNotEmpty(pageParam.getSbzt()), GradedEnterpriseStock::getSbzt, pageParam.getSbzt())
                .eq(GradedEnterpriseStock::getSpzt, GradeAwardingEnum.PASS.getState())
                .ge(GradedEnterpriseStock::getSpdj, 3)
                .le(GradedEnterpriseStock::getSpdj, 5)
                .ne(GradedEnterpriseStock::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseStock::getUpdateTime).list();
        list = generateButton(loginUser, list, pageParam);
        List<GradedEnterpriseStockVo> voList = translate(list);
        DataBindHandleBootstrap.dataHandConvert(voList, 2);
        List<GradedEnterpriseStockDto> gradedEnterpriseReviewDtos = BeanUtils.copyToList(voList, GradedEnterpriseStockDto.class);
        ZtExcelBuildUtil.buildExcelExport(GradedEnterpriseStockDto.class, "AAA级以上粮库信息").setData(gradedEnterpriseReviewDtos).doWebExport();
    }

    @DataBindFieldConvert
    @Override
    public List<GradedEnterpriseStockVo> translate(List<GradedEnterpriseStock> list) {
        return BeanUtils.copyToList(list, GradedEnterpriseStockVo.class);
    }

    @Override
    public Boolean pickRankData(GradedEnterpriseStockParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        // 保存审核信息
        GradedEnterpriseProcessSaveParam gradedEnterpriseProcessSaveParam = new GradedEnterpriseProcessSaveParam();
        gradedEnterpriseProcessSaveParam.setShjg(GradeShjgEnum.PICK.getState());
        gradedEnterpriseProcessSaveParam.setShyj(param.getGradedEnterpriseProcessSaveParam().getShyj());
        gradedEnterpriseProcessSaveParam.setQyid(param.getQyid());
        gradedEnterpriseProcessService.saveData(gradedEnterpriseProcessSaveParam);
        // 库点信息更新
        super.lambdaUpdate()
                .eq(GradedEnterpriseStock::getId, param.getId())
                .set(GradedEnterpriseStock::getSpzt, GradeAwardingEnum.NO_PASS.getState())
                .set(GradedEnterpriseStock::getSpnf, "")
                .set(GradedEnterpriseStock::getSpdj, "")
                .set(GradedEnterpriseStock::getCzbz, CzBzEnum.U.getCzBz())
                .set(GradedEnterpriseStock::getUpdateBy, loginUser.getName())
                .set(GradedEnterpriseStock::getUpdateTime, LocalDateTime.now())
                .update();
        // 更新授牌节点
        updateAwardNode(param);
        // 库点下发库软件
        List<String> ids = Lists.newArrayList();
        ids.add(param.getId());
        gradedEnterpriseReviewFactoryService.removeStock(ids);
        // 申报下发库软件
        GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam = gradedEnterpriseReviewService.getGradedEnterpriseReviewSaveParam(param.getQyid());
        gradedEnterpriseReviewSaveParam.setSbzt(GradeSbztEnum.PASS.getState());
        gradedEnterpriseReviewSaveParam.setSpzt(GradeAwardingEnum.NO_PASS.getState());
        gradedEnterpriseReviewSaveParam.setSpdj("");
        gradedEnterpriseReviewSaveParam.setSpnf("");
        gradedEnterpriseReviewService.updateData(gradedEnterpriseReviewSaveParam);
        return gradedEnterpriseReviewService.gradedEnterpriseHttpServiceSave(gradedEnterpriseReviewSaveParam);
    }

    private void updateIndeedNode(GradedEnterpriseStockParam param) {
        List<GradedEnterpriseFlowSaveParam> gradedEnterpriseFlowSaveParams1 = gradedEnterpriseFlowService.pickNode1(param.getQyid());
        GradedEnterpriseFlowSaveParam gradedEnterpriseFlowSaveParam1 = gradedEnterpriseFlowSaveParams1.get(0);
        gradedEnterpriseFlowSaveParam1.setFlowStatus(GradeFlowStatusEnum.NO_PASS.getState());
        gradedEnterpriseFlowService.updateData(gradedEnterpriseFlowSaveParam1);
    }

    private void updateAwardNode(GradedEnterpriseStockParam param) {
        List<GradedEnterpriseFlowSaveParam> gradedEnterpriseFlowSaveParams2 = gradedEnterpriseFlowService.pickNode2(param.getQyid());
        GradedEnterpriseFlowSaveParam gradedEnterpriseFlowSaveParam2= gradedEnterpriseFlowSaveParams2.get(0);
        gradedEnterpriseFlowSaveParam2.setFlowStatus(GradeFlowStatusEnum.NO_PASS.getState());
        gradedEnterpriseFlowService.updateData(gradedEnterpriseFlowSaveParam2);
    }

    @Override
    public Boolean reduceRankData(GradedEnterpriseStockParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        // 保存审核信息
        GradedEnterpriseProcessSaveParam gradedEnterpriseProcessSaveParam = new GradedEnterpriseProcessSaveParam();
        gradedEnterpriseProcessSaveParam.setShjg(GradeShjgEnum.REDUCE.getState());
        gradedEnterpriseProcessSaveParam.setShyj(param.getGradedEnterpriseProcessSaveParam().getShyj());
        gradedEnterpriseProcessSaveParam.setQyid(param.getQyid());
        gradedEnterpriseProcessService.saveData(gradedEnterpriseProcessSaveParam);
        // 降级验证
        GradedEnterpriseStockVo vo = detail(param.getId());
        Assert.isTrue(ObjectUtils.isNotEmpty(vo), "不存在该企业");
        int level = Integer.valueOf(vo.getSpdj());
        Assert.isTrue(level>1, "1A级不能降级，请摘除！");
        // 库点等级降级
        super.lambdaUpdate()
                .eq(GradedEnterpriseStock::getId, param.getId())
                .set(GradedEnterpriseStock::getSpzt, GradeAwardingEnum.PASS.getState())
                .set(GradedEnterpriseStock::getSpnf, String.valueOf(LocalDateTime.now().getYear()))
                .set(GradedEnterpriseStock::getCzbz, CzBzEnum.U.getCzBz())
                .set(GradedEnterpriseStock::getSpdj, String.valueOf(level-1))
                .set(GradedEnterpriseStock::getUpdateBy, loginUser.getName())
                .set(GradedEnterpriseStock::getUpdateTime, LocalDateTime.now())
                .update();
        // 授牌等级降级
        GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam = gradedEnterpriseReviewService.getGradedEnterpriseReviewSaveParam(param.getQyid());
        gradedEnterpriseReviewSaveParam.setSpdj(String.valueOf(level-1));
        gradedEnterpriseReviewService.updateData(gradedEnterpriseReviewSaveParam);
        // 库点下发库软件
        GradedEnterpriseStockSaveParam saveParam = BeanUtils.copyByClass(param,GradedEnterpriseStockSaveParam.class);
        saveParam.setId(param.getId());
        saveParam.setSpzt(GradeAwardingEnum.PASS.getState());
        saveParam.setSpnf(String.valueOf(LocalDateTime.now().getYear()));
        saveParam.setCzbz(CzBzEnum.U.getCzBz());
        saveParam.setSpdj(String.valueOf(level-1));
        saveParam.setUpdateBy(loginUser.getName());
        saveParam.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseReviewFactoryService.saveOrUpdateStock(saveParam);
        // 申报下发库软件
        gradedEnterpriseReviewSaveParam = gradedEnterpriseReviewService.getGradedEnterpriseReviewSaveParam(param.getQyid());
        return gradedEnterpriseReviewService.gradedEnterpriseHttpServiceSave(gradedEnterpriseReviewSaveParam);
    }

    @DataBindFieldConvert
    @Override
    public GradedEnterpriseStockVo detail(String id) {
        IGradedEnterpriseStockService gradedEnterpriseStockService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(gradedEnterpriseStockService.getById(id), GradedEnterpriseStockVo.class);
    }

    @DataBindService(strategy = DataBindGradedEnterpriseStock.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, gradedEnterpriseStockMapper);
    }

    
    @Override
    public String saveData(GradedEnterpriseStockSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseStock gradedEnterpriseStock = BeanUtils.copyByClass(param, GradedEnterpriseStock.class);
        gradedEnterpriseStock.setId(IdUtil.simpleUUID());
        gradedEnterpriseStock.setCreateBy(loginUser.getName());
        gradedEnterpriseStock.setUpdateBy(loginUser.getName());
        gradedEnterpriseStock.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseStock.setCreateTime(LocalDateTime.now());
        gradedEnterpriseStock.setCzbz(CzBzEnum.I.getCzBz());

        GradedEnterpriseStock unx = getByUnx(param.getQydm(),param.getKqdm());
        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在企业和库点的库点数据");

        super.save(gradedEnterpriseStock);
        param.setId(gradedEnterpriseStock.getId());
        return gradedEnterpriseStock.getId();
    }

    
    @Override
    public String updateData(GradedEnterpriseStockSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseStock gradedEnterpriseStock = BeanUtils.copyByClass(param, GradedEnterpriseStock.class);
        gradedEnterpriseStock.setCzbz(CzBzEnum.U.getCzBz());
        gradedEnterpriseStock.setUpdateBy(loginUser.getName());
        gradedEnterpriseStock.setUpdateTime(LocalDateTime.now());
        GradedEnterpriseStock unx = getByUnx(param.getQydm(),param.getKqdm());
        if(ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在企业和库点相同的库点数据");
        }
        super.updateById(gradedEnterpriseStock);
        param.setId(gradedEnterpriseStock.getId());
        return gradedEnterpriseStock.getId();
    }

    
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(GradedEnterpriseStock::getId, ids)
                .set(GradedEnterpriseStock::getCzbz, CzBzEnum.D.getCzBz())
                .set(GradedEnterpriseStock::getUpdateBy, loginUser.getName())
                .set(GradedEnterpriseStock::getUpdateTime, LocalDateTime.now())
                .update();
    }

    @Override
    public GradedEnterpriseStock getByUnx(String qydm,String kqdm) {
        return super.lambdaQuery()
                .select(GradedEnterpriseStock::getId)
                .eq(GradedEnterpriseStock::getQydm, qydm)
                .eq(GradedEnterpriseStock::getKqdm, kqdm)
                .ne(GradedEnterpriseStock::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseStock::getUpdateTime)
                .oneOpt()
                .map(GradedEnterpriseStock::getId)
                .map(this::getById)
                .orElse(null);
    }

    @Override
    public GradedEnterpriseStock getReduceByUnx(String qydm,String kqdm) {
        return super.lambdaQuery()
                .select(GradedEnterpriseStock::getId)
                .eq(GradedEnterpriseStock::getQydm, qydm)
                .eq(GradedEnterpriseStock::getKqdm, kqdm)
                .eq(GradedEnterpriseStock::getSpzt, GradeAwardingEnum.PASS.getState())
                .ne(GradedEnterpriseStock::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseStock::getUpdateTime)
                .oneOpt()
                .map(GradedEnterpriseStock::getId)
                .map(this::getById)
                .orElse(null);
    }

    @Override
    public GradedEnterpriseStock getPickByUnx(String qydm,String kqdm) {
        return super.lambdaQuery()
                .select(GradedEnterpriseStock::getId)
                .eq(GradedEnterpriseStock::getQydm, qydm)
                .eq(GradedEnterpriseStock::getKqdm, kqdm)
                .le(GradedEnterpriseStock::getUpdateTime,LocalDateTime.now())
                .gt(GradedEnterpriseStock::getUpdateTime,LocalDateTime.now().plusYears(-2))
                .eq(GradedEnterpriseStock::getSpzt, GradeAwardingEnum.NO_PASS.getState())
                .ne(GradedEnterpriseStock::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseStock::getUpdateTime)
                .oneOpt()
                .map(GradedEnterpriseStock::getId)
                .map(this::getById)
                .orElse(null);
    }

}