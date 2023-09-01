package com.sydata.warn.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.admin.domain.ReserveScale;
import com.sydata.admin.vo.ReserveScaleVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.warn.domain.WarnFoodstuffMessage;
import com.sydata.warn.dto.WarnFoodstuffMessageDto;
import com.sydata.warn.enums.HandleStatusContentEnum;
import com.sydata.warn.enums.WarnLevelContentEnum;
import com.sydata.warn.enums.WarnTypeContentEnum;
import com.sydata.warn.mapper.WarnFoodstuffMessageMapper;
import com.sydata.warn.param.WarnFoodstuffMessagePageParam;
import com.sydata.warn.param.WarnFoodstuffMessageUpdateParam;
import com.sydata.warn.service.IWarnFoodstuffMessageService;
import com.sydata.warn.vo.WarnFoodstuffMessageVo;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 库存数量-粮食库存异常告警Service业务层处理
 *
 * @author fuql
 * @date 2023-04-28
 */
@Service("warnFoodstuffMessageService")
public class WarnFoodstuffMessageServiceImpl extends ServiceImpl<WarnFoodstuffMessageMapper, WarnFoodstuffMessage> implements IWarnFoodstuffMessageService {

    @Resource
    private WarnFoodstuffMessageMapper warnFoodstuffMessageMapper;

    private static BigDecimal ONE_THOUSAND = new BigDecimal("10000");
    private static BigDecimal TEN_MILLION = new BigDecimal("10000000");

    @DataBindFieldConvert
    @Override
    public Page<WarnFoodstuffMessageVo> pageData(WarnFoodstuffMessagePageParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        Page<WarnFoodstuffMessage> page = super.lambdaQuery()
                .ge(isNotEmpty(param.getStartWarnDate()), WarnFoodstuffMessage::getWarnDate, param.getStartWarnDate())
                .le(isNotEmpty(param.getEndWarnDate()), WarnFoodstuffMessage::getWarnDate, param.getEndWarnDate())
                .ge(isNotEmpty(param.getStartHandleDate()), WarnFoodstuffMessage::getHandleDate, param.getStartHandleDate())
                .le(isNotEmpty(param.getEndHandleDate()), WarnFoodstuffMessage::getHandleDate, param.getEndHandleDate())
                .eq(isNotEmpty(param.getWarnLevel()), WarnFoodstuffMessage::getWarnLevel, param.getWarnLevel())
                .eq(isNotEmpty(param.getWarnType()), WarnFoodstuffMessage::getWarnType, param.getWarnType())
                .eq(Objects.nonNull(loginUser.getCountryId()), WarnFoodstuffMessage::getCountryId, loginUser.getCountryId())
                .eq(Objects.nonNull(loginUser.getProvinceId()), WarnFoodstuffMessage::getProvinceId, loginUser.getProvinceId())
                .eq(Objects.nonNull(loginUser.getCityId()), WarnFoodstuffMessage::getCityId, loginUser.getCityId())
                .eq(Objects.nonNull(loginUser.getAreaId()), WarnFoodstuffMessage::getAreaId, loginUser.getAreaId())
                .orderByDesc(WarnFoodstuffMessage::getUpdateTime)
                .page(new Page<>(param.getPageNum(), param.getPageSize()));
        return BeanUtils.copyToPage(page, WarnFoodstuffMessageVo.class);
    }

    @Override
    public String updateData(WarnFoodstuffMessageUpdateParam param) {
        WarnFoodstuffMessage message = super.getById(param.getId());
        Assert.state(Objects.nonNull(message), "未查询到相应的数据，请重新选择！");
        message.setHandleStatus(param.getHandleStatus())
                .setHandleDate(param.getHandleDate())
                .setHandlePeople(param.getHandlePeople())
                .setRemark(param.getRemark());
        super.updateById(message);
        return param.getId();
    }

    @DataBindFieldConvert
    @Override
    public WarnFoodstuffMessageVo getDataById(Long id) {
        return BeanUtils.copyByClass(getById(id), WarnFoodstuffMessageVo.class);
    }

