package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Company;
import com.sydata.basis.dto.CompanyQualityCheckDto;
import com.sydata.basis.mapper.CompanyMapper;
import com.sydata.basis.param.CompanyPageParam;
import com.sydata.basis.service.ICompanyService;
import com.sydata.basis.vo.CompanyVo;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.basis.CompanyApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.collect.quality.DataQualityProcessContext;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.param.basis.CompanyReportParam;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import static com.sydata.collect.api.enums.DataApiEnum.COMPANY;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * 基础数据-企业 Service业务层处理
 *
 * @author lzq
 * @date 2022-08-19
 */
@Service("companyService")
@CacheConfig(cacheNames = CompanyServiceImpl.CACHE_NAME)
@RequiredArgsConstructor
public class CompanyServiceImpl extends BaseDataImpl<CompanyApiParam, CompanyMapper, Company, CompanyReportParam>
        implements ICompanyService {

    private final CompanyMapper companyMapper;

    final static String CACHE_NAME = "basis:company";

    @Cacheable(key = "'id='+#id")
    @Override
    public Company getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean save(Company entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Company entity) {
        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean removeById(Company entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<CompanyVo> pages(CompanyPageParam pageParam) {
        Page<Company> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Company::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), Company::getEnterpriseId, pageParam.getEnterpriseId())
                .likeRight(isNotEmpty(pageParam.getDwdm()), Company::getDwdm, pageParam.getDwdm())
                .likeRight(isNotEmpty(pageParam.getDwmc()), Company::getDwmc, pageParam.getDwmc())
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), Company::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), Company::getUpdateTime, pageParam.getEndUpdateTime())
                .orderByDesc(Company::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, CompanyVo.class);
    }

    @DataBindFieldConvert
    @Override
    public CompanyVo detail(String id) {
        ICompanyService companyService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(companyService.getById(id), CompanyVo.class);
    }

    @DataBindService(strategy = DataBindCompany.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, companyMapper);
    }

    @Override
    public DataApiEnum api() {
        return COMPANY;
    }

    @Override
    public void validated(DataIssueDto dataIssueDto, CompanyApiParam param) {
        // 校验上级单位代码不能和单位代码相等
        dataIssueDto.append(!Objects.equals(param.getDwdm(), param.getSjdwdm()), CompanyApiParam::getSjdwdm,
                "上级单位代码不能和单位代码相等");

        // 如果填写了上级单位代码,校验填写的上级单位名称对应实际上级单位的名称
        boolean isSjdwdmName = isBlank(param.getSjdwdmName()) || param.getSjdwdmName().equals(param.getSjdwmc());
        dataIssueDto.append(isSjdwdmName, CompanyApiParam::getSjdwdmName,
                "填写的上级单位名称与实际上级单位名称不符,实际单位名称为:" + param.getSjdwdmName());

        boolean ccywlx = param.getCcywlx().split(COMMA).length == param.getCcywlxName().split(COMMA).length;
        dataIssueDto.append(ccywlx, CompanyApiParam::getCcywlx, "仓储业务类型中存在非标准代码");

        boolean ccpz = param.getCcpz().split(COMMA).length == param.getCcpzName().split(COMMA).length;
        dataIssueDto.append(ccpz, CompanyApiParam::getCcpz, "仓储品种中存在非标准代码");

        // 仓房数与油罐数不能都小于1
        boolean cfs = param.getCfs() > 0 || param.getYgs() > 0;
        dataIssueDto.append(cfs, CompanyApiParam::getCfs, "仓房数与油罐数不能同时为0");
        dataIssueDto.append(cfs, CompanyApiParam::getYgs, "仓房数与油罐数不能同时为0");
    }

    @Override
    public void dataQualityCustomCheck(DataQualityProcessContext context) {
        // 数据质量校验：是否上传了库区信息、所属库区数与上传库区数是否一致、所属仓房数与上传仓房数是否一致、所属油罐数与上传油罐数是否一致
        List<CompanyQualityCheckDto> checks = companyMapper.qualityCheck(context.getDataIds());
        checks.forEach(check -> {
            DataIssueDto dataIssue = context.issueRecord(check.getId());

            dataIssue.append(check.getSckqs() > 0, CompanyApiParam::getKqs, "该单位未上传库区信息");

            dataIssue.append(check.getKqs() == check.getSckqs(),
                    CompanyApiParam::getKqs, "所属库区数与上传库区数不一致");

            dataIssue.append(check.getCfs() == check.getSccfs(),
                    CompanyApiParam::getCfs, "所属仓房数与上传仓房数不一致");

            dataIssue.append(check.getYgs() == check.getScygs(),
                    CompanyApiParam::getYgs, "所属油罐数与上传油罐数不一致");
        });
    }
}
