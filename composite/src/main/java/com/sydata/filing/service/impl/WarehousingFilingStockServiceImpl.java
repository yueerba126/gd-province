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
import com.sydata.filing.domain.WarehousingFilingStock;
import com.sydata.filing.mapper.WarehousingFilingStockMapper;
import com.sydata.filing.service.IWarehousingFilingStockService;
import com.sydata.filing.service.IBusWarehousingFilingStockService;
import com.sydata.filing.param.WarehousingFilingStockPageParam;
import com.sydata.filing.param.WarehousingFilingStockSaveParam;
import com.sydata.filing.vo.WarehousingFilingStockVo;
import com.sydata.filing.vo.WarehousingFilingStockExcelVo;
import com.sydata.filing.vo.FileVo;
import com.sydata.filing.annotation.DataBindWarehousingFilingStock;
import lombok.SneakyThrows;
import com.sydata.common.api.enums.CzBzEnum;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import com.sydata.common.file.service.IFileStorageService;

/**   
 * @Description:TODO(仓储备案-仓储库点服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("warehousingFilingStockService")
public class WarehousingFilingStockServiceImpl extends ServiceImpl<WarehousingFilingStockMapper, WarehousingFilingStock> implements IWarehousingFilingStockService  {

    final static String CACHE_NAME = "composite:warehousingFilingStock";

    @Resource
    private WarehousingFilingStockMapper warehousingFilingStockMapper;

    @Resource
    private IBusWarehousingFilingStockService busWarehousingFilingStockService;

    @Resource
    private IFileStorageService fileStorageService;

    @DataBindFieldConvert
    @Override
    public Page<WarehousingFilingStockVo> pages(WarehousingFilingStockPageParam pageParam) {
        Page<WarehousingFilingStock> page = super.lambdaQuery()
        .eq(ObjectUtils.isNotEmpty(pageParam.getId()),  WarehousingFilingStock::getId, pageParam.getId())
        .eq(ObjectUtils.isNotEmpty(pageParam.getCompanyId()),  WarehousingFilingStock::getCompanyId, pageParam.getCompanyId())
        .eq(ObjectUtils.isNotEmpty(pageParam.getDwdm()),  WarehousingFilingStock::getDwdm, pageParam.getDwdm())
        .eq(ObjectUtils.isNotEmpty(pageParam.getDwmc()),  WarehousingFilingStock::getDwmc, pageParam.getDwmc())
        .eq(ObjectUtils.isNotEmpty(pageParam.getKddm()),  WarehousingFilingStock::getKddm, pageParam.getKddm())
        .eq(ObjectUtils.isNotEmpty(pageParam.getKdmc()),  WarehousingFilingStock::getKdmc, pageParam.getKdmc())
        .ne(WarehousingFilingStock::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingStock::getUpdateTime)
        .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));

        busWarehousingFilingStockService.beforeReturnPage(page);

        return BeanUtils.copyToPage(page, WarehousingFilingStockVo.class);
    }

    @DataBindFieldConvert
    @Override
    public WarehousingFilingStockVo detail(String id) {
    IWarehousingFilingStockService warehousingFilingStockService = SpringUtil.getBean(this.getClass());
        WarehousingFilingStockVo warehousingFilingStockVo = BeanUtils.copyByClass(warehousingFilingStockService.getById(id), WarehousingFilingStockVo.class);

        busWarehousingFilingStockService.beforeReturnDetail(warehousingFilingStockVo);

        return warehousingFilingStockVo;
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingStockVo> detailList(List<String> ids) {
        List<WarehousingFilingStock> warehousingFilingStockList =
         super.lambdaQuery()
        .in(WarehousingFilingStock::getId, ids)
        .ne(WarehousingFilingStock::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingStock::getUpdateTime).list();

        List<WarehousingFilingStockVo> warehousingFilingStockVoList = BeanUtils.copyToList(warehousingFilingStockList, WarehousingFilingStockVo.class);
        busWarehousingFilingStockService.beforeReturnDetailList(warehousingFilingStockVoList);

        return warehousingFilingStockVoList;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(WarehousingFilingStockSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingStock warehousingFilingStock = BeanUtils.copyByClass(param, WarehousingFilingStock.class);
        warehousingFilingStock.setId(IdUtil.simpleUUID());
        warehousingFilingStock.setCreateBy(loginUser.getName());
        warehousingFilingStock.setUpdateBy(loginUser.getName());
        warehousingFilingStock.setUpdateTime(LocalDateTime.now());
        warehousingFilingStock.setCreateTime(LocalDateTime.now());
        warehousingFilingStock.setCzbz(CzBzEnum.I.getCzBz());

        WarehousingFilingStock unx = getByUnx(param.getDwdm(),param.getKddm());
        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在"+"仓储单位统一社会信用代码和"+"库点编号"+"相同的数据");

        busWarehousingFilingStockService.beforeDoSave(warehousingFilingStock);

        super.save(warehousingFilingStock);
        param.setId(warehousingFilingStock.getId());
        return warehousingFilingStock.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(WarehousingFilingStockSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingStock warehousingFilingStock = BeanUtils.copyByClass(param, WarehousingFilingStock.class);
        warehousingFilingStock.setCzbz(CzBzEnum.U.getCzBz());
        warehousingFilingStock.setUpdateBy(loginUser.getName());
        warehousingFilingStock.setUpdateTime(LocalDateTime.now());

        WarehousingFilingStock unx = getByUnx(param.getDwdm(),param.getKddm());
        if (ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在"+"仓储单位统一社会信用代码和"+"库点编号"+"相同的数据");
        }

        busWarehousingFilingStockService.beforeDoUpdate(warehousingFilingStock);

        super.updateById(warehousingFilingStock);
        param.setId(warehousingFilingStock.getId());
        return warehousingFilingStock.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();

        busWarehousingFilingStockService.beforeDoRemove(ids);

        return super.lambdaUpdate()
        .in(WarehousingFilingStock::getId, ids)
        .set(WarehousingFilingStock::getCzbz , CzBzEnum.D.getCzBz())
        .set(WarehousingFilingStock::getUpdateBy ,loginUser.getName())
        .set(WarehousingFilingStock::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean saveOrUpdateBatchData(List<WarehousingFilingStockSaveParam> params) {
        if(CollectionUtils.isNotEmpty(params)){
            for (int i = 0; i < params.size(); i++) {
                WarehousingFilingStockSaveParam warehousingFilingStockSaveParam = params.get(i);
                if(StringUtil.isNullOrEmpty(warehousingFilingStockSaveParam.getId())){
                    saveData(warehousingFilingStockSaveParam);
                }else{
                    updateData(warehousingFilingStockSaveParam);
                }
            }
        }
        return true;
    }

    @Override
    public Boolean saveOrUpdateBatchDataByOtherSystem(List<WarehousingFilingStockSaveParam> params) {
        Boolean returnStatus = true;
        List<WarehousingFilingStock> warehousingFilingStocks = BeanUtils.copyToList(params, WarehousingFilingStock.class);
        if(CollectionUtils.isNotEmpty(warehousingFilingStocks)){
            returnStatus = super.saveOrUpdateBatch(warehousingFilingStocks);
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
        List<WarehousingFilingStockExcelVo> excelVoList = ExcelImportUtil.importExcel(file.getInputStream(), WarehousingFilingStockExcelVo.class, params);
        Assert.state(CollectionUtils.isNotEmpty(excelVoList), "导入数据为空，请重新导入！");
        List<WarehousingFilingStockSaveParam> saveList = BeanUtils.copyToList(excelVoList, WarehousingFilingStockSaveParam.class);
        Assert.state(CollectionUtils.isNotEmpty(saveList), "导入数据为空，请重新导入！");
        for (int i = 0; i < saveList.size(); i++) {
            updateOrSaveByUnx(saveList.get(i));
        }
    }
                        
    @Override
    public void exportData(WarehousingFilingStockPageParam pageParam) {
        List<WarehousingFilingStock> WarehousingFilingStockList = super.lambdaQuery()
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()), WarehousingFilingStock::getId, pageParam.getId())
            .eq(CollectionUtils.isNotEmpty(pageParam.getIds()), WarehousingFilingStock::getId, pageParam.getIds())
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()),  WarehousingFilingStock::getId, pageParam.getId())
            .eq(ObjectUtils.isNotEmpty(pageParam.getCompanyId()),  WarehousingFilingStock::getCompanyId, pageParam.getCompanyId())
            .eq(ObjectUtils.isNotEmpty(pageParam.getDwdm()),  WarehousingFilingStock::getDwdm, pageParam.getDwdm())
            .eq(ObjectUtils.isNotEmpty(pageParam.getDwmc()),  WarehousingFilingStock::getDwmc, pageParam.getDwmc())
            .eq(ObjectUtils.isNotEmpty(pageParam.getKddm()),  WarehousingFilingStock::getKddm, pageParam.getKddm())
            .eq(ObjectUtils.isNotEmpty(pageParam.getKdmc()),  WarehousingFilingStock::getKdmc, pageParam.getKdmc())
            .ne(WarehousingFilingStock::getCzbz, CzBzEnum.D.getCzBz())
            .orderByDesc(WarehousingFilingStock::getUpdateTime).list();
        List<WarehousingFilingStockVo> voList = BeanUtils.copyToList(WarehousingFilingStockList, WarehousingFilingStockVo.class);
        DataBindHandleBootstrap.dataHandConvert(voList, 2);
        List<WarehousingFilingStockExcelVo> excelVoList = BeanUtils.copyToList(voList, WarehousingFilingStockExcelVo.class);
        ZtExcelBuildUtil.buildExcelExport(WarehousingFilingStockExcelVo.class, "仓储备案-仓储库点信息").setData(excelVoList).doWebExport();
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingStockVo> listByMainId(String companyId) {
        List<WarehousingFilingStock> list = super.lambdaQuery()
        .eq(WarehousingFilingStock::getCompanyId, companyId)
        .ne(WarehousingFilingStock::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingStock::getUpdateTime).list();
        return BeanUtils.copyToList(list, WarehousingFilingStockVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean deleteByMainId(String companyId) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
        .eq(WarehousingFilingStock::getCompanyId, companyId)
        .set(WarehousingFilingStock::getCzbz , CzBzEnum.D.getCzBz())
        .set(WarehousingFilingStock::getUpdateBy ,loginUser.getName())
        .set(WarehousingFilingStock::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Override
    public List<WarehousingFilingStockSaveParam> listByMainIdWithSaveParam(String companyId) {
        List<WarehousingFilingStock> warehousingFilingStockList = super.lambdaQuery()
        .eq(WarehousingFilingStock::getCompanyId, companyId)
        .ne(WarehousingFilingStock::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingStock::getUpdateTime).list();
        return BeanUtils.copyToList(warehousingFilingStockList, WarehousingFilingStockSaveParam.class);
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingStockVo> listByMainIdWithHistory(String companyId) {
        List<WarehousingFilingStock> list = super.lambdaQuery()
            .eq(WarehousingFilingStock::getCompanyId, companyId)
        .orderByDesc(WarehousingFilingStock::getUpdateTime).list();
        return BeanUtils.copyToList(list, WarehousingFilingStockVo.class);
    }

    @Override
    public List<WarehousingFilingStockSaveParam> listByMainIdWithSaveParamWithHistory(String companyId) {
        List<WarehousingFilingStock> warehousingFilingStockList = super.lambdaQuery()
            .eq(WarehousingFilingStock::getCompanyId, companyId)
        .orderByDesc(WarehousingFilingStock::getUpdateTime).list();
        return BeanUtils.copyToList(warehousingFilingStockList, WarehousingFilingStockSaveParam.class);
    }

    @Override
    public WarehousingFilingStock getByUnx(String dwdm,String kddm) {
        return super.lambdaQuery()
            .select(WarehousingFilingStock::getId)
            .eq(WarehousingFilingStock::getDwdm, dwdm)
            .eq(WarehousingFilingStock::getKddm, kddm)
            .ne(WarehousingFilingStock::getCzbz, CzBzEnum.D.getCzBz())
            .oneOpt()
            .map(WarehousingFilingStock::getId)
            .map(this::getById)
            .orElse(null);
    }

    @Override
    public Boolean updateOrSaveByUnx(WarehousingFilingStockSaveParam saveParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingStock unx = getByUnx(saveParam.getDwdm(),saveParam.getKddm());
        if (ObjectUtils.isNotEmpty(unx)) {
            return super.lambdaUpdate()
                .eq(ObjectUtils.isNotEmpty(saveParam.getDwdm()),WarehousingFilingStock::getDwdm,saveParam.getDwdm())
                .eq(ObjectUtils.isNotEmpty(saveParam.getKddm()),WarehousingFilingStock::getKddm,saveParam.getKddm())
                .set(ObjectUtils.isNotEmpty(saveParam.getCompanyId()),WarehousingFilingStock::getCompanyId,saveParam.getCompanyId())
                .set(ObjectUtils.isNotEmpty(saveParam.getDwdm()),WarehousingFilingStock::getDwdm,saveParam.getDwdm())
                .set(ObjectUtils.isNotEmpty(saveParam.getDwmc()),WarehousingFilingStock::getDwmc,saveParam.getDwmc())
                .set(ObjectUtils.isNotEmpty(saveParam.getKddm()),WarehousingFilingStock::getKddm,saveParam.getKddm())
                .set(ObjectUtils.isNotEmpty(saveParam.getKdmc()),WarehousingFilingStock::getKdmc,saveParam.getKdmc())
                .set(ObjectUtils.isNotEmpty(saveParam.getZdmj()),WarehousingFilingStock::getZdmj,saveParam.getZdmj())
                .set(ObjectUtils.isNotEmpty(saveParam.getWhcr()),WarehousingFilingStock::getWhcr,saveParam.getWhcr())
                .set(ObjectUtils.isNotEmpty(saveParam.getYggr()),WarehousingFilingStock::getYggr,saveParam.getYggr())
                .set(ObjectUtils.isNotEmpty(saveParam.getXzqhdm()),WarehousingFilingStock::getXzqhdm,saveParam.getXzqhdm())
                .set(ObjectUtils.isNotEmpty(saveParam.getZyjhys()),WarehousingFilingStock::getZyjhys,saveParam.getZyjhys())
                .set(ObjectUtils.isNotEmpty(saveParam.getLycgjynl()),WarehousingFilingStock::getLycgjynl,saveParam.getLycgjynl())
                .set(ObjectUtils.isNotEmpty(saveParam.getLypzjynl()),WarehousingFilingStock::getLypzjynl,saveParam.getLypzjynl())
                .set(ObjectUtils.isNotEmpty(saveParam.getZbywwxy()),WarehousingFilingStock::getZbywwxy,saveParam.getZbywwxy())
                .set(ObjectUtils.isNotEmpty(saveParam.getZbywwry()),WarehousingFilingStock::getZbywwry,saveParam.getZbywwry())
                .set(WarehousingFilingStock::getCzbz, CzBzEnum.U.getCzBz())
                .set(WarehousingFilingStock::getUpdateBy, loginUser.getName())
                .set(WarehousingFilingStock::getUpdateTime, LocalDateTime.now())
                .update();
        } else {
            WarehousingFilingStock warehousingFilingStock = BeanUtils.copyByClass(saveParam, WarehousingFilingStock.class);
            warehousingFilingStock.setId(IdUtil.simpleUUID());
            warehousingFilingStock.setCreateBy(loginUser.getName());
            warehousingFilingStock.setUpdateBy(loginUser.getName());
            warehousingFilingStock.setUpdateTime(LocalDateTime.now());
            warehousingFilingStock.setCreateTime(LocalDateTime.now());
            warehousingFilingStock.setCzbz(CzBzEnum.I.getCzBz());
            return super.save(warehousingFilingStock);
        }
    }

    @DataBindService(strategy = DataBindWarehousingFilingStock.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, warehousingFilingStockMapper);
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