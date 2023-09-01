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
import com.sydata.filing.domain.WarehousingFilingPersonnel;
import com.sydata.filing.mapper.WarehousingFilingPersonnelMapper;
import com.sydata.filing.service.IWarehousingFilingPersonnelService;
import com.sydata.filing.service.IBusWarehousingFilingPersonnelService;
import com.sydata.filing.param.WarehousingFilingPersonnelPageParam;
import com.sydata.filing.param.WarehousingFilingPersonnelSaveParam;
import com.sydata.filing.vo.WarehousingFilingPersonnelVo;
import com.sydata.filing.vo.WarehousingFilingPersonnelExcelVo;
import com.sydata.filing.vo.FileVo;
import com.sydata.filing.annotation.DataBindWarehousingFilingPersonnel;
import lombok.SneakyThrows;
import com.sydata.common.api.enums.CzBzEnum;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import com.sydata.common.file.service.IFileStorageService;

/**   
 * @Description:TODO(仓储备案-仓储人员服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("warehousingFilingPersonnelService")
public class WarehousingFilingPersonnelServiceImpl extends ServiceImpl<WarehousingFilingPersonnelMapper, WarehousingFilingPersonnel> implements IWarehousingFilingPersonnelService  {

    final static String CACHE_NAME = "composite:warehousingFilingPersonnel";

    @Resource
    private WarehousingFilingPersonnelMapper warehousingFilingPersonnelMapper;

    @Resource
    private IBusWarehousingFilingPersonnelService busWarehousingFilingPersonnelService;

    @Resource
    private IFileStorageService fileStorageService;

    @DataBindFieldConvert
    @Override
    public Page<WarehousingFilingPersonnelVo> pages(WarehousingFilingPersonnelPageParam pageParam) {
        Page<WarehousingFilingPersonnel> page = super.lambdaQuery()
        .eq(ObjectUtils.isNotEmpty(pageParam.getId()),  WarehousingFilingPersonnel::getId, pageParam.getId())
        .eq(ObjectUtils.isNotEmpty(pageParam.getCompanyId()),  WarehousingFilingPersonnel::getCompanyId, pageParam.getCompanyId())
        .like(ObjectUtils.isNotEmpty(pageParam.getXm()),  WarehousingFilingPersonnel::getXm, pageParam.getXm())
        .eq(ObjectUtils.isNotEmpty(pageParam.getCyzw()),  WarehousingFilingPersonnel::getCyzw, pageParam.getCyzw())
        .eq(ObjectUtils.isNotEmpty(pageParam.getRylb()),  WarehousingFilingPersonnel::getRylb, pageParam.getRylb())
        .eq(ObjectUtils.isNotEmpty(pageParam.getRyjb()),  WarehousingFilingPersonnel::getRyjb, pageParam.getRyjb())
        .ne(WarehousingFilingPersonnel::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingPersonnel::getUpdateTime)
        .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));

        busWarehousingFilingPersonnelService.beforeReturnPage(page);

        return BeanUtils.copyToPage(page, WarehousingFilingPersonnelVo.class);
    }

    @DataBindFieldConvert
    @Override
    public WarehousingFilingPersonnelVo detail(String id) {
    IWarehousingFilingPersonnelService warehousingFilingPersonnelService = SpringUtil.getBean(this.getClass());
        WarehousingFilingPersonnelVo warehousingFilingPersonnelVo = BeanUtils.copyByClass(warehousingFilingPersonnelService.getById(id), WarehousingFilingPersonnelVo.class);

        busWarehousingFilingPersonnelService.beforeReturnDetail(warehousingFilingPersonnelVo);

        return warehousingFilingPersonnelVo;
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingPersonnelVo> detailList(List<String> ids) {
        List<WarehousingFilingPersonnel> warehousingFilingPersonnelList =
         super.lambdaQuery()
        .in(WarehousingFilingPersonnel::getId, ids)
        .ne(WarehousingFilingPersonnel::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingPersonnel::getUpdateTime).list();

        List<WarehousingFilingPersonnelVo> warehousingFilingPersonnelVoList = BeanUtils.copyToList(warehousingFilingPersonnelList, WarehousingFilingPersonnelVo.class);
        busWarehousingFilingPersonnelService.beforeReturnDetailList(warehousingFilingPersonnelVoList);

        return warehousingFilingPersonnelVoList;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(WarehousingFilingPersonnelSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingPersonnel warehousingFilingPersonnel = BeanUtils.copyByClass(param, WarehousingFilingPersonnel.class);
        warehousingFilingPersonnel.setId(IdUtil.simpleUUID());
        warehousingFilingPersonnel.setCreateBy(loginUser.getName());
        warehousingFilingPersonnel.setUpdateBy(loginUser.getName());
        warehousingFilingPersonnel.setUpdateTime(LocalDateTime.now());
        warehousingFilingPersonnel.setCreateTime(LocalDateTime.now());
        warehousingFilingPersonnel.setCzbz(CzBzEnum.I.getCzBz());

        WarehousingFilingPersonnel unx = getByUnx(param.getSfzhm());
        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在"+"身份证号码"+"相同的数据");

        busWarehousingFilingPersonnelService.beforeDoSave(warehousingFilingPersonnel);

        super.save(warehousingFilingPersonnel);
        param.setId(warehousingFilingPersonnel.getId());
        return warehousingFilingPersonnel.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(WarehousingFilingPersonnelSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingPersonnel warehousingFilingPersonnel = BeanUtils.copyByClass(param, WarehousingFilingPersonnel.class);
        warehousingFilingPersonnel.setCzbz(CzBzEnum.U.getCzBz());
        warehousingFilingPersonnel.setUpdateBy(loginUser.getName());
        warehousingFilingPersonnel.setUpdateTime(LocalDateTime.now());

        WarehousingFilingPersonnel unx = getByUnx(param.getSfzhm());
        if (ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在"+"身份证号码"+"相同的数据");
        }

        busWarehousingFilingPersonnelService.beforeDoUpdate(warehousingFilingPersonnel);

        super.updateById(warehousingFilingPersonnel);
        param.setId(warehousingFilingPersonnel.getId());
        return warehousingFilingPersonnel.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();

        busWarehousingFilingPersonnelService.beforeDoRemove(ids);

        return super.lambdaUpdate()
        .in(WarehousingFilingPersonnel::getId, ids)
        .set(WarehousingFilingPersonnel::getCzbz , CzBzEnum.D.getCzBz())
        .set(WarehousingFilingPersonnel::getUpdateBy ,loginUser.getName())
        .set(WarehousingFilingPersonnel::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean saveOrUpdateBatchData(List<WarehousingFilingPersonnelSaveParam> params) {
        if(CollectionUtils.isNotEmpty(params)){
            for (int i = 0; i < params.size(); i++) {
                WarehousingFilingPersonnelSaveParam warehousingFilingPersonnelSaveParam = params.get(i);
                if(StringUtil.isNullOrEmpty(warehousingFilingPersonnelSaveParam.getId())){
                    saveData(warehousingFilingPersonnelSaveParam);
                }else{
                    updateData(warehousingFilingPersonnelSaveParam);
                }
            }
        }
        return true;
    }

    @Override
    public Boolean saveOrUpdateBatchDataByOtherSystem(List<WarehousingFilingPersonnelSaveParam> params) {
        Boolean returnStatus = true;
        List<WarehousingFilingPersonnel> warehousingFilingPersonnels = BeanUtils.copyToList(params, WarehousingFilingPersonnel.class);
        if(CollectionUtils.isNotEmpty(warehousingFilingPersonnels)){
            returnStatus = super.saveOrUpdateBatch(warehousingFilingPersonnels);
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
        List<WarehousingFilingPersonnelExcelVo> excelVoList = ExcelImportUtil.importExcel(file.getInputStream(), WarehousingFilingPersonnelExcelVo.class, params);
        Assert.state(CollectionUtils.isNotEmpty(excelVoList), "导入数据为空，请重新导入！");
        List<WarehousingFilingPersonnelSaveParam> saveList = BeanUtils.copyToList(excelVoList, WarehousingFilingPersonnelSaveParam.class);
        Assert.state(CollectionUtils.isNotEmpty(saveList), "导入数据为空，请重新导入！");
        for (int i = 0; i < saveList.size(); i++) {
            updateOrSaveByUnx(saveList.get(i));
        }
    }
                        
    @Override
    public void exportData(WarehousingFilingPersonnelPageParam pageParam) {
        List<WarehousingFilingPersonnel> WarehousingFilingPersonnelList = super.lambdaQuery()
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()), WarehousingFilingPersonnel::getId, pageParam.getId())
            .eq(CollectionUtils.isNotEmpty(pageParam.getIds()), WarehousingFilingPersonnel::getId, pageParam.getIds())
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()),  WarehousingFilingPersonnel::getId, pageParam.getId())
            .eq(ObjectUtils.isNotEmpty(pageParam.getCompanyId()),  WarehousingFilingPersonnel::getCompanyId, pageParam.getCompanyId())
            .like(ObjectUtils.isNotEmpty(pageParam.getXm()),  WarehousingFilingPersonnel::getXm, pageParam.getXm())
            .eq(ObjectUtils.isNotEmpty(pageParam.getCyzw()),  WarehousingFilingPersonnel::getCyzw, pageParam.getCyzw())
            .eq(ObjectUtils.isNotEmpty(pageParam.getRylb()),  WarehousingFilingPersonnel::getRylb, pageParam.getRylb())
            .eq(ObjectUtils.isNotEmpty(pageParam.getRyjb()),  WarehousingFilingPersonnel::getRyjb, pageParam.getRyjb())
            .ne(WarehousingFilingPersonnel::getCzbz, CzBzEnum.D.getCzBz())
            .orderByDesc(WarehousingFilingPersonnel::getUpdateTime).list();
        List<WarehousingFilingPersonnelVo> voList = BeanUtils.copyToList(WarehousingFilingPersonnelList, WarehousingFilingPersonnelVo.class);
        DataBindHandleBootstrap.dataHandConvert(voList, 2);
        List<WarehousingFilingPersonnelExcelVo> excelVoList = BeanUtils.copyToList(voList, WarehousingFilingPersonnelExcelVo.class);
        ZtExcelBuildUtil.buildExcelExport(WarehousingFilingPersonnelExcelVo.class, "仓储备案-仓储人员信息").setData(excelVoList).doWebExport();
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingPersonnelVo> listByMainId(String companyId) {
        List<WarehousingFilingPersonnel> list = super.lambdaQuery()
        .eq(WarehousingFilingPersonnel::getCompanyId, companyId)
        .ne(WarehousingFilingPersonnel::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingPersonnel::getUpdateTime).list();
        return BeanUtils.copyToList(list, WarehousingFilingPersonnelVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean deleteByMainId(String companyId) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
        .eq(WarehousingFilingPersonnel::getCompanyId, companyId)
        .set(WarehousingFilingPersonnel::getCzbz , CzBzEnum.D.getCzBz())
        .set(WarehousingFilingPersonnel::getUpdateBy ,loginUser.getName())
        .set(WarehousingFilingPersonnel::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Override
    public List<WarehousingFilingPersonnelSaveParam> listByMainIdWithSaveParam(String companyId) {
        List<WarehousingFilingPersonnel> warehousingFilingPersonnelList = super.lambdaQuery()
        .eq(WarehousingFilingPersonnel::getCompanyId, companyId)
        .ne(WarehousingFilingPersonnel::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(WarehousingFilingPersonnel::getUpdateTime).list();
        return BeanUtils.copyToList(warehousingFilingPersonnelList, WarehousingFilingPersonnelSaveParam.class);
    }

    @DataBindFieldConvert
    @Override
    public List<WarehousingFilingPersonnelVo> listByMainIdWithHistory(String companyId) {
        List<WarehousingFilingPersonnel> list = super.lambdaQuery()
            .eq(WarehousingFilingPersonnel::getCompanyId, companyId)
        .orderByDesc(WarehousingFilingPersonnel::getUpdateTime).list();
        return BeanUtils.copyToList(list, WarehousingFilingPersonnelVo.class);
    }

    @Override
    public List<WarehousingFilingPersonnelSaveParam> listByMainIdWithSaveParamWithHistory(String companyId) {
        List<WarehousingFilingPersonnel> warehousingFilingPersonnelList = super.lambdaQuery()
            .eq(WarehousingFilingPersonnel::getCompanyId, companyId)
        .orderByDesc(WarehousingFilingPersonnel::getUpdateTime).list();
        return BeanUtils.copyToList(warehousingFilingPersonnelList, WarehousingFilingPersonnelSaveParam.class);
    }

    @Override
    public WarehousingFilingPersonnel getByUnx(String sfzhm) {
        return super.lambdaQuery()
            .select(WarehousingFilingPersonnel::getId)
            .eq(WarehousingFilingPersonnel::getSfzhm, sfzhm)
            .ne(WarehousingFilingPersonnel::getCzbz, CzBzEnum.D.getCzBz())
            .oneOpt()
            .map(WarehousingFilingPersonnel::getId)
            .map(this::getById)
            .orElse(null);
    }

    @Override
    public Boolean updateOrSaveByUnx(WarehousingFilingPersonnelSaveParam saveParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingPersonnel unx = getByUnx(saveParam.getSfzhm());
        if (ObjectUtils.isNotEmpty(unx)) {
            return super.lambdaUpdate()
                .eq(ObjectUtils.isNotEmpty(saveParam.getSfzhm()),WarehousingFilingPersonnel::getSfzhm,saveParam.getSfzhm())
                .set(ObjectUtils.isNotEmpty(saveParam.getCompanyId()),WarehousingFilingPersonnel::getCompanyId,saveParam.getCompanyId())
                .set(ObjectUtils.isNotEmpty(saveParam.getXm()),WarehousingFilingPersonnel::getXm,saveParam.getXm())
                .set(ObjectUtils.isNotEmpty(saveParam.getSfzhm()),WarehousingFilingPersonnel::getSfzhm,saveParam.getSfzhm())
                .set(ObjectUtils.isNotEmpty(saveParam.getXl()),WarehousingFilingPersonnel::getXl,saveParam.getXl())
                .set(ObjectUtils.isNotEmpty(saveParam.getXb()),WarehousingFilingPersonnel::getXb,saveParam.getXb())
                .set(ObjectUtils.isNotEmpty(saveParam.getNl()),WarehousingFilingPersonnel::getNl,saveParam.getNl())
                .set(ObjectUtils.isNotEmpty(saveParam.getZw()),WarehousingFilingPersonnel::getZw,saveParam.getZw())
                .set(ObjectUtils.isNotEmpty(saveParam.getCyzw()),WarehousingFilingPersonnel::getCyzw,saveParam.getCyzw())
                .set(ObjectUtils.isNotEmpty(saveParam.getCynx()),WarehousingFilingPersonnel::getCynx,saveParam.getCynx())
                .set(ObjectUtils.isNotEmpty(saveParam.getRylb()),WarehousingFilingPersonnel::getRylb,saveParam.getRylb())
                .set(ObjectUtils.isNotEmpty(saveParam.getRyjb()),WarehousingFilingPersonnel::getRyjb,saveParam.getRyjb())
                .set(ObjectUtils.isNotEmpty(saveParam.getZc()),WarehousingFilingPersonnel::getZc,saveParam.getZc())
                .set(ObjectUtils.isNotEmpty(saveParam.getZyzgzsbh()),WarehousingFilingPersonnel::getZyzgzsbh,saveParam.getZyzgzsbh())
                .set(ObjectUtils.isNotEmpty(saveParam.getZyzgzsmc()),WarehousingFilingPersonnel::getZyzgzsmc,saveParam.getZyzgzsmc())
                .set(ObjectUtils.isNotEmpty(saveParam.getFzjg()),WarehousingFilingPersonnel::getFzjg,saveParam.getFzjg())
                .set(ObjectUtils.isNotEmpty(saveParam.getDzyj()),WarehousingFilingPersonnel::getDzyj,saveParam.getDzyj())
                .set(ObjectUtils.isNotEmpty(saveParam.getLxfs()),WarehousingFilingPersonnel::getLxfs,saveParam.getLxfs())
                .set(WarehousingFilingPersonnel::getCzbz, CzBzEnum.U.getCzBz())
                .set(WarehousingFilingPersonnel::getUpdateBy, loginUser.getName())
                .set(WarehousingFilingPersonnel::getUpdateTime, LocalDateTime.now())
                .update();
        } else {
            WarehousingFilingPersonnel warehousingFilingPersonnel = BeanUtils.copyByClass(saveParam, WarehousingFilingPersonnel.class);
            warehousingFilingPersonnel.setId(IdUtil.simpleUUID());
            warehousingFilingPersonnel.setCreateBy(loginUser.getName());
            warehousingFilingPersonnel.setUpdateBy(loginUser.getName());
            warehousingFilingPersonnel.setUpdateTime(LocalDateTime.now());
            warehousingFilingPersonnel.setCreateTime(LocalDateTime.now());
            warehousingFilingPersonnel.setCzbz(CzBzEnum.I.getCzBz());
            return super.save(warehousingFilingPersonnel);
        }
    }

    @DataBindService(strategy = DataBindWarehousingFilingPersonnel.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, warehousingFilingPersonnelMapper);
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