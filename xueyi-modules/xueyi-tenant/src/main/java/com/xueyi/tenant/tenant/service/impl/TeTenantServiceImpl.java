package com.xueyi.tenant.tenant.service.impl;

import com.xueyi.common.cache.model.CacheModel;
import com.xueyi.common.core.constant.basic.DictConstants;
import com.xueyi.common.core.constant.basic.OperateConstants;
import com.xueyi.common.core.constant.basic.SecurityConstants;
import com.xueyi.common.core.utils.core.ObjectUtil;
import com.xueyi.common.core.utils.core.StrUtil;
import com.xueyi.common.core.web.result.AjaxResult;
import com.xueyi.common.core.web.result.R;
import com.xueyi.common.redis.constant.RedisConstants;
import com.xueyi.common.security.utils.SecurityUserUtils;
import com.xueyi.common.web.entity.service.impl.BaseServiceImpl;
import com.xueyi.system.api.organize.domain.dto.SysDeptDto;
import com.xueyi.system.api.organize.domain.dto.SysPostDto;
import com.xueyi.system.api.organize.domain.dto.SysUserDto;
import com.xueyi.system.api.organize.feign.RemoteDeptService;
import com.xueyi.system.api.organize.feign.RemotePostService;
import com.xueyi.system.api.organize.feign.RemoteUserService;
import com.xueyi.tenant.api.source.domain.dto.TeStrategyDto;
import com.xueyi.tenant.api.tenant.constant.TenantConstants;
import com.xueyi.tenant.api.tenant.domain.dto.TeTenantDto;
import com.xueyi.tenant.api.tenant.domain.query.TeTenantQuery;
import com.xueyi.tenant.source.service.ITeStrategyService;
import com.xueyi.tenant.tenant.domain.correlate.TeTenantCorrelate;
import com.xueyi.tenant.tenant.domain.dto.TeTenantRegister;
import com.xueyi.tenant.tenant.domain.model.TeTenantConverter;
import com.xueyi.tenant.tenant.manager.ITeTenantManager;
import com.xueyi.tenant.tenant.service.ITeTenantService;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.function.Function;

import static com.xueyi.common.core.constant.basic.BaseConstants.TOP_ID;

/**
 * 租户服务 | 租户模块 | 租户管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class TeTenantServiceImpl extends BaseServiceImpl<TeTenantQuery, TeTenantDto, TeTenantCorrelate, ITeTenantManager> implements ITeTenantService {

    @Lazy
    @Autowired
    ITeTenantService oneselfService;

    @Autowired
    private ITeStrategyService strategyService;

    @Autowired
    private RemoteDeptService deptService;

    @Autowired
    private RemotePostService postService;

    @Autowired
    private RemoteUserService userService;

    @Resource
    private TeTenantConverter baseConverter;

    /** 缓存定义 */
    @Override
    public CacheModel getCacheModel() {
        return new CacheModel(TenantConstants.CacheType.TE_TENANT_KEY.getCode(), TenantConstants.CacheType.TE_TENANT_KEY.getIsTenant());
    }

    /**
     * 新增租户 | 包含数据初始化
     *
     * @param tenantRegister 租户初始化对象
     * @return 结果
     */
    @Override
    @Transactional
    @GlobalTransactional
    public int insert(TeTenantRegister tenantRegister) {
        int rows = insert(tenantRegister.getTenant());
        if (rows > 0) {
            TeStrategyDto strategy = strategyService.selectById(tenantRegister.getTenant().getStrategyId());
            tenantRegister.setSourceName(strategy.getSourceSlave());
            oneselfService.organizeInit(tenantRegister);
        }
        return rows;
    }

    /**
     * 校验源策略是否被使用
     *
     * @param strategyId 数据源策略id
     * @return 结果 | true/false 存在/不存在
     */
    @Override
    public boolean checkStrategyExist(Long strategyId) {
        return ObjectUtil.isNotNull(baseManager.checkStrategyExist(strategyId));
    }

    /**
     * 校验租户是否为默认租户
     *
     * @param id 租户id
     * @return 结果 | true/false 是/不是
     */
    @Override
    public boolean checkIsDefault(Long id) {
        TeTenantDto tenant = baseManager.selectById(id);
        return ObjectUtil.isNotNull(tenant) && StrUtil.equals(tenant.getIsDefault(), DictConstants.DicYesNo.YES.getCode());
    }

    /**
     * 租户组织数据初始化
     *
     * @param tenantRegister 租户初始化对象
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void organizeInit(TeTenantRegister tenantRegister) {
        Long enterpriseId = tenantRegister.getTenant().getId();
        String sourceName = tenantRegister.getSourceName();
        tenantRegister.getDept().setParentId(TOP_ID);
        R<SysDeptDto> deptR = deptService.addInner(tenantRegister.getDept(), enterpriseId, sourceName);
        if (deptR.isFail()) {
            AjaxResult.warn("新增失败，请检查！");
        }
        tenantRegister.getPost().setDeptId(deptR.getData().getId());
        R<SysPostDto> postR = postService.addInner(tenantRegister.getPost(), enterpriseId, sourceName);
        if (postR.isFail()) {
            AjaxResult.warn("新增失败，请检查！");
        }
        tenantRegister.getUser().setPostIds(new Long[]{postR.getData().getId()});
        tenantRegister.getUser().setUserType(SecurityConstants.UserType.ADMIN.getCode());
        tenantRegister.getUser().setPassword(SecurityUserUtils.encryptPassword(tenantRegister.getUser().getPassword()));
        R<SysUserDto> userR = userService.addInner(tenantRegister.getUser(), enterpriseId, sourceName);
        if (userR.isFail()) {
            AjaxResult.warn("新增失败，请检查！");
        }
    }

    /**
     * 校验租户关联域名是否已存在
     *
     * @param id         租户Id
     * @param domainName 企业自定义域名
     * @return 结果 | true/false
     */
    @Override
    public Boolean checkDomainUnique(Long id, String domainName) {
        if (StrUtil.isBlank(domainName)) {
            return Boolean.FALSE;
        }
        return ObjectUtil.isNotNull(baseManager.checkDomainName(id, domainName));
    }

    /**
     * 缓存更新
     *
     * @param operate       服务层 - 操作类型
     * @param operateCache  缓存操作类型
     * @param dto           数据对象
     * @param dtoList       数据对象集合
     * @param cacheKey      缓存编码
     * @param isTenant      租户级缓存
     * @param cacheKeyFun   缓存键定义方法
     * @param cacheValueFun 缓存值定义方法
     */
    @Override
    public void refreshCache(OperateConstants.ServiceType operate, RedisConstants.OperateType operateCache, TeTenantDto dto, Collection<TeTenantDto> dtoList,
                             String cacheKey, Boolean isTenant, Function<? super TeTenantDto, String> cacheKeyFun, Function<? super TeTenantDto, Object> cacheValueFun) {
        super.refreshCache(operate, operateCache, dto, dtoList, cacheKey, isTenant, TeTenantDto::getIdStr, info -> baseConverter.mapper(info));
    }
}