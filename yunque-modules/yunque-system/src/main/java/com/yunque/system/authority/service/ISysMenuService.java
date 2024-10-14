package com.yunque.system.authority.service;

import com.yunque.common.web.entity.service.ITreeService;
import com.yunque.system.api.authority.domain.dto.SysMenuDto;
import com.yunque.system.api.authority.domain.query.SysMenuQuery;

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

    /**
     * 获取企业有权限且状态正常的菜单
     *
     * @param roleIds      角色Id集合
     * @param userType     用户标识
     * @return 菜单对象集合
     */
    List<SysMenuDto> selectEnterpriseList(Set<Long> roleIds, String userType);

    /**
     *
     * 构建菜单树
     *
     * [详细描述，可以包括方法的功能、使用注意事项等]
     *
     * @param list
     * @return
     */
    public List<SysMenuDto> buildTree(List<SysMenuDto> list);

}
