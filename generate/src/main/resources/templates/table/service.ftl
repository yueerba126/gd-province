package ${serviceUrl};

import ${entityUrl}.${entityName};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import ${entityUrl}.${entityName};
import ${saveParamUrl}.${entityName}SaveParam;
import ${pageParamUrl}.${entityName}PageParam;
import ${voUrl}.${entityName}Vo;
import ${voUrl}.FileVo;
<#if treeList?? && (treeList?size > 0) >
import ${voUrl}.${entityName}TreeVo;
</#if>
import com.sydata.framework.databind.domain.DataBindQuery;
import java.util.Collection;
import java.util.List;

/**   
 * @Description:TODO(${entityComment}服务层)
 * @date ${createDate}
 * @version: ${version}
 * @author: ${author}
 * 
 */
public interface I${entityName}Service extends IService<${entityName}> {

    /**
    * 分页列表
    *
    * @param pageParam 分页参数
    * @return 分页列表
    */
    Page<${entityName}Vo> pages(${entityName}PageParam pageParam);

    /**
    * 详情
    *
    * @param id 主键ID
    * @return 详情
    */
    ${entityName}Vo detail(String id);

    /**
    * 详情列表
    *
    * @param ids 主键ID集合
    * @return 详情
    */
    List<${entityName}Vo> detailList(List<String> ids);

    /**
    * 新增
    *
    * @param param 参数
    * @return 主键ID
    */
    String saveData(${entityName}SaveParam param);

    /**
    * 修改
    *
    * @param param 参数
    * @return 主键ID
    */
    String updateData(${entityName}SaveParam param);

    /**
    * 删除
    *
    * @param ids 参数
    * @return 主键ID
    */
    Boolean removeData(List<String> ids);

    /**
    * 批量新增或修改（这里需要我们生成ID）
    *
    * @param params 参数
    */
    Boolean saveOrUpdateBatchData(List<${entityName}SaveParam> params);

    /**
    * 其他系统批量新增或修改（这里不需要我们生成ID）
    *
    * @param params 参数
    */
    Boolean saveOrUpdateBatchDataByOtherSystem(List<${entityName}SaveParam> params);

    /**
    * 导入
    *
    * @param file
    */
    void importData(MultipartFile file);

    /**
    * 导出
    *
    * @param pageParam 参数
    */
    void exportData(${entityName}PageParam pageParam);
    <#if treeList?? && (treeList?size > 0) >
        <#if mainTableIdList?? && (mainTableIdList?size > 0) >

    /**
    * ${entityComment}树形列表
    * @return ${entityComment}树形列表
    */
    List<${entityName}TreeVo> treeList(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>);
        <#else>
    /**
    * ${entityComment}树形列表
    * @return ${entityComment}树形列表
    */
    List<${entityName}TreeVo> treeList();
        </#if>
    </#if>
    <#if mainTableIdList?? && (mainTableIdList?size > 0) >

    /**
    * 根据主表ID，查询从表列表
    *
    * @return 列表
    */
    List<${entityName}Vo> listByMainId(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>);

    /**
    * 根据主表ID，删除从表数据
    *
    * @return 列表
    */
    Boolean deleteByMainId(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>);

    /**
    * 获取保存参数
    *
    * @return 列表
    */
    List<${entityName}SaveParam> listByMainIdWithSaveParam(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>);

    /**
    * 根据主表ID，查询从表列表（包含历史）
    *
    * @return 列表
    */
    List<${entityName}Vo> listByMainIdWithHistory(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>);

    /**
    * 获取保存参数（包含历史）
    *
    * @return 列表
    */
    List<${entityName}SaveParam> listByMainIdWithSaveParamWithHistory(<#list mainTableIdList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>);
    </#if>

    /**
    * 唯一性
    */
    ${entityName} getByUnx(<#list primaryKeyInfoList as primaryKeyInfo><#if primaryKeyInfo_has_next>${primaryKeyInfo.javaType} ${primaryKeyInfo.property},<#else >${primaryKeyInfo.javaType} ${primaryKeyInfo.property}</#if></#list>);

    /**
    * 唯一性更新
    *
    * @param saveParam 参数
    * @return 主键ID
    */
    Boolean updateOrSaveByUnx(${entityName}SaveParam saveParam);
    <#if upDownList?? && (upDownList?size > 0) >

    /**
    * @Description 上传文件
    *
    * @Param file
    * @return FileVo
    **/
    FileVo uploadUse(MultipartFile file);

    /**
    * @Description 批量上传文件
    *
    * @Param [files]
    * @return List<FileVo>
    **/
    List<FileVo> batchUploadUse(MultipartFile[] files);

    /**
    * @Description 下载文件
    *
    * @Param fileVo
    * @return void
    **/
    void download(FileVo fileVo);

    /**
    * @Description 批量下载文件
    *
    * @Param fileStorageIdList
    * @return void
    **/
    void downloadToZip(List<FileVo> FileVos);
    </#if>

    /**
    * 数据绑定
    *
    * @param dataBindQuerys 数据查询列表
    */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

}