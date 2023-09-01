package com.sydata.monitoring.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.monitoring.constatns.MonitoringConstants;
import com.sydata.monitoring.dto.CheckPointAddDto;
import com.sydata.monitoring.dto.CheckPointConfigDto;
import com.sydata.monitoring.dto.CheckPointQueryDto;
import com.sydata.monitoring.dto.CheckPointRemoveDto;
import com.sydata.monitoring.entity.CheckPoint;
import com.sydata.monitoring.entity.CheckPointGrain;
import com.sydata.monitoring.entity.CheckPointPrice;
import com.sydata.monitoring.mapper.CheckPointMapper;
import com.sydata.monitoring.service.ICheckPointGrainService;
import com.sydata.monitoring.service.ICheckPointPriceService;
import com.sydata.monitoring.service.ICheckPointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.monitoring.vo.CheckPointConfigVo;
import com.sydata.monitoring.vo.CheckPointVo;
import com.sydata.organize.domain.Role;
import com.sydata.organize.domain.User;
import com.sydata.organize.param.UserRoleSetUpParam;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IRoleService;
import com.sydata.organize.service.IUserRoleService;
import com.sydata.organize.service.IUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 流通检测-监测点配置表 服务实现类
 * </p>
 *
 * @author zhangcy
 * @since 2023-04-24
 */
@Service
public class CheckPointServiceImpl extends ServiceImpl<CheckPointMapper, CheckPoint> implements ICheckPointService {

    @Resource
    private ICheckPointGrainService checkPointGrainService;

    @Resource
    private ICheckPointPriceService checkPointPriceService;

    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Resource
    private IUserRoleService userRoleService;

    @DataBindFieldConvert
    @Override
    public Page<CheckPointVo> pagination(CheckPointQueryDto queryDto) {
        String account = UserSecurity.loginUser().getAccount();
        String userId = UserSecurity.userId();

        Page<CheckPoint> page = lambdaQuery()
                .eq(!MonitoringConstants.ADMIN_ACCOUNT.equals(account), CheckPoint::getAccountUserId, userId)
                .like(StringUtils.isNotBlank(queryDto.getName()), CheckPoint::getName, queryDto.getName())
                .page(new Page<>(queryDto.getPageNum(), queryDto.getPageSize()));

        List<CheckPoint> records = page.getRecords();

        Page<CheckPointVo> checkPointVoPage = BeanUtils.copyToPage(page, CheckPointVo.class);

        if (CollectionUtils.isEmpty(records)) {
            return checkPointVoPage;
        }

        List<CheckPointVo> voList = records.stream().map(CheckPointVo::new).collect(Collectors.toList());

        checkPointVoPage.setRecords(voList);

        return checkPointVoPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean add(CheckPointAddDto addDto) {

        String account = addDto.getAccount();
        String password = addDto.getPassword();

        // 新增用户
        User user = new User();
        user.setName(addDto.getName());
        user.setAccount(account);
        user.setPassword(password);

        user.setOrganizeId(UserSecurity.organizeId());
        user.setCreateBy(UserSecurity.userName());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateBy(UserSecurity.userName());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);

        addDto.setAccountUserId(user.getId());

        addDto.setCreateBy(UserSecurity.userName());
        addDto.setCreateTime(LocalDateTime.now());
        addDto.setUpdateBy(UserSecurity.userName());
        addDto.setUpdateTime(LocalDateTime.now());
        addDto.setCountryId(UserSecurity.loginUser().getCountryId());
        // 保存监测点信息
        save(addDto);

        // 查询监测点角色
        Role monitoringPointRole = roleService.lambdaQuery()
                .eq(Role::getPermission, MonitoringConstants.CHECK_POINT_ACCOUNT_ROLE_PERMISSION)
                .orderByAsc(Role::getCreateTime)
                .last(" limit 1 ")
                .one();

        if (monitoringPointRole == null) {
            monitoringPointRole = new Role();
            monitoringPointRole.setName(addDto.getName() + MonitoringConstants.CHECK_POINT_ACCOUNT_NAME_SUFFIX);
            monitoringPointRole.setPermission(MonitoringConstants.CHECK_POINT_ACCOUNT_ROLE_PERMISSION);
            roleService.save(monitoringPointRole);
        }

        // 将监测点角色分配给账号
        UserRoleSetUpParam setUpParam = new UserRoleSetUpParam();
        setUpParam.setRoleIds(Collections.singleton(monitoringPointRole.getId()));
        setUpParam.setUserIds(Collections.singleton(user.getId()));

        userRoleService.setUp(setUpParam);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean config(CheckPointConfigDto configDto) {
        String id = configDto.getId();

        checkPointGrainService.lambdaUpdate()
                .eq(CheckPointGrain::getPointId, id)
                .remove();

        List<CheckPointGrain> pointGrains = configDto.getMaterialTypeList().stream()
                .map(materialType -> {
                    CheckPointGrain checkPointGrain = new CheckPointGrain();
                    checkPointGrain.setLspzdm(materialType);
                    checkPointGrain.setPointId(id);

                    return checkPointGrain;
                })
                .collect(Collectors.toList());

        checkPointGrainService.saveBatch(pointGrains);

        checkPointPriceService.lambdaUpdate()
                .eq(CheckPointPrice::getPointId, id)
                .remove();

        List<CheckPointPrice> pointPrices = configDto.getPriceList().stream()
                .map(priceType -> {
                    CheckPointPrice checkPointPrice = new CheckPointPrice();
                    checkPointPrice.setPriceType(priceType);
                    checkPointPrice.setPointId(id);

                    return checkPointPrice;
                })
                .collect(Collectors.toList());

        checkPointPriceService.saveBatch(pointPrices);

        return true;
    }

    @Override
    public Boolean remove(CheckPointRemoveDto removeDto) {
        return removeByIds(removeDto.getIds());
    }

    @DataBindFieldConvert
    @Override
    public CheckPointVo currentPoint() {
        return lambdaQuery()
                .eq(CheckPoint::getAccountUserId, UserSecurity.userId())
                .oneOpt()
                .map(CheckPointVo::new)
                .orElse(null);

    }

    @Override
    public CheckPointConfigVo configDetail(CheckPointConfigDto configDto) {
        String id = configDto.getId();

        CheckPointConfigVo vo = new CheckPointConfigVo(getById(id));

        List<String> lspzdmList = checkPointGrainService.lambdaQuery()
                .eq(CheckPointGrain::getPointId, id)
                .list()
                .stream()
                .map(CheckPointGrain::getLspzdm)
                .collect(Collectors.toList());

        vo.setMaterialTypeList(lspzdmList);

        List<String> priceTypeList = checkPointPriceService.lambdaQuery()
                .eq(CheckPointPrice::getPointId, id)
                .list()
                .stream()
                .map(CheckPointPrice::getPriceType)
                .collect(Collectors.toList());

        vo.setMaterialTypeList(lspzdmList);
        vo.setPriceList(priceTypeList);

        return vo;
    }

    @DataBindFieldConvert
    @Override
    public List<CheckPointVo> selectByIds(Set<String> pointIds) {
        if (CollectionUtils.isEmpty(pointIds)) {
            return Collections.emptyList();
        }

        List<CheckPoint> list = lambdaQuery().in(CheckPoint::getId, pointIds).list();
        return list.stream().map(CheckPointVo::new).collect(Collectors.toList());
    }

}
