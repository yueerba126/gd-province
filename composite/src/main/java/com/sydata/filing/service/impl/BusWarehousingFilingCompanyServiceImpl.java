package com.sydata.filing.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.filing.enums.FilingBalxEnum;
import com.sydata.filing.enums.FilingBaztEnum;
import com.sydata.filing.enums.FilingShjgEnum;
import com.sydata.filing.http.WarehousingFilingFactoryService;
import com.sydata.filing.param.WarehousingFilingProcessSaveParam;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.springframework.stereotype.Service;
import com.sydata.filing.domain.WarehousingFilingCompany;
import com.sydata.filing.param.WarehousingFilingCompanySaveParam;
import com.sydata.filing.param.WarehousingFilingCompanyPageParam;
import com.sydata.filing.vo.WarehousingFilingCompanyVo;
import com.sydata.filing.mapper.WarehousingFilingCompanyMapper;
import com.sydata.filing.service.IWarehousingFilingCompanyService;
import com.sydata.filing.service.IBusWarehousingFilingCompanyService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**   
 * @Description:TODO(仓储备案-仓储企业业务服务实现)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service("busWarehousingFilingCompanyService")
public class BusWarehousingFilingCompanyServiceImpl extends ServiceImpl<WarehousingFilingCompanyMapper, WarehousingFilingCompany> implements IBusWarehousingFilingCompanyService {

    @Resource
    private IWarehousingFilingCompanyService warehousingFilingCompanyService;

    @Resource
    private WarehousingFilingFactoryService warehousingFilingFactoryService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean approve(WarehousingFilingProcessSaveParam param){
        LoginUser loginUser = UserSecurity.loginUser();
        WarehousingFilingCompany warehousingFilingCompany = warehousingFilingCompanyService.getById(param.getCompanyId());
        if(StringUtils.equals(param.getShjg(), FilingShjgEnum.通过.getStatus())){
            if(StringUtils.equals(warehousingFilingCompany.getBalx(), FilingBalxEnum.注销备案.getStatus())){
                warehousingFilingCompany.setBazt(FilingBaztEnum.已注销.getStatus());
            }else{
                warehousingFilingCompany.setBazt(FilingBaztEnum.已备案.getStatus());
            }
        }else{
            warehousingFilingCompany.setBazt(FilingBaztEnum.审核不通过.getStatus());
        }
        warehousingFilingCompany.setCzbz(CzBzEnum.U.getCzBz());
        warehousingFilingCompany.setUpdateBy(loginUser.getName());
        warehousingFilingCompany.setUpdateTime(LocalDateTime.now());
        warehousingFilingCompany.setZhgxsj(LocalDateTime.now());
        warehousingFilingCompany.setShsj(LocalDateTime.now());
        warehousingFilingCompany.setShyj(param.getShyj());
        warehousingFilingCompany.setQyshr(loginUser.getName());
        warehousingFilingCompany.setBaclbm(loginUser.getOrganizeName());
        warehousingFilingCompanyService.updateById(warehousingFilingCompany);

        WarehousingFilingCompanySaveParam warehousingFilingCompanySaveParam
                = BeanUtils.copyByClass(warehousingFilingCompany, WarehousingFilingCompanySaveParam.class);

        return warehousingFilingFactoryService.doSptReturnInfo(warehousingFilingCompanySaveParam);
    }

    @Override
    public void beforeReturnPage(Page<WarehousingFilingCompany> page){};

    @Override
    public void beforeReturnDetail(WarehousingFilingCompanyVo warehousingFilingCompanyVo){};

    @Override
    public void beforeReturnDetailList(List<WarehousingFilingCompanyVo>  warehousingFilingCompanyVoList){};

    @Override
    public void beforeDoSave(WarehousingFilingCompany  warehousingFilingCompany){};

    @Override
    public void beforeDoUpdate(WarehousingFilingCompany  warehousingFilingCompany){};

    @Override
    public void beforeDoRemove(List<String> ids){};

}