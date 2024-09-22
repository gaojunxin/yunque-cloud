package com.yunque.system.authority.manager.impl;

import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.security.utils.SecurityUserUtils;
import com.yunque.system.api.authority.domain.dto.SysMenuDto;
import com.yunque.system.api.authority.domain.dto.SysModuleDto;
import com.yunque.system.api.model.DataScope;
import com.yunque.system.api.model.LoginUser;
import com.yunque.system.authority.domain.vo.SysAuthTree;
import com.yunque.system.authority.manager.ISysAuthManager;
import com.yunque.system.authority.manager.ISysMenuManager;
import com.yunque.system.authority.manager.ISysModuleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统服务 | 权限模块 | 权限管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysAuthManager implements ISysAuthManager {

    @Autowired
    private ISysModuleManager moduleManager;

    @Autowired
    private ISysMenuManager menuManager;

    /**
     * 获取公共模块 | 菜单权限树
     *
     * @return 权限对象集合
     */
    @Override
    public List<SysAuthTree> selectCommonAuthScope() {
        List<SysModuleDto> modules = moduleManager.selectCommonList();
        List<SysMenuDto> menus = menuManager.selectCommonList();
        return new ArrayList<>(CollUtil.addAll(
                modules.stream().map(SysAuthTree::new).collect(Collectors.toList()),
                menus.stream().map(SysAuthTree::new).collect(Collectors.toList())));
    }

}
