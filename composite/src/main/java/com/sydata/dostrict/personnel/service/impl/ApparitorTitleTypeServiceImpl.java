package com.sydata.dostrict.personnel.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.YesNo;
import com.sydata.common.composite.annotation.DataBindTitleType;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.personnel.domain.ApparitorTitleType;
import com.sydata.dostrict.personnel.mapper.ApparitorTitleTypeMapper;
import com.sydata.dostrict.personnel.param.ApparitorTitleTypePageParam;
import com.sydata.dostrict.personnel.param.ApparitorTitleTypeParam;
import com.sydata.dostrict.personnel.service.IApparitorTitleTypeService;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.ZERO;

/**
 * 行政管理-称号类别管理Service业务层处理
 *
 * @author fuql
 * @date 2023-04-25
 */
@CacheConfig(cacheNames = ApparitorTitleTypeServiceImpl.CACHE_NAME)
@Service("apparitorTitleTypeService")
public class ApparitorTitleTypeServiceImpl extends ServiceImpl<ApparitorTitleTypeMapper, ApparitorTitleType> implements IApparitorTitleTypeService {

    @Resource
    private ApparitorTitleTypeMapper apparitorTitleTypeMapper;

    final static String CACHE_NAME = "composite:apparitorTitleType";

    @DataBindFieldConvert
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    @Override
    public Page<ApparitorTitleType> pageData(ApparitorTitleTypePageParam param) {
        return super.lambdaQuery()
                .eq(Objects.nonNull(param.getTypeId()), ApparitorTitleType::getId, param.getTypeId())
                .like(Objects.nonNull(param.getTypeName()), ApparitorTitleType::getTypeName, param.getTypeName())
                .eq(Objects.nonNull(param.getParentId()), ApparitorTitleType::getParentId, param.getParentId())
                .likeRight(Objects.nonNull(param.getAllParentId()), ApparitorTitleType::getAllParentId, param.getAllParentId())
                .eq(Objects.nonNull(param.getIsLeafNode()), ApparitorTitleType::getIsLeafNode, param.getIsLeafNode())
                .eq(Objects.nonNull(param.getIsEnable()), ApparitorTitleType::getIsEnable, param.getIsEnable())
                .eq(ApparitorTitleType::getDelFlag, YesNo.NO.getCode())
                .page(new Page<>(param.getPageNum(), param.getPageSize()));
    }

    @Override
    public List<KuNode> treeList() {
        ApparitorTitleTypeParam param = new ApparitorTitleTypeParam();
        param.setIsShowNum(param.getIsShowNum() == null ? 1 : param.getIsShowNum());
        List<KuNode> fileTypes = apparitorTitleTypeMapper.selectFileTypeTreeList(param);
        return TreeUtils.toTree(fileTypes, KuNode::getKey, KuNode::getParentKey, KuNode::setChildren, ZERO);
    }

    @Override
    public String insertTitleType(ApparitorTitleType param) {
        param.setCreateTime(LocalDateTime.now());
        param.setUpdateTime(LocalDateTime.now());
        param.setIsEnable(YesNo.YES.getCode());
        param.setDelFlag(YesNo.NO.getCode());
        if (Objects.isNull(param.getParentId())) {
            param.setParentId(ZERO) ;
        }
        this.checkFileType(param);
        super.save(param);
        param.setAllParentId(String.format("%s%s,", StringUtils.defaultString(param.getAllParentId(), ""), param.getId()));
        super.updateById(param);
        return param.getId();
    }

    private void checkFileType(ApparitorTitleType param) {
        List<ApparitorTitleType> list = super.lambdaQuery()
                .eq(ApparitorTitleType::getDelFlag, YesNo.NO.getCode())
                .ne(!Objects.isNull(param.getId()), ApparitorTitleType::getId, param.getId())
                .eq(ApparitorTitleType::getTypeName, param.getTypeName())
                .eq(ApparitorTitleType::getParentId, param.getParentId())
                .list();
        Assert.state(CollectionUtils.isEmpty(list), "文件类别已存在，请检查");
    }

    @DataBindService(strategy = DataBindTitleType.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, apparitorTitleTypeMapper);
    }

    @Override
    public String updateTitleType(ApparitorTitleType param) {
        LoginUser loginUser = UserSecurity.loginUser();
        param.setStockHouseId(loginUser.getStockHouseId());
        param.setCreateBy(loginUser.getName());
        param.setUpdateTime(LocalDateTime.now());
        if (Objects.isNull(param.getParentId())) {
            param.setParentId(ZERO) ;
        }
        this.checkFileType(param);
        param.setAllParentId(String.format("%s%s,", StringUtils.defaultString(param.getAllParentId(), ""), param.getId()));
        super.updateById(param);
        return param.getId();
    }

    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorTitleType::getId, ids)
                .set(ApparitorTitleType::getDelFlag, YesNo.YES.getCode())
                .set(ApparitorTitleType::getUpdateBy, loginUser.getName())
                .set(ApparitorTitleType::getUpdateTime, LocalDateTime.now())
                .update();
    }
}
