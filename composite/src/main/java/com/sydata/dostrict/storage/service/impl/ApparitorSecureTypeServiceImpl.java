/**
 * @filename:ApparitorSecureTypeBeanServiceImpl 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2018 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.common.api.enums.YesNo;
import com.sydata.common.composite.annotation.DataBindZdglType;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.personnel.param.ApparitorFileTypeParam;
import com.sydata.dostrict.storage.domain.ApparitorSecureType;
import com.sydata.dostrict.storage.mapper.ApparitorSecureTypeMapper;
import com.sydata.dostrict.storage.param.ApparitorSecureTypePageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureTypeSaveParam;
import com.sydata.dostrict.storage.param.ApparitorSecureTypeSelectParam;
import com.sydata.dostrict.storage.service.IApparitorSecureTypeService;
import com.sydata.dostrict.storage.vo.ApparitorSecureTypeVo;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.ZERO;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**   
 * @Description:TODO(安全仓储-安全生产-制度类别服务实现)
 *
 * @version: V1.0
 * @author: lzq
 * 
 */
@CacheConfig(cacheNames = ApparitorSecureTypeServiceImpl.CACHE_NAME)
@Service
public class ApparitorSecureTypeServiceImpl
        extends ServiceImpl<ApparitorSecureTypeMapper, ApparitorSecureType>
        implements IApparitorSecureTypeService {

    @Resource
    private ApparitorSecureTypeMapper apparitorSecureTypeMapper;

    final static String CACHE_NAME = "composite:apparitorSecureType";

    @Override
    @DataBindFieldConvert
    public Page<ApparitorSecureTypeVo> pages(ApparitorSecureTypePageParam pageParam) {
        Page<ApparitorSecureType> page = super.lambdaQuery()
                .like(isNotEmpty(pageParam.getTypeName()), ApparitorSecureType::getTypeName, pageParam.getTypeName())
                .ne(ApparitorSecureType::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorSecureType::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorSecureTypeVo.class);
    }

    @Override
    @DataBindFieldConvert
    public List<ApparitorSecureTypeVo> lists() {
        List<ApparitorSecureType> apparitorSecureTypeList = super.lambdaQuery()
                .ne(ApparitorSecureType::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorSecureType::getUpdateTime).list();
        return BeanUtils.copyToList(apparitorSecureTypeList, ApparitorSecureTypeVo.class);
    }

    @Override
    public List<KuNode> treeList() {
        ApparitorSecureTypeSelectParam param = new ApparitorSecureTypeSelectParam();
        param.setIsShowNum(param.getIsShowNum() == null ? 1 : param.getIsShowNum());
        List<KuNode> kuNodeList = apparitorSecureTypeMapper.selectFileTypeTreeList(param);
        return TreeUtils.toTree(kuNodeList, KuNode::getKey, KuNode::getParentKey, KuNode::setChildren, ZERO);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorSecureTypeVo detail(String id) {
        return BeanUtils.copyByClass(getById(id), ApparitorSecureTypeVo.class);
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorSecureTypeSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorSecureType exitEntity = super.lambdaQuery()
                .eq(ApparitorSecureType::getTypeCode, param.getTypeCode())
                .eq(ApparitorSecureType::getTypeName, param.getTypeName())
                .one();
        Assert.isNull(exitEntity, "分类编码+分类名称组合已存在，请勿重复新增");
        ApparitorSecureType apparitorSecureType = BeanUtils.copyByClass(param, ApparitorSecureType.class);
        param.setIsEnable(YesNo.YES.getCode());
        if (Objects.isNull(param.getParentId())) {
            param.setParentId(ZERO) ;
        }
        apparitorSecureType.setId(IdUtil.simpleUUID());
        apparitorSecureType.setCzbz(CzBzEnum.I.getCzBz());
        apparitorSecureType.setCreateBy(loginUser.getName());
        apparitorSecureType.setUpdateBy(loginUser.getName());
        apparitorSecureType.setUpdateTime(LocalDateTime.now());
        apparitorSecureType.setCreateTime(LocalDateTime.now());
        apparitorSecureType.setZhgxsj(LocalDateTime.now());
        super.save(apparitorSecureType);
        param.setId(apparitorSecureType.getParentId());
        return apparitorSecureType.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorSecureTypeSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorSecureType exitEntity = super.lambdaQuery()
                .eq(ApparitorSecureType::getTypeCode, param.getTypeCode())
                .eq(ApparitorSecureType::getTypeName, param.getTypeName())
                .one();
        if(exitEntity!=null&&!StringUtils.equals(exitEntity.getId(),param.getId())){
            Assert.isNull(exitEntity, "分类编码+分类名称组合已存在，请修改分类编码+分类名称组合");
        }
        ApparitorSecureType apparitorSecureType = BeanUtils.copyByClass(param, ApparitorSecureType.class);
        apparitorSecureType.setCzbz(CzBzEnum.U.getCzBz());
        apparitorSecureType.setUpdateBy(loginUser.getName());
        apparitorSecureType.setUpdateTime(LocalDateTime.now());
        apparitorSecureType.setZhgxsj(LocalDateTime.now());
        super.updateById(apparitorSecureType);
        param.setId(apparitorSecureType.getId());
        return apparitorSecureType.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorSecureType::getId, ids)
                .set(ApparitorSecureType::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorSecureType::getUpdateBy ,loginUser.getName())
                .set(ApparitorSecureType::getUpdateTime ,LocalDateTime.now())
                .update();
    }
    
    @DataBindService(strategy = DataBindZdglType.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, apparitorSecureTypeMapper);
    }
}