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
import com.sydata.filing.domain.WarehousingFilingDevice;
import com.sydata.filing.mapper.WarehousingFilingDeviceMapper;
import com.sydata.filing.service.IWarehousingFilingDeviceService;
import com.sydata.filing.service.IBusWarehousingFilingDeviceService;
import com.sydata.filing.param.WarehousingFilingDevicePageParam;
import com.sydata.filing.param.WarehousingFilingDeviceSaveParam;
import com.sydata.filing.vo.WarehousingFilingDeviceVo;
import com.sydata.filing.vo.WarehousingFilingDeviceExcelVo;
import com.sydata.filing.vo.FileVo;
import com.sydata.filing.annotation.DataBindWarehousingFilingDevice;
import lombok.SneakyThrows;
import com.sydata.common.api.enums.CzBzEnum;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import com.sydata.common.file.service.IFileStorageService;

/**   
 * @Description:TODO(仓储备案-仓储设备服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("warehousingFilingDeviceService")
public class WarehousingFilingDeviceServiceImpl extends ServiceImpl<WarehousingFilingDeviceMapper, WarehousingFilingDevice> implements IWarehousingFilingDeviceService  {

    final static String CACHE_NAME = "composite:warehousingFilingDevice";

    @Resource
    private WarehousingFilingDeviceMapper warehousingFilingDeviceMapper;

    @Resource
    private IBusWarehousingFilingDeviceService busWarehousingFilingDeviceService;

    @Resource
    private IFileStorageService fileStorageService;

    @DataBindFieldConvert
    @Override
    public Page<WarehousingFilingDeviceVo> pages(WarehousingFilingDevicePageParam pageParam) {
        Page<WarehousingFilingDevice> page = super.lambdaQuery()
        .eq(ObjectUtils.isNotEmpty(pageParam.getId()),  WarehousingFilingDevice::getId, pageParam.getId())
        .eq(ObjectUtils.isNotEmpty(pageParam.getCompanyId()),  WarehousingFilingDevice::getCompanyId, pageParam.getCompanyId())
        .eq(ObjectUtils.isNotEmpty(pageParam.getStockId()),  WarehousingFilingDevice::getStockId, pageParam.getStockId())
        .eq(ObjectUtils.isNotEmpty(pageParam.getSbmc()),  WarehousingFilingDevice::getSbmc, pageParam.getSbmc())
        .ne(WarehousingFilingDevice::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingDevice::getUpdateTime)
        .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));

        busWarehousingFilingDeviceService.beforeReturnPage(page);

        return BeanUtils.copyToPage(page, WarehousingFilingDeviceVo.class);
    }

    @DataBindFieldConvert
    @Override
    public WarehousingFilingDeviceVo detail(String id) {
    IWarehousingFilingDeviceService warehousingFilingDeviceService = SpringUtil.getBean(this.getClass());
        WarehousingFilingDeviceVo warehousingFilingDeviceVo = BeanUtils.copyByClass(warehousingFilingDeviceService.getById(id), WarehousingFilingDeviceVo.class);

        busWarehousingFilingDeviceService.beforeReturnDetail(warehousingFilingDeviceVo);

        return warehousingFilingDeviceVo;
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingDeviceVo> detailList(List<String> ids) {
        List<WarehousingFilingDevice> warehousingFilingDeviceList =
         super.lambdaQuery()
        .in(WarehousingFilingDevice::getId, ids)
        .ne(WarehousingFilingDevice::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingDevice::getUpdateTime).list();

        List<WarehousingFilingDeviceVo> warehousingFilingDeviceVoList = BeanUtils.copyToList(warehousingFilingDeviceList, WarehousingFilingDeviceVo.class);
        busWarehousingFilingDeviceService.beforeReturnDetailList(warehousingFilingDeviceVoList);

        return warehousingFilingDeviceVoList;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(WarehousingFilingDeviceSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingDevice warehousingFilingDevice = BeanUtils.copyByClass(param, WarehousingFilingDevice.class);
        warehousingFilingDevice.setId(IdUtil.simpleUUID());
        warehousingFilingDevice.setCreateBy(loginUser.getName());
        warehousingFilingDevice.setUpdateBy(loginUser.getName());
        warehousingFilingDevice.setUpdateTime(LocalDateTime.now());
        warehousingFilingDevice.setCreateTime(LocalDateTime.now());
        warehousingFilingDevice.setCzbz(CzBzEnum.I.getCzBz());

        WarehousingFilingDevice unx = getByUnx(param.getCompanyId(),param.getStockId(),param.getSbmc());
        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在"+"仓储企业ID和"+"仓储库点ID和"+"设备名称"+"相同的数据");

        busWarehousingFilingDeviceService.beforeDoSave(warehousingFilingDevice);

        super.save(warehousingFilingDevice);
        param.setId(warehousingFilingDevice.getId());
        return warehousingFilingDevice.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(WarehousingFilingDeviceSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingDevice warehousingFilingDevice = BeanUtils.copyByClass(param, WarehousingFilingDevice.class);
        warehousingFilingDevice.setCzbz(CzBzEnum.U.getCzBz());
        warehousingFilingDevice.setUpdateBy(loginUser.getName());
        warehousingFilingDevice.setUpdateTime(LocalDateTime.now());

        WarehousingFilingDevice unx = getByUnx(param.getCompanyId(),param.getStockId(),param.getSbmc());
        if (ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在"+"仓储企业ID和"+"仓储库点ID和"+"设备名称"+"相同的数据");
        }

        busWarehousingFilingDeviceService.beforeDoUpdate(warehousingFilingDevice);

        super.updateById(warehousingFilingDevice);
        param.setId(warehousingFilingDevice.getId());
        return warehousingFilingDevice.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();

        busWarehousingFilingDeviceService.beforeDoRemove(ids);

        return super.lambdaUpdate()
        .in(WarehousingFilingDevice::getId, ids)
        .set(WarehousingFilingDevice::getCzbz , CzBzEnum.D.getCzBz())
        .set(WarehousingFilingDevice::getUpdateBy ,loginUser.getName())
        .set(WarehousingFilingDevice::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean saveOrUpdateBatchData(List<WarehousingFilingDeviceSaveParam> params) {
        if(CollectionUtils.isNotEmpty(params)){
            for (int i = 0; i < params.size(); i++) {
                WarehousingFilingDeviceSaveParam warehousingFilingDeviceSaveParam = params.get(i);
                if(StringUtil.isNullOrEmpty(warehousingFilingDeviceSaveParam.getId())){
                    saveData(warehousingFilingDeviceSaveParam);
                }else{
                    updateData(warehousingFilingDeviceSaveParam);
                }
            }
        }
        return true;
    }

    @Override
    public Boolean saveOrUpdateBatchDataByOtherSystem(List<WarehousingFilingDeviceSaveParam> params) {
        Boolean returnStatus = true;
        List<WarehousingFilingDevice> warehousingFilingDevices = BeanUtils.copyToList(params, WarehousingFilingDevice.class);
        if(CollectionUtils.isNotEmpty(warehousingFilingDevices)){
            returnStatus = super.saveOrUpdateBatch(warehousingFilingDevices);
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
        List<WarehousingFilingDeviceExcelVo> excelVoList = ExcelImportUtil.importExcel(file.getInputStream(), WarehousingFilingDeviceExcelVo.class, params);
        Assert.state(CollectionUtils.isNotEmpty(excelVoList), "导入数据为空，请重新导入！");
        List<WarehousingFilingDeviceSaveParam> saveList = BeanUtils.copyToList(excelVoList, WarehousingFilingDeviceSaveParam.class);
        Assert.state(CollectionUtils.isNotEmpty(saveList), "导入数据为空，请重新导入！");
        for (int i = 0; i < saveList.size(); i++) {
            updateOrSaveByUnx(saveList.get(i));
        }
    }
                        
    @Override
    public void exportData(WarehousingFilingDevicePageParam pageParam) {
        List<WarehousingFilingDevice> WarehousingFilingDeviceList = super.lambdaQuery()
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()), WarehousingFilingDevice::getId, pageParam.getId())
            .eq(CollectionUtils.isNotEmpty(pageParam.getIds()), WarehousingFilingDevice::getId, pageParam.getIds())
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()),  WarehousingFilingDevice::getId, pageParam.getId())
            .eq(ObjectUtils.isNotEmpty(pageParam.getCompanyId()),  WarehousingFilingDevice::getCompanyId, pageParam.getCompanyId())
            .eq(ObjectUtils.isNotEmpty(pageParam.getStockId()),  WarehousingFilingDevice::getStockId, pageParam.getStockId())
            .eq(ObjectUtils.isNotEmpty(pageParam.getSbmc()),  WarehousingFilingDevice::getSbmc, pageParam.getSbmc())
            .ne(WarehousingFilingDevice::getCzbz, CzBzEnum.D.getCzBz())
            .orderByDesc(WarehousingFilingDevice::getUpdateTime).list();
        List<WarehousingFilingDeviceVo> voList = BeanUtils.copyToList(WarehousingFilingDeviceList, WarehousingFilingDeviceVo.class);
        DataBindHandleBootstrap.dataHandConvert(voList, 2);
        List<WarehousingFilingDeviceExcelVo> excelVoList = BeanUtils.copyToList(voList, WarehousingFilingDeviceExcelVo.class);
        ZtExcelBuildUtil.buildExcelExport(WarehousingFilingDeviceExcelVo.class, "仓储备案-仓储设备信息").setData(excelVoList).doWebExport();
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingDeviceVo> listByMainId(String companyId,String stockId) {
        List<WarehousingFilingDevice> list = super.lambdaQuery()
        .eq(!StringUtil.isNullOrEmpty(companyId),WarehousingFilingDevice::getCompanyId, companyId)
        .eq(!StringUtil.isNullOrEmpty(stockId),WarehousingFilingDevice::getStockId, stockId)
        .ne(WarehousingFilingDevice::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingDevice::getUpdateTime).list();
        return BeanUtils.copyToList(list, WarehousingFilingDeviceVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean deleteByMainId(String companyId,String stockId) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
        .eq(!StringUtil.isNullOrEmpty(companyId),WarehousingFilingDevice::getCompanyId, companyId)
        .eq(!StringUtil.isNullOrEmpty(stockId),WarehousingFilingDevice::getStockId, stockId)
        .set(WarehousingFilingDevice::getCzbz , CzBzEnum.D.getCzBz())
        .set(WarehousingFilingDevice::getUpdateBy ,loginUser.getName())
        .set(WarehousingFilingDevice::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Override
    public List<WarehousingFilingDeviceSaveParam> listByMainIdWithSaveParam(String companyId,String stockId) {
        List<WarehousingFilingDevice> warehousingFilingDeviceList = super.lambdaQuery()
        .eq(!StringUtil.isNullOrEmpty(companyId),WarehousingFilingDevice::getCompanyId, companyId)
        .eq(!StringUtil.isNullOrEmpty(stockId),WarehousingFilingDevice::getStockId, stockId)
        .ne(WarehousingFilingDevice::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingDevice::getUpdateTime).list();
        return BeanUtils.copyToList(warehousingFilingDeviceList, WarehousingFilingDeviceSaveParam.class);
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingDeviceVo> listByMainIdWithHistory(String companyId,String stockId) {
        List<WarehousingFilingDevice> list = super.lambdaQuery()
        .eq(!StringUtil.isNullOrEmpty(companyId),WarehousingFilingDevice::getCompanyId, companyId)
        .eq(!StringUtil.isNullOrEmpty(stockId),WarehousingFilingDevice::getStockId, stockId)
        .orderByDesc(WarehousingFilingDevice::getUpdateTime).list();
        return BeanUtils.copyToList(list, WarehousingFilingDeviceVo.class);
    }

    @Override
    public List<WarehousingFilingDeviceSaveParam> listByMainIdWithSaveParamWithHistory(String companyId,String stockId) {
        List<WarehousingFilingDevice> warehousingFilingDeviceList = super.lambdaQuery()
        .eq(!StringUtil.isNullOrEmpty(companyId),WarehousingFilingDevice::getCompanyId, companyId)
        .eq(!StringUtil.isNullOrEmpty(stockId),WarehousingFilingDevice::getStockId, stockId)
        .orderByDesc(WarehousingFilingDevice::getUpdateTime).list();
        return BeanUtils.copyToList(warehousingFilingDeviceList, WarehousingFilingDeviceSaveParam.class);
    }

    @Override
    public WarehousingFilingDevice getByUnx(String companyId,String stockId,String sbmc) {
        return super.lambdaQuery()
            .select(WarehousingFilingDevice::getId)
            .eq(WarehousingFilingDevice::getCompanyId, companyId)
            .eq(WarehousingFilingDevice::getStockId, stockId)
            .eq(WarehousingFilingDevice::getSbmc, sbmc)
            .ne(WarehousingFilingDevice::getCzbz, CzBzEnum.D.getCzBz())
            .oneOpt()
            .map(WarehousingFilingDevice::getId)
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
        WarehousingFilingDevice warehousingFilingDevice = super.lambdaQuery()
        .eq(WarehousingFilingDevice::getFileId, fileVo.getFileId())
        .ne(WarehousingFilingDevice::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingDevice::getUpdateTime).list().stream().findFirst().orElse(null);
        if(ObjectUtils.isNotEmpty(warehousingFilingDevice)){
            fileStorageService.downloadWithFileName(fileVo.getFileId(),warehousingFilingDevice.getFileName());
        }
    }

    @Override
    public void downloadToZip(List<FileVo> fileVos) {
        List<String> fileStorageIdList = fileVos.stream().map(e->e.getFileId()).collect(Collectors.toList());
        List<String> fileNames = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(fileStorageIdList)){
            for (int i = 0; i < fileStorageIdList.size(); i++) {
                WarehousingFilingDevice warehousingFilingDevice = super.lambdaQuery()
                .eq(WarehousingFilingDevice::getFileId, fileStorageIdList.get(i))
                .ne(WarehousingFilingDevice::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(WarehousingFilingDevice::getUpdateTime).list().stream().findFirst().orElse(null);
                if(ObjectUtils.isNotEmpty(warehousingFilingDevice)){
                    fileNames.add(warehousingFilingDevice.getFileName());
                }else {
                    fileStorageIdList.remove(i);
                    i--;
                }
            }
            fileStorageService.downloadToZip(fileStorageIdList,fileNames,"仓储备案-仓储设备");
        }
    }

    @Override
    public Boolean updateOrSaveByUnx(WarehousingFilingDeviceSaveParam saveParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingDevice unx = getByUnx(saveParam.getCompanyId(),saveParam.getStockId(),saveParam.getSbmc());
        if (ObjectUtils.isNotEmpty(unx)) {
            return super.lambdaUpdate()
                .eq(ObjectUtils.isNotEmpty(saveParam.getCompanyId()),WarehousingFilingDevice::getCompanyId,saveParam.getCompanyId())
                .eq(ObjectUtils.isNotEmpty(saveParam.getStockId()),WarehousingFilingDevice::getStockId,saveParam.getStockId())
                .eq(ObjectUtils.isNotEmpty(saveParam.getSbmc()),WarehousingFilingDevice::getSbmc,saveParam.getSbmc())
                .set(ObjectUtils.isNotEmpty(saveParam.getCompanyId()),WarehousingFilingDevice::getCompanyId,saveParam.getCompanyId())
                .set(ObjectUtils.isNotEmpty(saveParam.getStockId()),WarehousingFilingDevice::getStockId,saveParam.getStockId())
                .set(ObjectUtils.isNotEmpty(saveParam.getSbmc()),WarehousingFilingDevice::getSbmc,saveParam.getSbmc())
                .set(ObjectUtils.isNotEmpty(saveParam.getSblx()),WarehousingFilingDevice::getSblx,saveParam.getSblx())
                .set(ObjectUtils.isNotEmpty(saveParam.getSbxh()),WarehousingFilingDevice::getSbxh,saveParam.getSbxh())
                .set(ObjectUtils.isNotEmpty(saveParam.getJldw()),WarehousingFilingDevice::getJldw,saveParam.getJldw())
                .set(ObjectUtils.isNotEmpty(saveParam.getSbzt()),WarehousingFilingDevice::getSbzt,saveParam.getSbzt())
                .set(ObjectUtils.isNotEmpty(saveParam.getFileId()),WarehousingFilingDevice::getFileId,saveParam.getFileId())
                .set(ObjectUtils.isNotEmpty(saveParam.getFileName()),WarehousingFilingDevice::getFileName,saveParam.getFileName())
                .set(ObjectUtils.isNotEmpty(saveParam.getFileUrl()),WarehousingFilingDevice::getFileUrl,saveParam.getFileUrl())
                .set(WarehousingFilingDevice::getCzbz, CzBzEnum.U.getCzBz())
                .set(WarehousingFilingDevice::getUpdateBy, loginUser.getName())
                .set(WarehousingFilingDevice::getUpdateTime, LocalDateTime.now())
                .update();
        } else {
            WarehousingFilingDevice warehousingFilingDevice = BeanUtils.copyByClass(saveParam, WarehousingFilingDevice.class);
            warehousingFilingDevice.setId(IdUtil.simpleUUID());
            warehousingFilingDevice.setCreateBy(loginUser.getName());
            warehousingFilingDevice.setUpdateBy(loginUser.getName());
            warehousingFilingDevice.setUpdateTime(LocalDateTime.now());
            warehousingFilingDevice.setCreateTime(LocalDateTime.now());
            warehousingFilingDevice.setCzbz(CzBzEnum.I.getCzBz());
            return super.save(warehousingFilingDevice);
        }
    }

    @DataBindService(strategy = DataBindWarehousingFilingDevice.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, warehousingFilingDeviceMapper);
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