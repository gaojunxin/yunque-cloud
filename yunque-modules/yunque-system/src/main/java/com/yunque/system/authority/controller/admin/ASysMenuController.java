package com.yunque.system.authority.controller.admin;

import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.core.web.validate.V_A;
import com.yunque.common.core.web.validate.V_E;
import com.yunque.common.log.annotation.Log;
import com.yunque.common.log.enums.BusinessType;
import com.yunque.common.security.annotation.AdminAuth;
import com.yunque.common.security.service.TokenUserService;
import com.yunque.common.security.utils.SecurityUserUtils;
import com.yunque.system.api.authority.domain.dto.SysMenuDto;
import com.yunque.system.api.authority.domain.query.SysMenuQuery;
import com.yunque.system.api.model.DataScope;
import com.yunque.system.authority.controller.base.BSysMenuController;
import com.yunque.system.utils.MRouteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统服务 | 权限模块 | 菜单管理 | 管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/menu")
public class ASysMenuController extends BSysMenuController {

    @Autowired
    private TokenUserService tokenService;

    /**
     * 获取路由信息
     */
    @GetMapping("/getRouters/{moduleId}")
    public AjaxResult getMultiRouters(@PathVariable Long moduleId) {
        Map<String, Object> menuMap = tokenService.getMenuRoute();
        String moduleKey = moduleId.toString();
        if (ObjectUtil.isNull(menuMap) || ObjectUtil.isNull(menuMap.get(moduleKey))) {
            DataScope dataScope = SecurityUserUtils.getDataScope();
            if (ObjectUtil.isNull(menuMap)) {
                menuMap = new HashMap<>();
            }
            if (CollUtil.isNotEmpty(dataScope.getMenuIds())) {
                List<SysMenuDto> menus = baseService.getRoutes(moduleId, dataScope.getMenuIds());
                menuMap.put(moduleKey, MRouteUtils.buildMenus(baseService.buildTree(menus)));
            } else {
                menuMap.put(moduleKey, new ArrayList<>());
            }
            tokenService.setMenuRoute(menuMap);
        }
        return success(menuMap.get(moduleKey));
    }

    /**
     * 查询菜单列表
     */
    @Override
    @GetMapping("/list")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_MENU_LIST)")
    public AjaxResult list(SysMenuQuery menu) {
        List<SysMenuDto> list;
        if (ObjectUtil.equals(Boolean.TRUE, menu.getExNodes())) {
            Serializable id = menu.getId();
            menu.initId();
            list = baseService.selectListScope(menu);
            SHandleExNodes(list, id);
        } else {
            list = baseService.selectListScope(menu);
        }
        if (ObjectUtil.equals(Boolean.TRUE, menu.getDefaultNode())) {
            list.add(TopNodeBuilder(menu));
        }
        return success(baseService.buildTree(list));
    }

    /**
     * 查询菜单详细
     */
    @Override
    @GetMapping(value = "/{id}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_MENU_SINGLE)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return success(baseService.selectInfoById(id));
    }

    /**
     * 菜单新增
     */
    @Override
    @PostMapping
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_MENU_ADD)")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    public AjaxResult add(@Validated({V_A.class}) @RequestBody SysMenuDto menu) {
        return super.add(menu);
    }

    /**
     * 菜单修改
     */
    @Override
    @PutMapping
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_MENU_EDIT)")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody SysMenuDto menu) {
        return super.edit(menu);
    }

    /**
     * 菜单修改状态
     */
    @Override
    @PutMapping("/status")
    @PreAuthorize("@ss.hasAnyAuthority(@Auth.SYS_MENU_EDIT, @Auth.SYS_MENU_ES)")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE_STATUS)
    public AjaxResult editStatus(@RequestBody SysMenuDto menu) {
        return super.editStatus(menu);
    }

    /**
     * 菜单批量删除
     */
    @Override
    @DeleteMapping("/batch/{idList}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_MENU_DEL)")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        return super.batchRemove(idList);
    }

}
