package com.yunque.system.authority.service.impl;

import com.yunque.common.datascope.annotation.DataScope;
import com.yunque.common.web.correlate.contant.CorrelateConstants;
import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.system.api.authority.domain.dto.SysRoleDto;
import com.yunque.system.api.authority.domain.query.SysRoleQuery;
import com.yunque.system.authority.domain.correlate.SysRoleCorrelate;
import com.yunque.system.authority.manager.ISysRoleManager;
import com.yunque.system.authority.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yunque.common.core.constant.basic.SecurityConstants.CREATE_BY;

/**
 * 系统服务 | 权限模块 | 角色管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleQuery, SysRoleDto, SysRoleCorrelate, ISysRoleManager> implements ISysRoleService {

    /**
     * 默认方法关联配置定义
     */
    @Override
    protected Map<CorrelateConstants.ServiceType, SysRoleCorrelate> defaultCorrelate() {
        return new HashMap<>() {{
            put(CorrelateConstants.ServiceType.ADD, SysRoleCorrelate.BASE_ADD);
            put(CorrelateConstants.ServiceType.DELETE, SysRoleCorrelate.BASE_DEL);
        }};
    }

    /**
     * 查询角色对象列表 | 数据权限 | 附加数据
     *
     * @param role 角色对象
     * @return 角色对象集合
     */
    @Override
    @DataScope(userAlias = CREATE_BY, mapperScope = {"SysRoleMapper"})
    public List<SysRoleDto> selectListScope(SysRoleQuery role) {
        return super.selectListScope(role);
    }

    /**
     * 根据Id查询角色信息对象 | 含功能权限
     *
     * @param id Id
     * @return 角色信息对象
     */
    @Override
    public SysRoleDto selectAuthById(Long id) {
        return subCorrelates(selectById(id), SysRoleCorrelate.AUTH_SEL);
    }

    /**
     * 根据Id查询角色信息对象 | 含数据权限
     *
     * @param id Id
     * @return 角色信息对象
     */
    @Override
    public SysRoleDto selectDataById(Long id) {
        return subCorrelates(selectById(id), SysRoleCorrelate.DATA_SEL);
    }

    /**
     * 修改角色功能权限
     *
     * @param role 角色对象
     * @return 结果
     */
    @Override
    public int updateRoleAuth(SysRoleDto role) {
        return editCorrelates(role, SysRoleCorrelate.AUTH_EDIT);
    }

    /**
     * 修改角色组织权限
     *
     * @param role 角色对象
     * @return 结果
     */
    @Override
    public int updateDataScope(SysRoleDto role) {
        int row = baseManager.updateDataScope(role.getId(), role.getRoleKey(), role.getDataScope());
        if (row > 0) {
            editCorrelates(role, SysRoleCorrelate.DATA_EDIT);
        }
        return row;
    }
}
