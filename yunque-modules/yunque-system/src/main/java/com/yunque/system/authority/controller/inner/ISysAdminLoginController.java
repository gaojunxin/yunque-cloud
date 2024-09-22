package com.yunque.system.authority.controller.inner;

import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.context.SecurityContextHolder;
import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.web.result.R;
import com.yunque.common.security.annotation.InnerAuth;
import com.yunque.common.web.entity.controller.BasisController;
import com.yunque.system.api.authority.domain.dto.SysMenuDto;
import com.yunque.system.api.authority.domain.dto.SysModuleDto;
import com.yunque.system.api.authority.domain.dto.SysRoleDto;
import com.yunque.system.api.model.DataScope;
import com.yunque.system.api.model.LoginUser;
import com.yunque.system.api.organize.domain.dto.SysUserDto;
import com.yunque.system.authority.service.ISysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 管理端登录 | 内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/login/admin")
public class ISysAdminLoginController extends BasisController {

    @Autowired
    private ISysLoginService loginService;


    /**
     * 获取登录信息
     */
    @GetMapping
    @InnerAuth(isAnonymous = true)
    public R<LoginUser> getLoginInfoInner(@RequestParam String userName, @RequestParam String password) {
        SysUserDto user = loginService.loginByUser(userName, password);
        // 不存在直接返回空数据 | 与网络调用错误区分
        if (ObjectUtil.isNull(user)) {
            return R.ok(null, "用户账号不存在");
        }
        SecurityContextHolder.setUserType(user.getUserType());
        // 角色权限标识
        Set<String> roles = loginService.getRolePermission(user.getRoles(), user.getUserType());
        // 角色Id集合
        Set<Long> roleIds = CollUtil.isNotEmpty(user.getRoles())
                ? user.getRoles().stream().filter(item -> BaseConstants.Status.isNormal(item.getStatus())).map(SysRoleDto::getId).collect(Collectors.toSet())
                : new HashSet<>();

        // 权限范围
        DataScope dataScope = loginService.getDataScope(user.getRoles(), user);
        dataScope.setRoles(roles);
        dataScope.setRoleIds(roleIds);

        // 获取权限内模块列表
        List<SysModuleDto> moduleList = loginService.getModuleList(roleIds, user.getUserType());
        dataScope.setModuleIds(moduleList.stream().map(SysModuleDto::getId).collect(Collectors.toSet()));

        // 获取权限内菜单列表
        List<SysMenuDto> menuList = loginService.getMenuList(roleIds, user.getUserType());
        dataScope.setMenuIds(menuList.stream().map(SysMenuDto::getId).collect(Collectors.toSet()));

        // 菜单权限标识
        Set<String> permissions = loginService.getMenuPermission(menuList, user.getUserType());
        dataScope.setPermissions(permissions);

        // 路由路径集合
        Map<String, String> routeMap = loginService.getMenuRouteMap(menuList);
        LoginUser loginUser = new LoginUser();
        loginUser.initUser(user);
        loginUser.setScope(dataScope);
        loginUser.setRouteURL(routeMap);
        return R.ok(loginUser);
    }
}
