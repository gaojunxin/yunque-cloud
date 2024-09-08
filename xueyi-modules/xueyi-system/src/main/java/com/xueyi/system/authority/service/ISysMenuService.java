package com.xueyi.system.authority.service;

import com.xueyi.common.web.entity.service.ITreeService;
import com.xueyi.system.api.authority.domain.dto.SysMenuDto;
import com.xueyi.system.api.authority.domain.query.SysMenuQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 系统服务 | 权限模块 | 菜单管理 服务层
 *
 * @author xueyi
 */
public interface ISysMenuService extends ITreeService<SysMenuQuery, SysMenuDto> {

    /**
     * 根据Id查询菜单信息
     *
     * @param id Id
     * @return 菜单数据对象
     */
    SysMenuDto selectInfoById(Serializable id);

    /**
     * 根据模块Id查询菜单路由
     *
     * @param moduleId 模块Id
     * @param menuIds  菜单Ids
     * @return 菜单列表
     */
    List<SysMenuDto> getRoutes(Long moduleId, Collection<Long> menuIds);


}
