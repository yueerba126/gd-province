package ${serviceImplUrl};

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
import ${entityUrl}.${entityName};
import ${daoUrl}.${entityName}Mapper;
import ${serviceUrl}.I${entityName}Service;
import ${serviceUrl}.IBus${entityName}Service;
import ${pageParamUrl}.${entityName}PageParam;
import ${saveParamUrl}.${entityName}SaveParam;
import ${voUrl}.${entityName}Vo;
import ${voUrl}.${entityName}ExcelVo;
import ${voUrl}.FileVo;
<#if treeList?? && (treeList?size > 0) >
import ${voUrl}.${entityName}TreeVo;
import com.sydata.framework.core.util.TreeUtils;
</#if>
import ${dataBindUrl}.DataBind${entityName};
import lombok.SneakyThrows;
import com.sydata.common.api.enums.CzBzEnum;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import com.sydata.common.file.service.IFileStorageService;

/**   
 * @Description:TODO(${entityComment}服务实现)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 * 
 */
@Service("${entityName?uncap_first}Service")
public class ${entityName}ServiceImpl extends ServiceImpl<${entityName}Mapper, ${entityName}> implements I${entityName}Service  {

    final static String CACHE_NAME = "composite:${entityName?uncap_first}";

    @Resource
    private ${entityName}Mapper ${entityName?uncap_first}Mapper;

    @Resource
    private IBus${entityName}Service bus${entityName}Service;

    @Resource
    private IFileStorageService fileStorageService;

    @DataBindFieldConvert
    @Override
    public Page<${entityName}Vo> pages(${entityName}PageParam pageParam) {
        Page<${entityName}> page = super.lambdaQuery()
        <#list pageParamInfoList as pa>
        <#if "${pa.javaType}"?contains("LocalDate")>
        .ge(ObjectUtils.isNotEmpty(pageParam.getBegin${pa.property?cap_first}()),  ${entityName}::get${pa.property?cap_first}, pageParam.getBegin${pa.property?cap_first}())
        .le(ObjectUtils.isNotEmpty(pageParam.getEnd${pa.property?cap_first}()),  ${entityName}::get${pa.property?cap_first}, pageParam.getEnd${pa.property?cap_first}())
        <#else>
        .${pa.paramSelectType}(ObjectUtils.isNotEmpty(pageParam.get${pa.property?cap_first}()),  ${entityName}::get${pa.property?cap_first}, pageParam.get${pa.property?cap_first}())
        </#if>
        </#list>
        .ne(${entityName}::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(${entityName}::getUpdateTime)
        .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));

        bus${entityName}Service.beforeReturnPage(page);

