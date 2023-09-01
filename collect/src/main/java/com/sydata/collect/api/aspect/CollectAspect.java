package com.sydata.collect.api.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sydata.collect.api.accept.CollectAccept;
import com.sydata.collect.api.annotation.DataApi;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.valid.BaseValid;
import com.sydata.collect.domain.RequestLog;
import com.sydata.collect.service.IRequestLogService;
import com.sydata.framework.core.global.WebResult;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.sydata.framework.util.StringUtils.isEmpty;
import static com.sydata.organize.enums.LoginDeviceEnum.API;
import static com.sydata.organize.handler.OrganizeMetaObjectHandler.getFieldVal;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author lzq
 * @description 数据收集AOP拦截
 * @date 2022/10/20 17:30
 */
@Aspect
@Component
@Slf4j
public class CollectAspect {


    @Resource
    private CollectAccept collectAccept;

    private ObjectMapper objectMapper;

    private Map<String, IDataOwnerCheck> organizeKindMap;

    public CollectAspect(MappingJackson2HttpMessageConverter messageConverter, List<IDataOwnerCheck> dataOwnerChecks) {
        this.objectMapper = messageConverter.getObjectMapper();
        this.organizeKindMap = StreamEx.of(dataOwnerChecks).toMap(impl -> impl.type().getKind(), Function.identity());
    }

    @Resource
    private IRequestLogService requestLogService;

    @Around(value = "@annotation(dataApi)")
    public Object dataApiAround(ProceedingJoinPoint joinPoint, DataApi dataApi) throws Throwable {
        if (joinPoint.getSignature().getDeclaringType().isInterface()) {
            return joinPoint.proceed();
        }

        LocalDateTime beginTime = LocalDateTime.now();
        BaseApiParam apiParam = (BaseApiParam) joinPoint.getArgs()[0];
        DataApiEnum apiEnum = dataApi.value();

        LoginUser login = UserSecurity.loginUser();

        Boolean state = TRUE;
        WebResult webResult = WebResult.SUCCESS;
        try {
            // 执行目标方法
            BaseValid.set(apiParam);
            Object result = joinPoint.proceed();

            // 如果登录设备为api时需要校验数据归属权限
            if (API.getDevice().equals(login.getLoginDevice())) {
                organizeKindMap.get(login.getOrganizeKind()).checkDataOwner(apiParam, login);
            }

            // 将数据交由接收器处理
            DataBindHandleBootstrap.dataHandConvert(apiParam);
            collectAccept.accept(beginTime, apiEnum, dataApi.isDataHandle(), apiParam);
            return result;
        } catch (Throwable e) {
            state = FALSE;
            webResult = WebResult.error(e.getMessage());
            throw e;
        } finally {
            BaseValid.remove();

            String stockHouseId = getFieldVal(login, LoginUser::getStockHouseId);
            if (isEmpty(stockHouseId)) {
                stockHouseId = apiParam.getBuildStockHouseId();
            }


            // 记录请求日志（插入缓冲区异步处理）
            LocalDateTime endTime = LocalDateTime.now();
            RequestLog requestLog = new RequestLog()
                    .setApiCode(apiEnum.getApiCode())
                    .setDataId(apiParam.getBuildId())
                    .setCzbz(apiParam.getCzbz())
                    .setBeginTime(beginTime)
                    .setEndTime(endTime)
                    .setTimeConsuming(Duration.between(beginTime, endTime).toMillis())
                    .setParam(objectMapper.writeValueAsString(apiParam))
                    .setResult(objectMapper.writeValueAsString(webResult))
                    .setState(state)
                    .setRegionId(getFieldVal(login, LoginUser::getRegionId))
                    .setCountryId(getFieldVal(login, LoginUser::getCountryId))
                    .setProvinceId(getFieldVal(login, LoginUser::getProvinceId))
                    .setCityId(getFieldVal(login, LoginUser::getCityId))
                    .setAreaId(getFieldVal(login, LoginUser::getAreaId))
                    .setEnterpriseId(getFieldVal(login, LoginUser::getOrganizeId))
                    .setStockHouseId(stockHouseId)
                    .setCreateBy(login.getName());
            requestLogService.saveByBuffer(requestLog);
        }
    }
}
