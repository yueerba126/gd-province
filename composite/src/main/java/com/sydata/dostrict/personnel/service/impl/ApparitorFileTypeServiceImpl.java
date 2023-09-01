package com.sydata.dostrict.personnel.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.YesNo;
import com.sydata.common.composite.annotation.DataBindFileType;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.personnel.domain.ApparitorFileType;
import com.sydata.dostrict.personnel.mapper.ApparitorFileTypeMapper;
import com.sydata.dostrict.personnel.param.ApparitorFileTypePageParam;
import com.sydata.dostrict.personnel.param.ApparitorFileTypeParam;
import com.sydata.dostrict.personnel.service.IApparitorFileTypeService;
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
 * 行政管理-文件类别管理Service业务层处理
 *
 * @author fuql
 * @date 2023-04-24
 */
@CacheConfig(cacheNames = ApparitorFileTypeServiceImpl.CACHE_NAME)
@Service("apparitorFileTypeService")
public class ApparitorFileTypeServiceImpl extends ServiceImpl<ApparitorFileTypeMapper, ApparitorFileType> implements IApparitorFileTypeService {

    @Resource
    private ApparitorFileTypeMapper apparitorFileTypeMapper;

    final static String CACHE_NAME = "composite:apparitorFileType";


    @DataBindFieldConvert
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    @Override
    public Page<ApparitorFileType> pageData(ApparitorFileTypePageParam param) {
        return super.lambdaQuery()
                .eq(Objects.nonNull(param.getTypeId()), ApparitorFileType::getId, param.getTypeId())
                .like(Objects.nonNull(param.getTypeName()), ApparitorFileType::getTypeName, param.getTypeName())
                .eq(Objects.nonNull(param.getParentId()), ApparitorFileType::getParentId, param.getParentId())
                .likeRight(Objects.nonNull(param.getAllParentId()), ApparitorFileType::getAllParentId, param.getAllParentId())
                .eq(Objects.nonNull(param.getIsLeafNode()), ApparitorFileType::getIsLeafNode, param.getIsLeafNode())
                .eq(Objects.nonNull(param.getIsEnable()), ApparitorFileType::getIsEnable, param.getIsEnable())
                .eq(ApparitorFileType::getDelFlag, YesNo.NO.getCode())
                .page(new Page<>(param.getPageNum(), param.getPageSize()));
    }

    @Override
    public List<KuNode> treeList() {
        ApparitorFileTypeParam param = new ApparitorFileTypeParam();
        param.setIsShowNum(param.getIsShowNum() == null ? 1 : param.getIsShowNum());
        List<KuNode> fileTypes = apparitorFileTypeMapper.selectFileTypeTreeList(param);
        return TreeUtils.toTree(fileTypes, KuNode::getKey, KuNode::getParentKey, KuNode::setChildren, ZERO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insertFileType(ApparitorFileType param) {
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

    @DataBindService(strategy = DataBindFileType.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, apparitorFileTypeMapper);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateFileType(ApparitorFileType param) {
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

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorFileType::getId, ids)
                .set(ApparitorFileType::getDelFlag, YesNo.YES.getCode())
                .set(ApparitorFileType::getUpdateBy, loginUser.getName())
                .set(ApparitorFileType::getUpdateTime, LocalDateTime.now())
                .update();
    }

    private void checkFileType(ApparitorFileType param) {
        List<ApparitorFileType> list = super.lambdaQuery()
                .eq(ApparitorFileType::getDelFlag, YesNo.NO.getCode())
                .ne(!Objects.isNull(param.getId()), ApparitorFileType::getId, param.getId())
                .eq(ApparitorFileType::getTypeName, param.getTypeName())
                .eq(ApparitorFileType::getParentId, param.getParentId())
                .list();
        Assert.state(CollectionUtils.isEmpty(list), "文件类别已存在，请检查");
    }

}