        return BeanUtils.copyToPage(page, ${entityName}Vo.class);
    }

    @DataBindFieldConvert
    @Override
    public ${entityName}Vo detail(String id) {
    I${entityName}Service ${entityName?uncap_first}Service = SpringUtil.getBean(this.getClass());
        ${entityName}Vo ${entityName?uncap_first}Vo = BeanUtils.copyByClass(${entityName?uncap_first}Service.getById(id), ${entityName}Vo.class);

        bus${entityName}Service.beforeReturnDetail(${entityName?uncap_first}Vo);

        return ${entityName?uncap_first}Vo;
    }

    @DataBindFieldConvert
    @Override
    public List<${entityName}Vo> detailList(List<String> ids) {
        List<${entityName}> ${entityName?uncap_first}List =
         super.lambdaQuery()
        .in(${entityName}::getId, ids)
        .ne(${entityName}::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(${entityName}::getUpdateTime).list();

        List<${entityName}Vo> ${entityName?uncap_first}VoList = BeanUtils.copyToList(${entityName?uncap_first}List, ${entityName}Vo.class);
        bus${entityName}Service.beforeReturnDetailList(${entityName?uncap_first}VoList);

        return ${entityName?uncap_first}VoList;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(${entityName}SaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ${entityName} ${entityName?uncap_first} = BeanUtils.copyByClass(param, ${entityName}.class);
        ${entityName?uncap_first}.setId(IdUtil.simpleUUID());
        ${entityName?uncap_first}.setCreateBy(loginUser.getName());
        ${entityName?uncap_first}.setUpdateBy(loginUser.getName());
        ${entityName?uncap_first}.setUpdateTime(LocalDateTime.now());
        ${entityName?uncap_first}.setCreateTime(LocalDateTime.now());
        ${entityName?uncap_first}.setCzbz(CzBzEnum.I.getCzBz());

        ${entityName} unx = getByUnx(<#list primaryKeyInfoList as primaryKeyInfo><#if primaryKeyInfo_has_next>param.get${primaryKeyInfo.property?cap_first}(),<#else >param.get${primaryKeyInfo.property?cap_first}()</#if></#list>);
        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在"+<#list primaryKeyInfoList as primaryKeyInfo><#if primaryKeyInfo_has_next>"${primaryKeyInfo.comment}和"+<#else >"${primaryKeyInfo.comment}"+</#if></#list>"相同的数据");

        bus${entityName}Service.beforeDoSave(${entityName?uncap_first});

        super.save(${entityName?uncap_first});
        param.setId(${entityName?uncap_first}.getId());
        return ${entityName?uncap_first}.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(${entityName}SaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ${entityName} ${entityName?uncap_first} = BeanUtils.copyByClass(param, ${entityName}.class);
        ${entityName?uncap_first}.setCzbz(CzBzEnum.U.getCzBz());
        ${entityName?uncap_first}.setUpdateBy(loginUser.getName());
        ${entityName?uncap_first}.setUpdateTime(LocalDateTime.now());

        ${entityName} unx = getByUnx(<#list primaryKeyInfoList as primaryKeyInfo><#if primaryKeyInfo_has_next>param.get${primaryKeyInfo.property?cap_first}(),<#else >param.get${primaryKeyInfo.property?cap_first}()</#if></#list>);
        if (ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在"+<#list primaryKeyInfoList as primaryKeyInfo><#if primaryKeyInfo_has_next>"${primaryKeyInfo.comment}和"+<#else >"${primaryKeyInfo.comment}"+</#if></#list>"相同的数据");
        }

        bus${entityName}Service.beforeDoUpdate(${entityName?uncap_first});

        super.updateById(${entityName?uncap_first});
        param.setId(${entityName?uncap_first}.getId());
        return ${entityName?uncap_first}.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();

        bus${entityName}Service.beforeDoRemove(ids);

        return super.lambdaUpdate()
        .in(${entityName}::getId, ids)
        .set(${entityName}::getCzbz , CzBzEnum.D.getCzBz())
        .set(${entityName}::getUpdateBy ,loginUser.getName())
        .set(${entityName}::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean saveOrUpdateBatchData(List<${entityName}SaveParam> params) {
        if(CollectionUtils.isNotEmpty(params)){
            for (int i = 0; i < params.size(); i++) {
                ${entityName}SaveParam ${entityName?uncap_first}SaveParam = params.get(i);
                if(StringUtil.isNullOrEmpty(${entityName?uncap_first}SaveParam.getId())){
                    saveData(${entityName?uncap_first}SaveParam);
                }else{
                    updateData(${entityName?uncap_first}SaveParam);
                }
            }
        }
        return true;
    }

    @Override
    public Boolean saveOrUpdateBatchDataByOtherSystem(List<${entityName}SaveParam> params) {
        Boolean returnStatus = true;
        List<${entityName}> ${entityName?uncap_first}s = BeanUtils.copyToList(params, ${entityName}.class);
        if(CollectionUtils.isNotEmpty(${entityName?uncap_first}s)){
            returnStatus = super.saveOrUpdateBatch(${entityName?uncap_first}s);
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
        List<${entityName}ExcelVo> excelVoList = ExcelImportUtil.importExcel(file.getInputStream(), ${entityName}ExcelVo.class, params);
        Assert.state(CollectionUtils.isNotEmpty(excelVoList), "导入数据为空，请重新导入！");
        List<${entityName}SaveParam> saveList = BeanUtils.copyToList(excelVoList, ${entityName}SaveParam.class);
        Assert.state(CollectionUtils.isNotEmpty(saveList), "导入数据为空，请重新导入！");
        for (int i = 0; i < saveList.size(); i++) {
            updateOrSaveByUnx(saveList.get(i));
        }
    }
                        
    @Override
    public void exportData(${entityName}PageParam pageParam) {
        List<${entityName}> ${entityName}List = super.lambdaQuery()
            .eq(ObjectUtils.isNotEmpty(pageParam.getId()), ${entityName}::getId, pageParam.getId())
            .eq(CollectionUtils.isNotEmpty(pageParam.getIds()), ${entityName}::getId, pageParam.getIds())
            <#list pageParamInfoList as pa>
                <#if "${pa.javaType}"?contains("LocalDate")>
            .ge(ObjectUtils.isNotEmpty(pageParam.getBegin${pa.property?cap_first}()),  ${entityName}::get${pa.property?cap_first}, pageParam.getBegin${pa.property?cap_first}())
            .le(ObjectUtils.isNotEmpty(pageParam.getEnd${pa.property?cap_first}()),  ${entityName}::get${pa.property?cap_first}, pageParam.getEnd${pa.property?cap_first}())
                <#else>
            .${pa.paramSelectType}(ObjectUtils.isNotEmpty(pageParam.get${pa.property?cap_first}()),  ${entityName}::get${pa.property?cap_first}, pageParam.get${pa.property?cap_first}())
                </#if>
            </#list>
            .ne(${entityName}::getCzbz, CzBzEnum.D.getCzBz())
            .orderByDesc(${entityName}::getUpdateTime).list();
        List<${entityName}Vo> voList = BeanUtils.copyToList(${entityName}List, ${entityName}Vo.class);
        DataBindHandleBootstrap.dataHandConvert(voList, 2);
        List<${entityName}ExcelVo> excelVoList = BeanUtils.copyToList(voList, ${entityName}ExcelVo.class);
        ZtExcelBuildUtil.buildExcelExport(${entityName}ExcelVo.class, "${entityComment}信息").setData(excelVoList).doWebExport();
    }
    <#if treeList?? && (treeList?size > 0) >
        <#if mainTableIdList?? && (mainTableIdList?size > 0) >

    @DataBindFieldConvert
    @Override
    public List<${entityName}TreeVo> treeList(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>){
        List<${entityName}Vo> voList = SpringUtil.getBean(this.getClass()).listByMainId(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.property},<#else >${primaryKeyInfo.property}</#if></#list>);
        List<${entityName}TreeVo> vos = BeanUtils.copyToList(voList, ${entityName}TreeVo.class);

        return TreeUtils.toTree(vos, ${entityName}TreeVo::getId,
                    ${entityName}TreeVo::getParentId,
                    ${entityName}TreeVo::setChild, 0L);
    }
        <#else>

    @DataBindFieldConvert
    @Override
    public List<${entityName}TreeVo> treeList(){
        List<${entityName}> list = SpringUtil.getBean(this.getClass()).list();
        List<${entityName}TreeVo> vos = BeanUtils.copyToList(list, ${entityName}TreeVo.class);

        return TreeUtils.toTree(vos, ${entityName}TreeVo::getId,
                    ${entityName}TreeVo::getParentId,
                    ${entityName}TreeVo::setChild, 0L);
    }
        </#if>
    </#if>
    <#if mainTableIdList?? && (mainTableIdList?size > 0) >

    @DataBindFieldConvert
    @Override
    public List<${entityName}Vo> listByMainId(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>) {
        List<${entityName}> list = super.lambdaQuery()
        <#list mainTableIdList as primaryKeyInfo>
        .eq(${entityName}::get${primaryKeyInfo.property?cap_first}, ${primaryKeyInfo.property})
        </#list>
        .ne(${entityName}::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(${entityName}::getUpdateTime).list();
        return BeanUtils.copyToList(list, ${entityName}Vo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean deleteByMainId(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
        <#list mainTableIdList as primaryKeyInfo>
        .eq(${entityName}::get${primaryKeyInfo.property?cap_first}, ${primaryKeyInfo.property})
        </#list>
        .set(${entityName}::getCzbz , CzBzEnum.D.getCzBz())
        .set(${entityName}::getUpdateBy ,loginUser.getName())
        .set(${entityName}::getUpdateTime ,LocalDateTime.now())
        .update();
    }

    @Override
    public List<${entityName}SaveParam> listByMainIdWithSaveParam(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>) {
        List<${entityName}> ${entityName?uncap_first}List = super.lambdaQuery()
        <#list mainTableIdList as primaryKeyInfo>
        .eq(${entityName}::get${primaryKeyInfo.property?cap_first}, ${primaryKeyInfo.property})
        </#list>
        .ne(${entityName}::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(${entityName}::getUpdateTime).list();
        return BeanUtils.copyToList(${entityName?uncap_first}List, ${entityName}SaveParam.class);
    }

    @DataBindFieldConvert
    @Override
    public List<${entityName}Vo> listByMainIdWithHistory(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>) {
        List<${entityName}> list = super.lambdaQuery()
        <#list mainTableIdList as primaryKeyInfo>
            .eq(${entityName}::get${primaryKeyInfo.property?cap_first}, ${primaryKeyInfo.property})
        </#list>
        .orderByDesc(${entityName}::getUpdateTime).list();
        return BeanUtils.copyToList(list, ${entityName}Vo.class);
    }

    @Override
    public List<${entityName}SaveParam> listByMainIdWithSaveParamWithHistory(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>) {
        List<${entityName}> ${entityName?uncap_first}List = super.lambdaQuery()
        <#list mainTableIdList as primaryKeyInfo>
            .eq(${entityName}::get${primaryKeyInfo.property?cap_first}, ${primaryKeyInfo.property})
        </#list>
        .orderByDesc(${entityName}::getUpdateTime).list();
        return BeanUtils.copyToList(${entityName?uncap_first}List, ${entityName}SaveParam.class);
    }

    </#if>
    @Override
    public ${entityName} getByUnx(<#list primaryKeyInfoList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>) {
        return super.lambdaQuery()
            .select(${entityName}::getId)
            <#list primaryKeyInfoList as primaryKeyInfo>
            .eq(${entityName}::get${primaryKeyInfo.property?cap_first}, ${primaryKeyInfo.property})
            </#list>
            .ne(${entityName}::getCzbz, CzBzEnum.D.getCzBz())
            .oneOpt()
            .map(${entityName}::getId)
            .map(this::getById)
            .orElse(null);
    }

    <#if upDownList?? && (upDownList?size > 0) >
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
        ${entityName} ${entityName?uncap_first} = super.lambdaQuery()
        .eq(${entityName}::getFileId, fileVo.getFileId())
        .ne(${entityName}::getCzbz, CzBzEnum.D.getCzBz())
        .orderByDesc(${entityName}::getUpdateTime).list().stream().findFirst().orElse(null);
        if(ObjectUtils.isNotEmpty(${entityName?uncap_first})){
            fileStorageService.downloadWithFileName(fileVo.getFileId(),${entityName?uncap_first}.getFileName());
        }
    }

    @Override
    public void downloadToZip(List<FileVo> fileVos) {
        List<String> fileStorageIdList = fileVos.stream().map(e->e.getFileId()).collect(Collectors.toList());
        List<String> fileNames = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(fileStorageIdList)){
            for (int i = 0; i < fileStorageIdList.size(); i++) {
                ${entityName} ${entityName?uncap_first} = super.lambdaQuery()
                .eq(${entityName}::getFileId, fileStorageIdList.get(i))
                .ne(${entityName}::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(${entityName}::getUpdateTime).list().stream().findFirst().orElse(null);
                if(ObjectUtils.isNotEmpty(${entityName?uncap_first})){
                    fileNames.add(${entityName?uncap_first}.getFileName());
                }else {
                    fileStorageIdList.remove(i);
                    i--;
                }
            }
            fileStorageService.downloadToZip(fileStorageIdList,fileNames,"${entityComment}");
        }
    }

    </#if>
    @Override
    public Boolean updateOrSaveByUnx(${entityName}SaveParam saveParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        ${entityName} unx = getByUnx(<#list primaryKeyInfoList as primaryKeyInfo><#if primaryKeyInfo_has_next>saveParam.get${primaryKeyInfo.property?cap_first}(),<#else >saveParam.get${primaryKeyInfo.property?cap_first}()</#if></#list>);
        if (ObjectUtils.isNotEmpty(unx)) {
            return super.lambdaUpdate()
            <#list primaryKeyInfoList as primaryKeyInfo>
                .eq(ObjectUtils.isNotEmpty(saveParam.get${primaryKeyInfo.property?cap_first}()),${entityName}::get${primaryKeyInfo.property?cap_first},saveParam.get${primaryKeyInfo.property?cap_first}())
            </#list>
            <#list entityExcludeAfterList?sort_by(["id"]) as ci>
                <#if ci.property!="id">
                .set(ObjectUtils.isNotEmpty(saveParam.get${ci.property?cap_first}()),${entityName}::get${ci.property?cap_first},saveParam.get${ci.property?cap_first}())
                </#if>
            </#list>
                .set(${entityName}::getCzbz, CzBzEnum.U.getCzBz())
                .set(${entityName}::getUpdateBy, loginUser.getName())
                .set(${entityName}::getUpdateTime, LocalDateTime.now())
                .update();
        } else {
            ${entityName} ${entityName?uncap_first} = BeanUtils.copyByClass(saveParam, ${entityName}.class);
            ${entityName?uncap_first}.setId(IdUtil.simpleUUID());
            ${entityName?uncap_first}.setCreateBy(loginUser.getName());
            ${entityName?uncap_first}.setUpdateBy(loginUser.getName());
            ${entityName?uncap_first}.setUpdateTime(LocalDateTime.now());
            ${entityName?uncap_first}.setCreateTime(LocalDateTime.now());
            ${entityName?uncap_first}.setCzbz(CzBzEnum.I.getCzBz());
            return super.save(${entityName?uncap_first});
        }
    }

    @DataBindService(strategy = DataBind${entityName}.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, ${entityName?uncap_first}Mapper);
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