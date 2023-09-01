package com.sydata.filing.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.excel.ZtExcelBuildUtil;
import org.apache.commons.collections4.CollectionUtils;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.Lists;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.filing.domain.WarehousingFilingAttachment;
import com.sydata.filing.mapper.WarehousingFilingAttachmentMapper;
import com.sydata.filing.service.IWarehousingFilingAttachmentService;
import com.sydata.filing.service.IBusWarehousingFilingAttachmentService;
import com.sydata.filing.param.WarehousingFilingAttachmentPageParam;
import com.sydata.filing.param.WarehousingFilingAttachmentSaveParam;
import com.sydata.filing.vo.WarehousingFilingAttachmentVo;
import com.sydata.filing.vo.WarehousingFilingAttachmentExcelVo;
import com.sydata.filing.vo.FileVo;
import com.sydata.filing.annotation.DataBindWarehousingFilingAttachment;
import lombok.SneakyThrows;
import com.sydata.common.api.enums.CzBzEnum;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import com.sydata.common.file.service.IFileStorageService;

/**   
 * @Description:TODO(仓储备案-仓储附件服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("warehousingFilingAttachmentService")
public class WarehousingFilingAttachmentServiceImpl extends ServiceImpl<WarehousingFilingAttachmentMapper, WarehousingFilingAttachment> implements IWarehousingFilingAttachmentService  {

    final static String CACHE_NAME = "composite:warehousingFilingAttachment";

    @Resource
    private WarehousingFilingAttachmentMapper warehousingFilingAttachmentMapper;

    @Resource
    private IBusWarehousingFilingAttachmentService busWarehousingFilingAttachmentService;

    @Resource
    private IFileStorageService fileStorageService;

    @DataBindFieldConvert
    @Override
    public Page<WarehousingFilingAttachmentVo> pages(WarehousingFilingAttachmentPageParam pageParam) {
        Page<WarehousingFilingAttachment> page = super.lambdaQuery()
        .eq(ObjectUtils.isNotEmpty(pageParam.getId()),  WarehousingFilingAttachment::getId, pageParam.getId())
        .eq(ObjectUtils.isNotEmpty(pageParam.getCompanyId()),  WarehousingFilingAttachment::getCompanyId, pageParam.getCompanyId())
        .eq(ObjectUtils.isNotEmpty(pageParam.getFileName()),  WarehousingFilingAttachment::getFileName, pageParam.getFileName())
        .ne(WarehousingFilingAttachment::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingAttachment::getUpdateTime)
        .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));

        busWarehousingFilingAttachmentService.beforeReturnPage(page);

        return BeanUtils.copyToPage(page, WarehousingFilingAttachmentVo.class);
    }

    @DataBindFieldConvert
    @Override
    public WarehousingFilingAttachmentVo detail(String id) {
    IWarehousingFilingAttachmentService warehousingFilingAttachmentService = SpringUtil.getBean(this.getClass());
        WarehousingFilingAttachmentVo warehousingFilingAttachmentVo = BeanUtils.copyByClass(warehousingFilingAttachmentService.getById(id), WarehousingFilingAttachmentVo.class);

        busWarehousingFilingAttachmentService.beforeReturnDetail(warehousingFilingAttachmentVo);

        return warehousingFilingAttachmentVo;
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingAttachmentVo> detailList(List<String> ids) {
        List<WarehousingFilingAttachment> warehousingFilingAttachmentList =
         super.lambdaQuery()
        .in(WarehousingFilingAttachment::getId, ids)
        .ne(WarehousingFilingAttachment::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingAttachment::getUpdateTime).list();

        List<WarehousingFilingAttachmentVo> warehousingFilingAttachmentVoList = BeanUtils.copyToList(warehousingFilingAttachmentList, WarehousingFilingAttachmentVo.class);
        busWarehousingFilingAttachmentService.beforeReturnDetailList(warehousingFilingAttachmentVoList);

        return warehousingFilingAttachmentVoList;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(WarehousingFilingAttachmentSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingAttachment warehousingFilingAttachment = BeanUtils.copyByClass(param, WarehousingFilingAttachment.class);
        warehousingFilingAttachment.setId(IdUtil.simpleUUID());
        warehousingFilingAttachment.setCreateBy(loginUser.getName());
        warehousingFilingAttachment.setUpdateBy(loginUser.getName());
        warehousingFilingAttachment.setUpdateTime(LocalDateTime.now());
        warehousingFilingAttachment.setCreateTime(LocalDateTime.now());
        warehousingFilingAttachment.setCzbz(CzBzEnum.I.getCzBz());

        WarehousingFilingAttachment unx = getByUnx(param.getCompanyId(),param.getFileName());
        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在"+"仓储企业ID和"+"资料名称"+"相同的数据");

        busWarehousingFilingAttachmentService.beforeDoSave(warehousingFilingAttachment);

        super.save(warehousingFilingAttachment);
        param.setId(warehousingFilingAttachment.getId());
        return warehousingFilingAttachment.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(WarehousingFilingAttachmentSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingAttachment warehousingFilingAttachment = BeanUtils.copyByClass(param, WarehousingFilingAttachment.class);
        warehousingFilingAttachment.setCzbz(CzBzEnum.U.getCzBz());
        warehousingFilingAttachment.setUpdateBy(loginUser.getName());
        warehousingFilingAttachment.setUpdateTime(LocalDateTime.now());

        WarehousingFilingAttachment unx = getByUnx(param.getCompanyId(),param.getFileName());
        if (ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在"+"仓储企业ID和"+"资料名称"+"相同的数据");
        }

        busWarehousingFilingAttachmentService.beforeDoUpdate(warehousingFilingAttachment);

        super.updateById(warehousingFilingAttachment);
        param.setId(warehousingFilingAttachment.getId());
        return warehousingFilingAttachment.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();

        busWarehousingFilingAttachmentService.beforeDoRemove(ids);

        return super.lambdaUpdate()
        .in(WarehousingFilingAttachment::getId, ids)
        .set(WarehousingFilingAttachment::getCzbz , CzBzEnum.D.getCzBz())
        .set(WarehousingFilingAttachment::getUpdateBy ,loginUser.getName())
        .set(WarehousingFilingAttachment::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean saveOrUpdateBatchData(List<WarehousingFilingAttachmentSaveParam> params) {
        if(CollectionUtils.isNotEmpty(params)){
            for (int i = 0; i < params.size(); i++) {
                WarehousingFilingAttachmentSaveParam warehousingFilingAttachmentSaveParam = params.get(i);
                if(StringUtil.isNullOrEmpty(warehousingFilingAttachmentSaveParam.getId())){
                    saveData(warehousingFilingAttachmentSaveParam);
                }else{
                    updateData(warehousingFilingAttachmentSaveParam);
                }
            }
        }
        return true;
    }

    @Override
    public Boolean saveOrUpdateBatchDataByOtherSystem(List<WarehousingFilingAttachmentSaveParam> params) {
        Boolean returnStatus = true;
        List<WarehousingFilingAttachment> warehousingFilingAttachments = BeanUtils.copyToList(params, WarehousingFilingAttachment.class);
        if(CollectionUtils.isNotEmpty(warehousingFilingAttachments)){
            returnStatus = super.saveOrUpdateBatch(warehousingFilingAttachments);
        }
        return returnStatus;
    }

    @SneakyThrows
    @Override
    public void importData(MultipartFile file) {
        // 只支持xlsx或者xls的文件格式
        String fileType = FileUtil.extName(file.getOriginalFilename());
        boolean state = isState(fileType);
        Assert.state(state, "只支持xlsx或者xls的文件格式,系统检测该文件实际类型是" + fileType);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        List<WarehousingFilingAttachmentExcelVo> excelVoList = ExcelImportUtil.importExcel(file.getInputStream(), WarehousingFilingAttachmentExcelVo.class, params);
        Assert.state(CollectionUtils.isNotEmpty(excelVoList), "导入数据为空，请重新导入！");
        List<WarehousingFilingAttachmentSaveParam> saveList = BeanUtils.copyToList(excelVoList, WarehousingFilingAttachmentSaveParam.class);
        Assert.state(CollectionUtils.isNotEmpty(saveList), "导入数据为空，请重新导入！");
        for (int i = 0; i < saveList.size(); i++) {
            updateOrSaveByUnx(saveList.get(i));
        }
    }
                        
    @Override
    public void exportData(WarehousingFilingAttachmentPageParam pageParam) {
        List<WarehousingFilingAttachment> WarehousingFilingAttachmentList = super.lambdaQuery()
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()), WarehousingFilingAttachment::getId, pageParam.getId())
            .eq(CollectionUtils.isNotEmpty(pageParam.getIds()), WarehousingFilingAttachment::getId, pageParam.getIds())
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()),  WarehousingFilingAttachment::getId, pageParam.getId())
            .eq(ObjectUtils.isNotEmpty(pageParam.getCompanyId()),  WarehousingFilingAttachment::getCompanyId, pageParam.getCompanyId())
            .eq(ObjectUtils.isNotEmpty(pageParam.getFileName()),  WarehousingFilingAttachment::getFileName, pageParam.getFileName())
            .ne(WarehousingFilingAttachment::getCzbz, CzBzEnum.D.getCzBz())
            .orderByDesc(WarehousingFilingAttachment::getUpdateTime).list();
        List<WarehousingFilingAttachmentVo> voList = BeanUtils.copyToList(WarehousingFilingAttachmentList, WarehousingFilingAttachmentVo.class);
        DataBindHandleBootstrap.dataHandConvert(voList, 2);
        List<WarehousingFilingAttachmentExcelVo> excelVoList = BeanUtils.copyToList(voList, WarehousingFilingAttachmentExcelVo.class);
        ZtExcelBuildUtil.buildExcelExport(WarehousingFilingAttachmentExcelVo.class, "仓储备案-仓储附件信息").setData(excelVoList).doWebExport();
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingAttachmentVo> listByMainId(String companyId) {
        List<WarehousingFilingAttachment> list = super.lambdaQuery()
        .eq(WarehousingFilingAttachment::getCompanyId, companyId)
        .ne(WarehousingFilingAttachment::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingAttachment::getUpdateTime).list();
        return BeanUtils.copyToList(list, WarehousingFilingAttachmentVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean deleteByMainId(String companyId) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
        .eq(WarehousingFilingAttachment::getCompanyId, companyId)
        .set(WarehousingFilingAttachment::getCzbz , CzBzEnum.D.getCzBz())
        .set(WarehousingFilingAttachment::getUpdateBy ,loginUser.getName())
        .set(WarehousingFilingAttachment::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Override
    public List<WarehousingFilingAttachmentSaveParam> listByMainIdWithSaveParam(String companyId) {
        List<WarehousingFilingAttachment> warehousingFilingAttachmentList = super.lambdaQuery()
        .eq(WarehousingFilingAttachment::getCompanyId, companyId)
        .ne(WarehousingFilingAttachment::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingAttachment::getUpdateTime).list();
        return BeanUtils.copyToList(warehousingFilingAttachmentList, WarehousingFilingAttachmentSaveParam.class);
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingAttachmentVo> listByMainIdWithHistory(String companyId) {
        List<WarehousingFilingAttachment> list = super.lambdaQuery()
            .eq(WarehousingFilingAttachment::getCompanyId, companyId)
        .orderByDesc(WarehousingFilingAttachment::getUpdateTime).list();
        return BeanUtils.copyToList(list, WarehousingFilingAttachmentVo.class);
    }

    @Override
    public List<WarehousingFilingAttachmentSaveParam> listByMainIdWithSaveParamWithHistory(String companyId) {
        List<WarehousingFilingAttachment> warehousingFilingAttachmentList = super.lambdaQuery()
            .eq(WarehousingFilingAttachment::getCompanyId, companyId)
        .orderByDesc(WarehousingFilingAttachment::getUpdateTime).list();
        return BeanUtils.copyToList(warehousingFilingAttachmentList, WarehousingFilingAttachmentSaveParam.class);
    }

    @Override
    public WarehousingFilingAttachment getByUnx(String companyId,String fileName) {
        return super.lambdaQuery()
            .select(WarehousingFilingAttachment::getId)
            .eq(WarehousingFilingAttachment::getCompanyId, companyId)
            .eq(WarehousingFilingAttachment::getFileName, fileName)
            .ne(WarehousingFilingAttachment::getCzbz, CzBzEnum.D.getCzBz())
            .oneOpt()
            .map(WarehousingFilingAttachment::getId)
            .map(this::getById)
            .orElse(null);
    }

    @Override
    public FileVo uploadUse(MultipartFile multipartFile) {
        LoginUser loginUser = UserSecurity.loginUser();
        Assert.isTrue(verifySize(multipartFile.getSize()), "上传文件不能大于20M");
        String fileId = fileStorageService.uploadUse(multipartFile, loginUser.getOrganizeId(), loginUser.getStockHouseId());
        String fileName = multipartFile.getOriginalFilename();
        FileVo fileVo = new FileVo();
        fileVo.setFileId(fileId);
        fileVo.setFileName(fileName);
        return fileVo;
    }

    @SneakyThrows
    @Override
    public List<FileVo> batchUploadUse(MultipartFile[] multipartFiles) {
        List<FileVo> fileVoList = Lists.newArrayList();
        for (int i = 0; i < multipartFiles.length; i++) {
            FileVo fileVo = uploadUse(multipartFiles[i]);
            fileVoList.add(fileVo);
        }
        return fileVoList;
    }

    @Override
    public void download(FileVo fileVo) {
        WarehousingFilingAttachment warehousingFilingAttachment = super.lambdaQuery()
        .eq(WarehousingFilingAttachment::getFileId, fileVo.getFileId())
        .ne(WarehousingFilingAttachment::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingAttachment::getUpdateTime).list().stream().findFirst().orElse(null);
        if(ObjectUtils.isNotEmpty(warehousingFilingAttachment)){
            fileStorageService.downloadWithFileName(fileVo.getFileId(),warehousingFilingAttachment.getFileName());
        }
    }

    @Override
    public void downloadToZip(List<FileVo> fileVos) {
        List<String> fileStorageIdList = fileVos.stream().map(e->e.getFileId()).collect(Collectors.toList());
        List<String> fileNames = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(fileStorageIdList)){
            for (int i = 0; i < fileStorageIdList.size(); i++) {
                WarehousingFilingAttachment warehousingFilingAttachment = super.lambdaQuery()
                .eq(WarehousingFilingAttachment::getFileId, fileStorageIdList.get(i))
                .ne(WarehousingFilingAttachment::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(WarehousingFilingAttachment::getUpdateTime).list().stream().findFirst().orElse(null);
                if(ObjectUtils.isNotEmpty(warehousingFilingAttachment)){
                    fileNames.add(warehousingFilingAttachment.getFileName());
                }else {
                    fileStorageIdList.remove(i);
                    i--;
                }
            }
            fileStorageService.downloadToZip(fileStorageIdList,fileNames,"仓储备案-仓储附件");
        }
    }

    @Override
    public Boolean updateOrSaveByUnx(WarehousingFilingAttachmentSaveParam saveParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingAttachment unx = getByUnx(saveParam.getCompanyId(),saveParam.getFileName());
        if (ObjectUtils.isNotEmpty(unx)) {
            return super.lambdaUpdate()
                .eq(ObjectUtils.isNotEmpty(saveParam.getCompanyId()),WarehousingFilingAttachment::getCompanyId,saveParam.getCompanyId())
                .eq(ObjectUtils.isNotEmpty(saveParam.getFileName()),WarehousingFilingAttachment::getFileName,saveParam.getFileName())
                .set(ObjectUtils.isNotEmpty(saveParam.getCompanyId()),WarehousingFilingAttachment::getCompanyId,saveParam.getCompanyId())
                .set(ObjectUtils.isNotEmpty(saveParam.getFileName()),WarehousingFilingAttachment::getFileName,saveParam.getFileName())
                .set(ObjectUtils.isNotEmpty(saveParam.getFileId()),WarehousingFilingAttachment::getFileId,saveParam.getFileId())
                .set(ObjectUtils.isNotEmpty(saveParam.getFileUrl()),WarehousingFilingAttachment::getFileUrl,saveParam.getFileUrl())
                .set(WarehousingFilingAttachment::getCzbz, CzBzEnum.U.getCzBz())
                .set(WarehousingFilingAttachment::getUpdateBy, loginUser.getName())
                .set(WarehousingFilingAttachment::getUpdateTime, LocalDateTime.now())
                .update();
        } else {
            WarehousingFilingAttachment warehousingFilingAttachment = BeanUtils.copyByClass(saveParam, WarehousingFilingAttachment.class);
            warehousingFilingAttachment.setId(IdUtil.simpleUUID());
            warehousingFilingAttachment.setCreateBy(loginUser.getName());
            warehousingFilingAttachment.setUpdateBy(loginUser.getName());
            warehousingFilingAttachment.setUpdateTime(LocalDateTime.now());
            warehousingFilingAttachment.setCreateTime(LocalDateTime.now());
            warehousingFilingAttachment.setCzbz(CzBzEnum.I.getCzBz());
            return super.save(warehousingFilingAttachment);
        }
    }

    @DataBindService(strategy = DataBindWarehousingFilingAttachment.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, warehousingFilingAttachmentMapper);
    }

    private boolean isState(String fileType) {
        Set<String> fileTypes = new HashSet();
        fileTypes.add("xlsx");
        fileTypes.add("xls");
        return fileTypes.contains(fileType);
    }

    private Boolean verifySize(long fileS) {
        double fileSize =  (double) fileS / 1048576;
        if (fileSize > 20) {
            return false;
        }
        return true;
    }

}