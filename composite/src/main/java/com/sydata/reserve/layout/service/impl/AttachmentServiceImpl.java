package com.sydata.reserve.layout.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.reserve.layout.domain.Attachment;
import com.sydata.reserve.layout.mapper.AttachmentMapper;
import com.sydata.reserve.layout.param.AttachmentPageParam;
import com.sydata.reserve.layout.param.AttachmentSaveParam;
import com.sydata.reserve.layout.param.AttachmentUpdateParam;
import com.sydata.reserve.layout.service.IAttachmentService;
import com.sydata.reserve.layout.vo.AttachmentVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备布局地理信息-附件文件Service业务层处理
 *
 * @author lzq
 * @date 2022-07-08
 */
@CacheConfig(cacheNames = AttachmentServiceImpl.CACHE_NAME)
@Service("attachmentService")
public class AttachmentServiceImpl
        extends ServiceImpl<AttachmentMapper, Attachment>
        implements IAttachmentService {

    final static String CACHE_NAME = "reserveLayout:Attachment";


    @Override
    public boolean removeById(Attachment entity) {
        return super.removeById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<AttachmentVo> pages(AttachmentPageParam pageParam) {
        Page<Attachment> page = super.lambdaQuery()
                .like(isNotEmpty(pageParam.getAttachmentName()), Attachment::getAttachmentName, pageParam.getAttachmentName())
                .eq(isNotEmpty(pageParam.getDataId()), Attachment::getDataId, pageParam.getDataId())
                .eq(isNotEmpty(pageParam.getDwdm()), Attachment::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getFileStorageId()), Attachment::getFileStorageId, pageParam.getFileStorageId())
                .orderByDesc(Attachment::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, AttachmentVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean save(AttachmentSaveParam AttachmentSaveParam) {
        Attachment Attachment = BeanUtils.copyByClass(AttachmentSaveParam, Attachment.class);
        return super.save(Attachment.setId(String.valueOf((IdUtil.getSnowflakeNextId()))));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(AttachmentUpdateParam AttachmentUpdateParam) {
        Attachment attachment = this.getById(AttachmentUpdateParam.getId());
        Assert.notNull(attachment, "储备布局地理信息-附件文件不存在");
        return super.updateById(BeanUtils.copyByClass(AttachmentUpdateParam, Attachment.class));
    }


}