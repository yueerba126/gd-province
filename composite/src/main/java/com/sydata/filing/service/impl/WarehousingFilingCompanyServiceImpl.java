package com.sydata.filing.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.filing.WarehousingFilingApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.filing.annotation.DataBindWarehousingFilingCompany;
import com.sydata.filing.domain.WarehousingFilingCompany;
import com.sydata.filing.mapper.WarehousingFilingCompanyMapper;
import com.sydata.filing.param.*;
import com.sydata.filing.service.*;
import com.sydata.filing.vo.WarehousingFilingCompanyExcelVo;
import com.sydata.filing.vo.WarehousingFilingCompanyVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.excel.ZtExcelBuildUtil;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import io.netty.util.internal.StringUtil;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sydata.collect.api.enums.DataApiEnum.WAREHOUSING_FILING;

/**   
 * @Description:TODO(仓储备案-仓储企业服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("warehousingFilingCompanyService")
public class WarehousingFilingCompanyServiceImpl
        extends BaseDataImpl<WarehousingFilingApiParam, WarehousingFilingCompanyMapper, WarehousingFilingCompany, Object>
        implements IWarehousingFilingCompanyService  {

    final static String CACHE_NAME = "composite:warehousingFilingCompany";

    @Resource
    private WarehousingFilingCompanyMapper warehousingFilingCompanyMapper;
    @Resource
    private IBusWarehousingFilingCompanyService busWarehousingFilingCompanyService;
    @Resource
    private IWarehousingFilingStockService warehousingFilingStockService;
    @Resource
    private IWarehousingFilingAttachmentService warehousingFilingAttachmentService;
    @Resource
    private IWarehousingFilingDeviceService warehousingFilingDeviceService;
    @Resource
    private IWarehousingFilingPersonnelService warehousingFilingPersonnelService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean save(WarehousingFilingCompany param) {
        WarehousingFilingCompany warehousingFilingCompany = BeanUtils.copyByClass(param, WarehousingFilingCompany.class);

        WarehousingFilingCompany unx = getByUnx(param.getDwdm());
        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在"+"仓储单位统一社会信用代码"+"相同的数据");

        busWarehousingFilingCompanyService.beforeDoSave(warehousingFilingCompany);

        return super.save(warehousingFilingCompany);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean updateById(WarehousingFilingCompany param) {
        WarehousingFilingCompany warehousingFilingCompany = BeanUtils.copyByClass(param, WarehousingFilingCompany.class);
        WarehousingFilingCompany unx = getByUnx(param.getDwdm());
        if (ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在"+"仓储单位统一社会信用代码"+"相同的数据");
        }

        busWarehousingFilingCompanyService.beforeDoUpdate(warehousingFilingCompany);

        return super.updateById(warehousingFilingCompany);
    }

    @Override
    public boolean removeById(WarehousingFilingCompany entity) {
        Assert.isTrue(false, "不允许删除");
        return super.removeById(entity);
    }

    @Override
    protected void collectAfter(WarehousingFilingApiParam param, WarehousingFilingCompany warehousingFiling) {
        // 保存或是更新从表信息
        List<WarehousingFilingStockSaveParam> filingStockSaveParamList = BeanUtils.copyToList(param.getWarehousingFilingStockSaveParams(),
                WarehousingFilingStockSaveParam.class);
        warehousingFilingStockService.saveOrUpdateBatchDataByOtherSystem(filingStockSaveParamList);
        List<WarehousingFilingAttachmentSaveParam> warehousingFilingAttachmentSaveParamList = BeanUtils.copyToList(param.getWarehousingFilingAttachmentSaveParams(),
                WarehousingFilingAttachmentSaveParam.class);
        warehousingFilingAttachmentService.saveOrUpdateBatchDataByOtherSystem(warehousingFilingAttachmentSaveParamList);
        List<WarehousingFilingDeviceSaveParam> warehousingFilingDeviceSaveParamList = BeanUtils.copyToList(param.getWarehousingFilingDeviceSaveParams(),
                WarehousingFilingDeviceSaveParam.class);
        warehousingFilingDeviceService.saveOrUpdateBatchDataByOtherSystem(warehousingFilingDeviceSaveParamList);
        List<WarehousingFilingPersonnelSaveParam> warehousingFilingPersonnelSaveParamList = BeanUtils.copyToList(param.getWarehousingFilingPersonnelSaveParams(),
                WarehousingFilingPersonnelSaveParam.class);
        warehousingFilingPersonnelService.saveOrUpdateBatchDataByOtherSystem(warehousingFilingPersonnelSaveParamList);
    }

    @DataBindFieldConvert
    @Override
    public Page<WarehousingFilingCompanyVo> pages(WarehousingFilingCompanyPageParam pageParam) {
        Page<WarehousingFilingCompany> page = super.lambdaQuery()
        .eq(ObjectUtils.isNotEmpty(pageParam.getId()),  WarehousingFilingCompany::getId, pageParam.getId())
        .eq(ObjectUtils.isNotEmpty(pageParam.getDwmc()),  WarehousingFilingCompany::getDwmc, pageParam.getDwmc())
        .eq(ObjectUtils.isNotEmpty(pageParam.getDwlx()),  WarehousingFilingCompany::getDwlx, pageParam.getDwlx())
        .eq(ObjectUtils.isNotEmpty(pageParam.getBazt()),  WarehousingFilingCompany::getBazt, pageParam.getBazt())
        .ge(ObjectUtils.isNotEmpty(pageParam.getBeginBarq()),  WarehousingFilingCompany::getBarq, pageParam.getBeginBarq())
        .le(ObjectUtils.isNotEmpty(pageParam.getEndBarq()),  WarehousingFilingCompany::getBarq, pageParam.getEndBarq())
        .eq(ObjectUtils.isNotEmpty(pageParam.getBaly()),  WarehousingFilingCompany::getBaly, pageParam.getBaly())
        .ne(WarehousingFilingCompany::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingCompany::getUpdateTime)
        .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));

        busWarehousingFilingCompanyService.beforeReturnPage(page);

        return BeanUtils.copyToPage(page, WarehousingFilingCompanyVo.class);
    }

    @DataBindFieldConvert
    @Override
    public WarehousingFilingCompanyVo detail(String id) {
    IWarehousingFilingCompanyService warehousingFilingCompanyService = SpringUtil.getBean(this.getClass());
        WarehousingFilingCompanyVo warehousingFilingCompanyVo = BeanUtils.copyByClass(warehousingFilingCompanyService.getById(id), WarehousingFilingCompanyVo.class);

        busWarehousingFilingCompanyService.beforeReturnDetail(warehousingFilingCompanyVo);

        return warehousingFilingCompanyVo;
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingCompanyVo> detailList(List<String> ids) {
        List<WarehousingFilingCompany> warehousingFilingCompanyList =
         super.lambdaQuery()
        .in(WarehousingFilingCompany::getId, ids)
        .ne(WarehousingFilingCompany::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingCompany::getUpdateTime).list();

        List<WarehousingFilingCompanyVo> warehousingFilingCompanyVoList = BeanUtils.copyToList(warehousingFilingCompanyList, WarehousingFilingCompanyVo.class);
        busWarehousingFilingCompanyService.beforeReturnDetailList(warehousingFilingCompanyVoList);

        return warehousingFilingCompanyVoList;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(WarehousingFilingCompanySaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingCompany warehousingFilingCompany = BeanUtils.copyByClass(param, WarehousingFilingCompany.class);
        warehousingFilingCompany.setId(IdUtil.simpleUUID());
        warehousingFilingCompany.setCreateBy(loginUser.getName());
        warehousingFilingCompany.setUpdateBy(loginUser.getName());
        warehousingFilingCompany.setUpdateTime(LocalDateTime.now());
        warehousingFilingCompany.setCreateTime(LocalDateTime.now());
        warehousingFilingCompany.setCzbz(CzBzEnum.I.getCzBz());

        WarehousingFilingCompany unx = getByUnx(param.getDwdm());
        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在"+"仓储单位统一社会信用代码"+"相同的数据");

        busWarehousingFilingCompanyService.beforeDoSave(warehousingFilingCompany);

        super.save(warehousingFilingCompany);
        param.setId(warehousingFilingCompany.getId());
        return warehousingFilingCompany.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(WarehousingFilingCompanySaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingCompany warehousingFilingCompany = BeanUtils.copyByClass(param, WarehousingFilingCompany.class);
        warehousingFilingCompany.setCzbz(CzBzEnum.U.getCzBz());
        warehousingFilingCompany.setUpdateBy(loginUser.getName());
        warehousingFilingCompany.setUpdateTime(LocalDateTime.now());

        WarehousingFilingCompany unx = getByUnx(param.getDwdm());
        if (ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在"+"仓储单位统一社会信用代码"+"相同的数据");
        }

        busWarehousingFilingCompanyService.beforeDoUpdate(warehousingFilingCompany);

        super.updateById(warehousingFilingCompany);
        param.setId(warehousingFilingCompany.getId());
        return warehousingFilingCompany.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();

        busWarehousingFilingCompanyService.beforeDoRemove(ids);

        return super.lambdaUpdate()
        .in(WarehousingFilingCompany::getId, ids)
        .set(WarehousingFilingCompany::getCzbz , CzBzEnum.D.getCzBz())
        .set(WarehousingFilingCompany::getUpdateBy ,loginUser.getName())
        .set(WarehousingFilingCompany::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean saveOrUpdateBatchData(List<WarehousingFilingCompanySaveParam> params) {
        if(CollectionUtils.isNotEmpty(params)){
            for (int i = 0; i < params.size(); i++) {
                WarehousingFilingCompanySaveParam warehousingFilingCompanySaveParam = params.get(i);
                if(StringUtil.isNullOrEmpty(warehousingFilingCompanySaveParam.getId())){
                    saveData(warehousingFilingCompanySaveParam);
                }else{
                    updateData(warehousingFilingCompanySaveParam);
                }
            }
        }
        return true;
    }

    @Override
    public Boolean saveOrUpdateBatchDataByOtherSystem(List<WarehousingFilingCompanySaveParam> params) {
        Boolean returnStatus = true;
        List<WarehousingFilingCompany> warehousingFilingCompanys = BeanUtils.copyToList(params, WarehousingFilingCompany.class);
        if(CollectionUtils.isNotEmpty(warehousingFilingCompanys)){
            returnStatus = super.saveOrUpdateBatch(warehousingFilingCompanys);
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
        List<WarehousingFilingCompanyExcelVo> excelVoList = ExcelImportUtil.importExcel(file.getInputStream(), WarehousingFilingCompanyExcelVo.class, params);
        Assert.state(CollectionUtils.isNotEmpty(excelVoList), "导入数据为空，请重新导入！");
        List<WarehousingFilingCompanySaveParam> saveList = BeanUtils.copyToList(excelVoList, WarehousingFilingCompanySaveParam.class);
        Assert.state(CollectionUtils.isNotEmpty(saveList), "导入数据为空，请重新导入！");
        for (int i = 0; i < saveList.size(); i++) {
            updateOrSaveByUnx(saveList.get(i));
        }
    }
                        
    @Override
    public void exportData(WarehousingFilingCompanyPageParam pageParam) {
        List<WarehousingFilingCompany> WarehousingFilingCompanyList = super.lambdaQuery()
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()), WarehousingFilingCompany::getId, pageParam.getId())
            .eq(CollectionUtils.isNotEmpty(pageParam.getIds()), WarehousingFilingCompany::getId, pageParam.getIds())
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()),  WarehousingFilingCompany::getId, pageParam.getId())
            .eq(ObjectUtils.isNotEmpty(pageParam.getDwmc()),  WarehousingFilingCompany::getDwmc, pageParam.getDwmc())
            .eq(ObjectUtils.isNotEmpty(pageParam.getDwlx()),  WarehousingFilingCompany::getDwlx, pageParam.getDwlx())
            .eq(ObjectUtils.isNotEmpty(pageParam.getBazt()),  WarehousingFilingCompany::getBazt, pageParam.getBazt())
            .ge(ObjectUtils.isNotEmpty(pageParam.getBeginBarq()),  WarehousingFilingCompany::getBarq, pageParam.getBeginBarq())
            .le(ObjectUtils.isNotEmpty(pageParam.getEndBarq()),  WarehousingFilingCompany::getBarq, pageParam.getEndBarq())
            .eq(ObjectUtils.isNotEmpty(pageParam.getBaly()),  WarehousingFilingCompany::getBaly, pageParam.getBaly())
            .ne(WarehousingFilingCompany::getCzbz, CzBzEnum.D.getCzBz())
            .orderByDesc(WarehousingFilingCompany::getUpdateTime).list();
        List<WarehousingFilingCompanyVo> voList = BeanUtils.copyToList(WarehousingFilingCompanyList, WarehousingFilingCompanyVo.class);
        DataBindHandleBootstrap.dataHandConvert(voList, 2);
        List<WarehousingFilingCompanyExcelVo> excelVoList = BeanUtils.copyToList(voList, WarehousingFilingCompanyExcelVo.class);
        ZtExcelBuildUtil.buildExcelExport(WarehousingFilingCompanyExcelVo.class, "仓储备案-仓储企业信息").setData(excelVoList).doWebExport();
    }
    @Override
    public WarehousingFilingCompany getByUnx(String dwdm) {
        return super.lambdaQuery()
            .select(WarehousingFilingCompany::getId)
            .eq(WarehousingFilingCompany::getDwdm, dwdm)
            .ne(WarehousingFilingCompany::getCzbz, CzBzEnum.D.getCzBz())
            .oneOpt()
            .map(WarehousingFilingCompany::getId)
            .map(this::getById)
            .orElse(null);
    }

    @Override
    public Boolean updateOrSaveByUnx(WarehousingFilingCompanySaveParam saveParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingCompany unx = getByUnx(saveParam.getDwdm());
        if (ObjectUtils.isNotEmpty(unx)) {
            return super.lambdaUpdate()
                .eq(ObjectUtils.isNotEmpty(saveParam.getDwdm()),WarehousingFilingCompany::getDwdm,saveParam.getDwdm())
                .set(ObjectUtils.isNotEmpty(saveParam.getDwdm()),WarehousingFilingCompany::getDwdm,saveParam.getDwdm())
                .set(ObjectUtils.isNotEmpty(saveParam.getDwmc()),WarehousingFilingCompany::getDwmc,saveParam.getDwmc())
                .set(ObjectUtils.isNotEmpty(saveParam.getDwlx()),WarehousingFilingCompany::getDwlx,saveParam.getDwlx())
                .set(ObjectUtils.isNotEmpty(saveParam.getDwxz()),WarehousingFilingCompany::getDwxz,saveParam.getDwxz())
                .set(ObjectUtils.isNotEmpty(saveParam.getBalx()),WarehousingFilingCompany::getBalx,saveParam.getBalx())
                .set(ObjectUtils.isNotEmpty(saveParam.getBazt()),WarehousingFilingCompany::getBazt,saveParam.getBazt())
                .set(ObjectUtils.isNotEmpty(saveParam.getQyshr()),WarehousingFilingCompany::getQyshr,saveParam.getQyshr())
                .set(ObjectUtils.isNotEmpty(saveParam.getShyj()),WarehousingFilingCompany::getShyj,saveParam.getShyj())
                .set(ObjectUtils.isNotEmpty(saveParam.getShsj()),WarehousingFilingCompany::getShsj,saveParam.getShsj())
                .set(ObjectUtils.isNotEmpty(saveParam.getBarq()),WarehousingFilingCompany::getBarq,saveParam.getBarq())
                .set(ObjectUtils.isNotEmpty(saveParam.getBaly()),WarehousingFilingCompany::getBaly,saveParam.getBaly())
                .set(ObjectUtils.isNotEmpty(saveParam.getBaclbm()),WarehousingFilingCompany::getBaclbm,saveParam.getBaclbm())
                .set(ObjectUtils.isNotEmpty(saveParam.getCcywlx()),WarehousingFilingCompany::getCcywlx,saveParam.getCcywlx())
                .set(ObjectUtils.isNotEmpty(saveParam.getCcpz()),WarehousingFilingCompany::getCcpz,saveParam.getCcpz())
                .set(ObjectUtils.isNotEmpty(saveParam.getYyzz()),WarehousingFilingCompany::getYyzz,saveParam.getYyzz())
                .set(ObjectUtils.isNotEmpty(saveParam.getFzjg()),WarehousingFilingCompany::getFzjg,saveParam.getFzjg())
                .set(ObjectUtils.isNotEmpty(saveParam.getSpxkz()),WarehousingFilingCompany::getSpxkz,saveParam.getSpxkz())
                .set(ObjectUtils.isNotEmpty(saveParam.getFddbr()),WarehousingFilingCompany::getFddbr,saveParam.getFddbr())
                .set(ObjectUtils.isNotEmpty(saveParam.getZcsj()),WarehousingFilingCompany::getZcsj,saveParam.getZcsj())
                .set(ObjectUtils.isNotEmpty(saveParam.getZczb()),WarehousingFilingCompany::getZczb,saveParam.getZczb())
                .set(ObjectUtils.isNotEmpty(saveParam.getZcdz()),WarehousingFilingCompany::getZcdz,saveParam.getZcdz())
                .set(ObjectUtils.isNotEmpty(saveParam.getLsptzc()),WarehousingFilingCompany::getLsptzc,saveParam.getLsptzc())
                .set(ObjectUtils.isNotEmpty(saveParam.getGsdh()),WarehousingFilingCompany::getGsdh,saveParam.getGsdh())
                .set(ObjectUtils.isNotEmpty(saveParam.getSjdwdm()),WarehousingFilingCompany::getSjdwdm,saveParam.getSjdwdm())
                .set(ObjectUtils.isNotEmpty(saveParam.getSjdwmc()),WarehousingFilingCompany::getSjdwmc,saveParam.getSjdwmc())
                .set(ObjectUtils.isNotEmpty(saveParam.getLsgx()),WarehousingFilingCompany::getLsgx,saveParam.getLsgx())
                .set(ObjectUtils.isNotEmpty(saveParam.getJyfw()),WarehousingFilingCompany::getJyfw,saveParam.getJyfw())
                .set(ObjectUtils.isNotEmpty(saveParam.getXzqhdm()),WarehousingFilingCompany::getXzqhdm,saveParam.getXzqhdm())
                .set(ObjectUtils.isNotEmpty(saveParam.getJd()),WarehousingFilingCompany::getJd,saveParam.getJd())
                .set(ObjectUtils.isNotEmpty(saveParam.getWd()),WarehousingFilingCompany::getWd,saveParam.getWd())
                .set(ObjectUtils.isNotEmpty(saveParam.getJydz()),WarehousingFilingCompany::getJydz,saveParam.getJydz())
                .set(ObjectUtils.isNotEmpty(saveParam.getSgzg()),WarehousingFilingCompany::getSgzg,saveParam.getSgzg())
                .set(ObjectUtils.isNotEmpty(saveParam.getDczg()),WarehousingFilingCompany::getDczg,saveParam.getDczg())
                .set(ObjectUtils.isNotEmpty(saveParam.getLyxse()),WarehousingFilingCompany::getLyxse,saveParam.getLyxse())
                .set(ObjectUtils.isNotEmpty(saveParam.getCpxse()),WarehousingFilingCompany::getCpxse,saveParam.getCpxse())
                .set(ObjectUtils.isNotEmpty(saveParam.getZlxse()),WarehousingFilingCompany::getZlxse,saveParam.getZlxse())
                .set(ObjectUtils.isNotEmpty(saveParam.getZpxse()),WarehousingFilingCompany::getZpxse,saveParam.getZpxse())
                .set(WarehousingFilingCompany::getCzbz, CzBzEnum.U.getCzBz())
                .set(WarehousingFilingCompany::getUpdateBy, loginUser.getName())
                .set(WarehousingFilingCompany::getUpdateTime, LocalDateTime.now())
                .update();
        } else {
            WarehousingFilingCompany warehousingFilingCompany = BeanUtils.copyByClass(saveParam, WarehousingFilingCompany.class);
            warehousingFilingCompany.setId(IdUtil.simpleUUID());
            warehousingFilingCompany.setCreateBy(loginUser.getName());
            warehousingFilingCompany.setUpdateBy(loginUser.getName());
            warehousingFilingCompany.setUpdateTime(LocalDateTime.now());
            warehousingFilingCompany.setCreateTime(LocalDateTime.now());
            warehousingFilingCompany.setCzbz(CzBzEnum.I.getCzBz());
            return super.save(warehousingFilingCompany);
        }
    }

    @DataBindService(strategy = DataBindWarehousingFilingCompany.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, warehousingFilingCompanyMapper);
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

    @Override
    public DataApiEnum api() {
        return WAREHOUSING_FILING;
    }
}