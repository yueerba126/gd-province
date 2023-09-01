package com.sydata.safe.asess.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.GenerateNoUtil;
import com.sydata.organize.domain.Dept;
import com.sydata.organize.domain.Organize;
import com.sydata.organize.domain.Region;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IDeptService;
import com.sydata.organize.service.IOrganizeService;
import com.sydata.organize.service.IRegionService;
import com.sydata.safe.asess.annotation.DataBindTemplate;
import com.sydata.safe.asess.domain.Template;
import com.sydata.safe.asess.domain.TemplateIndex;
import com.sydata.safe.asess.mapper.TemplateMapper;
import com.sydata.safe.asess.param.*;
import com.sydata.safe.asess.service.*;
import com.sydata.safe.asess.vo.TemplateAllotDeptVo;
import com.sydata.safe.asess.vo.TemplateVo;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;
import static com.sydata.organize.service.impl.OrganizeServiceImpl.ROOT_PARENT_ID;
import static com.sydata.safe.asess.enums.SafeAssessStateEnum.*;
import static java.lang.Boolean.TRUE;
import static java.util.function.Function.identity;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;


/**
 * 粮食安全考核-考核模板Service业务层处理
 *
 * @author system
 * @date 2023-02-13
 */
@CacheConfig(cacheNames = TemplateServiceImpl.CACHE_NAME)
@Service("templateService")
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements ITemplateService {

    final static String CACHE_NAME = "safeAssess:template";

    @Resource
    private TemplateMapper templateMapper;

    @Resource
    private ITemplateIndexService templateIndexService;

    @Resource
    private IReviewService reviewService;

    @Resource
    private IScoreService scoreService;

    @Resource
    private IRegionService regionService;

    @Resource
    private IOrgAssessService orgAssessService;

    @Resource
    private IOrgAssessDeptService orgAssessDeptService;

    @Resource
    private IOrgAssessReviewService orgAssessReviewService;

    @Resource
    private ICheckPlanService checkPlanService;

    @Resource
    private IOrganizeService organizeService;

    @Resource
    private IDeptService deptService;

    @Cacheable(key = "'id='+#id")
    @Override
    public Template getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(key = "'id='+#entity.id")
    @Override
    public boolean updateById(Template entity) {
        // 操作权限判断
        Assert.state(this.operationAuth(entity.getId()), "非考核模板组织人员不可操作");

        Template template = SpringUtil.getBean(this.getClass()).getById(entity.getId());
        Assert.state(!PUSH.getState().equals(template.getState()), "已发布状态不允许修改");

        return super.updateById(entity);
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean removeById(Serializable id) {
        // 操作权限判断
        Assert.state(this.operationAuth((String) id), "非考核模板组织人员不可操作");

        Template template = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.state(SAVE.getState().equals(template.getState()), "非保存状态不允许删除");

        // 根据模板删除指标
        templateIndexService.removeByTemplateId((String) id);
        return super.removeById(id);
    }

    @DataBindFieldConvert
    @Override
    public Page<TemplateVo> page(TemplatePageParam pageParam) {
        // 获取当前登录人所属行政区划进行数据权限过滤,只看自己行政区划的数据
        Page<Template> page = super.lambdaQuery()
                .eq(Template::getRegionId, UserSecurity.loginUser().getRegionId())
                .likeRight(isNotEmpty(pageParam.getNumber()), Template::getNumber, pageParam.getNumber())
                .likeRight(isNotEmpty(pageParam.getName()), Template::getName, pageParam.getName())
                .eq(isNotEmpty(pageParam.getRegionId()), Template::getRegionId, pageParam.getRegionId())
                .ge(isNotEmpty(pageParam.getPushDateBegin()), Template::getPushDate, pageParam.getPushDateBegin())
                .le(isNotEmpty(pageParam.getPushDateEnd()), Template::getPushDate, pageParam.getPushDateEnd())
                .orderByDesc(Template::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, TemplateVo.class);
    }

    @Override
    public String generateNumber() {
        String date = LocalDate.now().toString().replace(DASH, EMPTY);
        String prefix = CACHE_NAME + ":number:";
        String number = GenerateNoUtil.generate(prefix + date);
        return number.substring(prefix.length());
    }

    @Override
    public Template getByUnxName(String year, String name, String organizeId) {
        TemplateServiceImpl bean = SpringUtil.getBean(this.getClass());
        return super.lambdaQuery()
                .select(Template::getId)
                .eq(Template::getYear, year)
                .eq(Template::getName, name)
                .eq(Template::getOrganizeId, organizeId)
                .oneOpt()
                .map(Template::getId)
                .map(bean::getById)
                .orElse(null);
    }

    @Override
    public Boolean add(TemplateSaveParam saveParam) {
        // 校验《年份/名称/组织ID》不可重复
        Template oldTemplate = this.getByUnxName(saveParam.getYear(), saveParam.getName(), UserSecurity.organizeId());
        Assert.isNull(oldTemplate, "已存在年份和名称相同的考核模板");

        // 获取子行政区域ID集合
        List<Region> regions = regionService.listByParentId(UserSecurity.loginUser().getRegionId());
        Assert.notEmpty(regions, "没有下级行政区域,无法新增考核模板");

        String regionIds = StreamEx.of(regions).map(Region::getId).joining(COMMA);
        Template template = BeanUtils.copyByClass(saveParam, Template.class)
                .setRegionIds(regionIds)
                .setRegionTotalCount(regions.size())
                .setState(SAVE.getState());
        return super.save(template);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean copyAdd(TemplateCopySaveParam saveParam) {
        // 校验《年份/名称/组织ID》不可重复
        Template oldTemplate = this.getByUnxName(saveParam.getYear(), saveParam.getName(), UserSecurity.organizeId());
        Assert.isNull(oldTemplate, "已存在年份和名称相同的考核模板");

        Template copyTemplate = SpringUtil.getBean(this.getClass()).getById(saveParam.getId());
        Template template = BeanUtils.copyByClass(saveParam, Template.class)
                .setId(IdWorker.getIdStr())
                .setRegionIds(copyTemplate.getRegionIds())
                .setRegionTotalCount(copyTemplate.getRegionTotalCount())
                .setState(SAVE.getState());

        // 获取考核模板指标列表,将老ID和新ID进行映射,组装新的考核指标数据
        List<TemplateIndex> list = templateIndexService.listByTemplateId(saveParam.getId());
        Map<String, String> idMap = StreamEx.of(list).toMap(TemplateIndex::getId, v -> IdWorker.getIdStr());
        StreamEx.of(list)
                .map(index -> BeanUtils.copyByClass(index, TemplateIndex.class))
                .map(index -> {
                    String parentId = idMap.getOrDefault(index.getParentId(), ROOT_PARENT_ID);
                    return index.setId(idMap.get(index.getId())).setTemplateId(template.getId()).setParentId(parentId);
                })
                .toListAndThen(templateIndexService::saveBatch);

        return super.save(template);
    }

    @Override
    public Boolean update(TemplateUpdateParam updateParam) {
        // 校验《年份/名称/组织ID》不可重复
        Template oldTemplate = this.getByUnxName(updateParam.getYear(), updateParam.getName(), UserSecurity.organizeId());
        boolean state = oldTemplate == null || oldTemplate.getId().equals(updateParam.getId());
        Assert.state(state, "已存在年份和名称相同的考核模板");

        int regionTotalCount = updateParam.getRegionIds().split(COMMA).length;
        Template template = BeanUtils.copyByClass(updateParam, Template.class).setRegionTotalCount(regionTotalCount);
        return SpringUtil.getBean(this.getClass()).updateById(template);
    }

    @DataBindFieldConvert
    @Override
    public List<TemplateAllotDeptVo> allotDept(String id) {
        Template template = SpringUtil.getBean(this.getClass()).getById(id);

        return StreamEx.of(templateIndexService.listByTemplateId(template.getId()))
                .filter(TemplateIndex::getTopic)
                .map(this::deptIdsByIndex)
                .flatMap(Collection::stream)
                .distinct()
                .map(deptId -> new TemplateAllotDeptVo().setDeptId(deptId))
                .toList();
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean allot(String id) {
        // 操作权限判断
        Assert.state(this.operationAuth(id), "非考核模板组织人员不可操作");

        // 模板状态校验-必须为待分配状态才允许分配
        Template template = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.state(AWAIT_ALLOT.getState().equals(template.getState()), "待分配状态才允许分配");


        // 校验自动分配的组织必须有且只有一个部门
        Set<String> autoAllotDeptIds = isEmpty(template.getAutoAllotDeptIds()) ?
                Collections.emptySet() : StreamEx.of(template.getAutoAllotDeptIds().split(COMMA)).toSet();

        List<Dept> deptList = CollectionUtils.isNotEmpty(autoAllotDeptIds) ?
                deptService.listByOrganizeIds(autoAllotDeptIds) : Collections.emptyList();

        Map<String, List<Dept>> organizeIdMap = StreamEx.of(deptList).groupingBy(Dept::getOrganizeId);
        List<String> organizeIds = StreamEx.of(autoAllotDeptIds)
                .filter(organizeId -> CollectionUtils.size(organizeIdMap.get(organizeId)) != 1)
                .toList();
        Assert.state(CollectionUtils.isEmpty(organizeIds), () -> {
            List<Organize> organizes = organizeService.listByIds(organizeIds);
            List<String> names = StreamEx.of(organizes).map(Organize::getName).toList();
            return "自动分配的" + names + "组织必须有且只有一个部门";
        });

        // 计算部门数
        List<TemplateIndex> indexList = templateIndexService.listByTemplateId(template.getId());
        int deptTotalCount = StreamEx.of(indexList)
                .filter(TemplateIndex::getTopic)
                .map(this::deptIdsByIndex)
                .flatMap(Collection::stream)
                .toSet()
                .size();
        Assert.state(deptTotalCount > 0, "考核指标中没有设置部门无法进行分配");

        LocalDate allotDate = LocalDate.now();
        boolean update = super.lambdaUpdate()
                .set(Template::getDeptTotalCount, deptTotalCount)
                .set(Template::getAllotDate, allotDate)
                .set(Template::getState, ALLOT.getState())
                .eq(Template::getId, template.getId())
                .eq(Template::getState, AWAIT_ALLOT.getState())
                .update();
        Assert.state(update, "待分配状态才允许分配");

        // 将考核模板任务分配到考核评审
        Map<String, Dept> autoAllotMap = StreamEx.of(deptList).toMap(Dept::getOrganizeId, identity());
        return reviewService.allot(template, indexList, allotDate, autoAllotMap);
    }

    @CacheEvict(key = "'id='+#pushParam.id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean push(TemplatePushParam pushParam) {
        // 操作权限判断
        Assert.state(this.operationAuth(pushParam.getId()), "非考核模板组织人员不可操作");

        // 校验最晚提交日期必须在发布日期之后
        LocalDate pushDate = LocalDate.now();
        LocalDate lastSubmitDate = pushParam.getLastSubmitDate();
        Assert.state(lastSubmitDate.isAfter(pushDate), "最晚提交日期必须在发布日期之后");

        // 查询判断该模板是否为待发布
        Template template = SpringUtil.getBean(this.getClass()).getById(pushParam.getId());
        Assert.state(AWAIT_PUSH.getState().equals(template.getState()), "待发布状态的考核模板才允许发布");

        // 获取模板下级地市,给各地市发布考核
        List<String> regionIds = Arrays.asList(template.getRegionIds().split(COMMA));
        List<Region> regions = regionService.listByIds(regionIds);

        // 修改模板状态为已发布
        boolean update = super.lambdaUpdate()
                .set(Template::getLastSubmitDate, lastSubmitDate)
                .set(Template::getPushDate, pushDate)
                .set(Template::getState, PUSH.getState())
                .eq(Template::getId, template.getId())
                .eq(Template::getState, AWAIT_PUSH.getState())
                .update();
        Assert.state(update, "待发布状态的考核模板才允许发布");

        // 考核模板发布到各地市
        List<TemplateIndex> templateIndexList = templateIndexService.listByTemplateId(template.getId());
        return orgAssessService.push(template, templateIndexList, regions, pushDate, lastSubmitDate);
    }

    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean revoke(String id) {
        boolean update = super.lambdaUpdate()
                .set(Template::getAllotDate, null)
                .set(Template::getLastSubmitDate, null)
                .set(Template::getPushDate, null)
                .set(Template::getDeptTotalCount, 0)
                .set(Template::getDeptAllotCount, 0)
                .set(Template::getDeptAssessCount, 0)
                .set(Template::getRegionSubmitCount, 0)
                .set(Template::getRegionAssessCount, 0)
                .set(Template::getState, SAVE.getState())
                .eq(Template::getId, id)
                .update();
        Assert.state(update, "考核模板撤回失败");

        // 撤回
        reviewService.templateRevoke(id);
        scoreService.templateRevoke(id);
        orgAssessService.templateRevoke(id);
        orgAssessDeptService.templateRevoke(id);
        orgAssessReviewService.templateRevoke(id);
        checkPlanService.templateRevoke(id);

        return TRUE;
    }

    @CacheEvict(key = "'id='+#id")
    @Override
    public Boolean operationRegionSubmitCount(String id, int count) {
        Boolean state = templateMapper.operationRegionSubmitCount(id, count);
        Assert.state(state, "考核模板统计地市已提交数量失败");
        return TRUE;
    }

    @CacheEvict(key = "'id='+#id")
    @Override
    public Boolean operationRegionAssessCount(String id, int count) {
        Boolean state = templateMapper.operationRegionAssessCount(id, count);
        Assert.state(state, "考核模板统计地市已考核数量失败");
        return TRUE;
    }

    @CacheEvict(key = "'id='+#id")
    @Override
    public Boolean operationDeptAllotCount(String id, int count) {
        Boolean state = templateMapper.operationDeptAllotCount(id, count);
        Assert.state(state, "考核模板统计考核评审已分配数量失败");
        return TRUE;
    }

    @CacheEvict(key = "'id='+#id")
    @Override
    public Boolean operationDeptAssessCount(String id, int count) {
        Boolean state = templateMapper.operationDeptAssessCount(id, count);
        Assert.state(state, "考核模板统计考核评审已考核数量失败");
        return TRUE;
    }

    @Override
    public Boolean operationAuth(String id) {
        Template template = SpringUtil.getBean(this.getClass()).getById(id);
        return UserSecurity.organizeId().equals(template.getOrganizeId());
    }

    @DataBindService(strategy = DataBindTemplate.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, templateMapper);
    }

    /**
     * 获取指标部门ID列表
     *
     * @param index 考核指标
     * @return 部门IDS
     */
    private Set<Long> deptIdsByIndex(TemplateIndex index) {
        Set<Long> deptIds = SetUtils.hashSet(index.getLeadDeptId());
        String cooperateDeptIds = index.getCooperateDeptIds();
        if (isNotEmpty(cooperateDeptIds)) {
            StreamEx.of(cooperateDeptIds.split(COMMA)).map(Long::parseLong).toListAndThen(deptIds::addAll);
        }
        return deptIds;
    }
}