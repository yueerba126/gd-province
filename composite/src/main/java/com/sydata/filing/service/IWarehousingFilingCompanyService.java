package com.sydata.filing.service;

import com.sydata.filing.domain.WarehousingFilingCompany;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import com.sydata.filing.domain.WarehousingFilingCompany;
import com.sydata.filing.param.WarehousingFilingCompanySaveParam;
import com.sydata.filing.param.WarehousingFilingCompanyPageParam;
import com.sydata.filing.vo.WarehousingFilingCompanyVo;
import com.sydata.filing.vo.FileVo;
import com.sydata.framework.databind.domain.DataBindQuery;
import java.util.Collection;
import java.util.List;

/**   
 * @Description:TODO(仓储备案-仓储企业服务层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface IWarehousingFilingCompanyService extends IService<WarehousingFilingCompany> {

    /**
    * 分页列表
    *
    * @param pageParam 分页参数
    * @return 分页列表
    */
    Page<WarehousingFilingCompanyVo> pages(WarehousingFilingCompanyPageParam pageParam);

    /**
    * 详情
    *
    * @param id 主键ID
    * @return 详情
    */
    WarehousingFilingCompanyVo detail(String id);

    /**
    * 详情列表
    *
    * @param ids 主键ID集合
    * @return 详情
    */
    List<WarehousingFilingCompanyVo> detailList(List<String> ids);

    /**
    * 新增
    *
    * @param param 参数
    * @return 主键ID
    */
    String saveData(WarehousingFilingCompanySaveParam param);

    /**
    * 修改
    *
    * @param param 参数
    * @return 主键ID
    */
    String updateData(WarehousingFilingCompanySaveParam param);

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
    Boolean saveOrUpdateBatchData(List<WarehousingFilingCompanySaveParam> params);

    /**
    * 其他系统批量新增或修改（这里不需要我们生成ID）
    *
    * @param params 参数
    */
    Boolean saveOrUpdateBatchDataByOtherSystem(List<WarehousingFilingCompanySaveParam> params);

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
    void exportData(WarehousingFilingCompanyPageParam pageParam);

    /**
    * 唯一性
    */
    WarehousingFilingCompany getByUnx(String dwdm);

    /**
    * 唯一性更新
    *
    * @param saveParam 参数
    * @return 主键ID
    */
    Boolean updateOrSaveByUnx(WarehousingFilingCompanySaveParam saveParam);

    /**
    * 数据绑定
    *
    * @param dataBindQuerys 数据查询列表
    */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

}