    @Override
    public boolean execute() {
        LoginUser loginUser = UserSecurity.loginUser();
        List<ReserveScale> scaleList = warnFoodstuffMessageMapper.selectReserveScaleNoPermissionByParam(loginUser);
        List<ReserveScaleVo> scales = BeanUtils.copyToList(scaleList, ReserveScaleVo.class);
        if (CollectionUtils.isEmpty(scales)) {
            return Boolean.TRUE;
        }
        DataBindHandleBootstrap.dataHandConvert(scales);
        List<WarnFoodstuffMessage> messages = new ArrayList<>();
        //查询日库存数据
        for (ReserveScaleVo sc : scales) {
            String warnContent = "";
            WarnFoodstuffMessageDto dtos = warnFoodstuffMessageMapper.selectStockAffiliation(sc);
            String msg = WarnTypeContentEnum.INVENTORY_WARN.getMsg();
            LocalDateTime now = LocalDateTime.now();
            BigDecimal multiply = Objects.isNull(sc.getYlcbgmjhsl()) ? BigDecimal.ZERO : sc.getYlcbgmjhsl().divide(ONE_THOUSAND, 2, BigDecimal.ROUND_CEILING);
            if (Objects.nonNull(dtos)) {
                BigDecimal totalQty = Objects.isNull(dtos.getTotalQty()) ? BigDecimal.ZERO : dtos.getTotalQty().divide(TEN_MILLION, 2, BigDecimal.ROUND_CEILING);
                warnContent = String.format(msg, sc.getYlpzName(), multiply, totalQty, totalQty);
            } else {
                warnContent = String.format(msg, sc.getYlpzName(), multiply, BigDecimal.ZERO, BigDecimal.ZERO);
            }
            if (Objects.isNull(dtos) || sc.getYlcbgmjhsl().compareTo(dtos.getTotalQty()) < 0) {
                WarnFoodstuffMessage baseFiledEntity = new WarnFoodstuffMessage();
                baseFiledEntity.setDwdm(sc.getCcqy())
                        .setWarnLevel(WarnLevelContentEnum.ONE.getKey())
                        .setWarnType(WarnTypeContentEnum.INVENTORY_WARN.getKey())
                        .setWarnDate(now)
                        .setWarnContent(warnContent)
                        .setHandleStatus(HandleStatusContentEnum.ONE.getKey())
                        .setCreateTime(now)
                        .setUpdateTime(now)
                        .setProvinceId(sc.getProvinceId())
                        .setCityId(sc.getCityId())
                        .setAreaId(sc.getAreaId())
                        .setCountryId(sc.getCountryId());
                messages.add(baseFiledEntity);
            }
        }
        if (!CollectionUtils.isEmpty(messages)) {
            super.saveBatch(messages);
        }
        return Boolean.TRUE;
    }

    @XxlJob("reserveScaleNoPermission")
    public void reserveScaleNoPermission() {
        List<ReserveScale> scaleList = warnFoodstuffMessageMapper.selectReserveScaleNoPermission();
        List<ReserveScaleVo> scales = BeanUtils.copyToList(scaleList, ReserveScaleVo.class);
        if (CollectionUtils.isEmpty(scales)) {
            return;
        }
        DataBindHandleBootstrap.dataHandConvert(scales);
        List<WarnFoodstuffMessage> messages = new ArrayList<>();
        //查询日库存数据
        for (ReserveScaleVo sc : scales) {
            String warnContent = "";
            WarnFoodstuffMessageDto dtos = warnFoodstuffMessageMapper.selectStockAffiliation(sc);
            String msg = WarnTypeContentEnum.INVENTORY_WARN.getMsg();
            LocalDateTime now = LocalDateTime.now();
            BigDecimal multiply = Objects.isNull(sc.getYlcbgmjhsl()) ? BigDecimal.ZERO : sc.getYlcbgmjhsl().divide(ONE_THOUSAND, 2, BigDecimal.ROUND_CEILING);
            if (Objects.nonNull(dtos)) {
                BigDecimal totalQty = Objects.isNull(dtos.getTotalQty()) ? BigDecimal.ZERO : dtos.getTotalQty().divide(TEN_MILLION, 2, BigDecimal.ROUND_CEILING);
                warnContent = String.format(msg, sc.getYlpzName(),multiply, totalQty, totalQty);
            } else {
                warnContent = String.format(msg, sc.getYlpzName(),multiply, BigDecimal.ZERO, BigDecimal.ZERO);
            }
            if (Objects.isNull(dtos) || sc.getYlcbgmjhsl().compareTo(dtos.getTotalQty()) < 0) {
                WarnFoodstuffMessage baseFiledEntity = new WarnFoodstuffMessage();
                baseFiledEntity.setDwdm(sc.getCcqy())
                        .setWarnLevel(WarnLevelContentEnum.ONE.getKey())
                        .setWarnType(WarnTypeContentEnum.INVENTORY_WARN.getKey())
                        .setWarnDate(now)
                        .setWarnContent(warnContent)
                        .setHandleStatus(HandleStatusContentEnum.ONE.getKey())
                        .setCreateTime(now)
                        .setUpdateTime(now)
                        .setProvinceId(sc.getProvinceId())
                        .setCityId(sc.getCityId())
                        .setAreaId(sc.getAreaId())
                        .setCountryId(sc.getCountryId());
                messages.add(baseFiledEntity);
            }
        }
        if (!CollectionUtils.isEmpty(messages)) {
            super.saveBatch(messages);
        }
    }
}
