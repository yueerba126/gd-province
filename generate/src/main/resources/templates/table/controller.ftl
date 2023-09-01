package ${controllerUrl};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${serviceUrl}.I${entityName}Service;
import ${pageParamUrl}.${entityName}PageParam;
import ${saveParamUrl}.${entityName}SaveParam;
import ${voUrl}.${entityName}Vo;
<#if treeList?? && (treeList?size > 0) >
import ${voUrl}.${entityName}TreeVo;
</#if>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.sydata.filing.vo.FileVo;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:TODO(${entityComment}API接口层)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 *
 */
@Api(tags = "${entityComment}",value = "${entityComment}")
@Validated
@RestController
@RequestMapping("/${requestMappingPrefix}/${objectName}")
public class ${entityName}Controller {

    @Resource
    private I${entityName}Service ${entityName?uncap_first}Service;

    <#if treeList?? && (treeList?size > 0) >
        <#if mainTableIdList?? && (mainTableIdList?size > 0) >
    @ApiOperation("${entityComment}树形结构")
    @PostMapping("/tree")
    public List<${entityName}TreeVo> tree(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>@RequestParam("${primaryKeyInfo.property}") ${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >@RequestParam("${primaryKeyInfo.property}") ${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>) {
        return ${entityName?uncap_first}Service.treeList(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.property},<#else >${primaryKeyInfo.property}</#if></#list>);
    }

        <#else>
    @ApiOperation("${entityComment}树形结构")
    @PostMapping("/tree")
    public List<${entityName}TreeVo> tree() {
        return ${entityName?uncap_first}Service.treeList();
    }

        </#if>
    </#if>
    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<${entityName}Vo> page(@RequestBody @Valid ${entityName}PageParam pageParam) {
        return ${entityName?uncap_first}Service.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ${entityName}Vo detail(@RequestParam("id") String id) {
        return ${entityName?uncap_first}Service.detail(id);
    }

    <#if mainTableIdList?? && (mainTableIdList?size > 0) >
    @ApiOperation("根据主表ID，查询从表列表")
    @PostMapping("/listByMainId")
    public List<${entityName}Vo> listByMainId(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>@RequestParam("${primaryKeyInfo.property}") ${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >@RequestParam("${primaryKeyInfo.property}") ${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>) {
        return ${entityName?uncap_first}Service.listByMainId(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.property},<#else >${primaryKeyInfo.property}</#if></#list>);
    }

    @ApiOperation("根据主表ID，删除从表")
    @PostMapping("/deleteByMainId")
    public Boolean deleteByMainId(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>@RequestParam("${primaryKeyInfo.property}") ${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >@RequestParam("${primaryKeyInfo.property}") ${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>) {
        return ${entityName?uncap_first}Service.deleteByMainId(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.property},<#else >${primaryKeyInfo.property}</#if></#list>);
    }

    </#if>
    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ${entityName}SaveParam param) {
        return ${entityName?uncap_first}Service.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ${entityName}SaveParam param) {
        return ${entityName?uncap_first}Service.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return ${entityName?uncap_first}Service.removeData(ids);
    }

    @ApiOperation("批量新增或修改")
    @PostMapping("/saveOrUpdateBatchData")
    public Boolean saveOrUpdateBatchData(@RequestBody @Valid List<${entityName}SaveParam> param) {
        return ${entityName?uncap_first}Service.saveOrUpdateBatchData(param);
    }

    @ApiOperation("导入")
    @PostMapping("/import")
    public void importData(@NotNull(message = "文件不能为空") @RequestPart("file") MultipartFile file) {
        ${entityName?uncap_first}Service.importData(file);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public void export(@RequestBody @Valid ${entityName}PageParam pageParam) {
        ${entityName?uncap_first}Service.exportData(pageParam);
    }
    <#if upDownList?? && (upDownList?size > 0) >

    @ApiOperation("上传文件")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileVo upload(@Valid MultipartFile file) {
        return ${entityName?uncap_first}Service.uploadUse(file);
    }

    @ApiOperation("批量上传文件")
    @PostMapping(value = "/batchUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<FileVo> upload(@RequestParam(value = "files")  MultipartFile[] files) {
        return ${entityName?uncap_first}Service.batchUploadUse(files);
    }

    @ApiOperation("下载文件")
    @PostMapping("/download")
    public void download(@RequestBody FileVo fileVo) {
        ${entityName?uncap_first}Service.download(fileVo);
    }

    @ApiOperation("批量下载文件")
    @PostMapping("/batchDownload")
    public void downloadToZip(@RequestBody List<FileVo> fileVos) {
        ${entityName?uncap_first}Service.downloadToZip(fileVos);
    }
    </#if>
                
}