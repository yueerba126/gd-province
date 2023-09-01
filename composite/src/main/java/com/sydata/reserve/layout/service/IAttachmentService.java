package com.sydata.reserve.layout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.reserve.layout.domain.Attachment;
import com.sydata.reserve.layout.param.AttachmentPageParam;
import com.sydata.reserve.layout.param.AttachmentSaveParam;
import com.sydata.reserve.layout.param.AttachmentUpdateParam;
import com.sydata.reserve.layout.vo.AttachmentVo;


/**
 * 储备布局地理信息-附件文件Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface IAttachmentService extends IService<Attachment> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<AttachmentVo> pages(AttachmentPageParam pageParam);

    Boolean save(AttachmentSaveParam attachmentSaveParam);

    Boolean update(AttachmentUpdateParam attachmentUpdateParam);

}