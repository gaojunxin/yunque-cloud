package com.yunque.system.authority.controller.base;

import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.context.SecurityContextHolder;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.security.utils.SecurityUserUtils;
import com.yunque.common.web.entity.controller.TreeController;
import com.yunque.system.api.authority.constant.AuthorityConstants;
import com.yunque.system.api.authority.domain.dto.SysMenuDto;
import com.yunque.system.api.authority.domain.query.SysMenuQuery;
import com.yunque.system.authority.service.ISysMenuService;

import java.util.List;

/**
 * 系统服务 | 权限模块 | 菜单管理 | 通用 业务处理
 *
 * @author xueyi
 */
public class BSysMenuController extends TreeController<SysMenuQuery, SysMenuDto, ISysMenuService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "菜单";
    }

    /**
     * 构造树型Top节点
     *
     * @param query 数据查询对象
     * @return Top节点对象
     */
    @Override
    protected SysMenuDto TopNodeBuilder(SysMenuQuery query) {
        SysMenuDto topMenu = super.TopNodeBuilder(query);
        topMenu.setTitle(topMenu.getName());
        topMenu.setFrameType(AuthorityConstants.FrameType.NORMAL.getCode());
        topMenu.setMenuType(AuthorityConstants.MenuType.DIR.getCode());
        topMenu.setModuleId(query.getModuleId());
        return topMenu;
    }

    /**
     * 树型 树死循环逻辑校验 | 父节点不能为自己或自己的子节点
     *
     * @param dto 待修改数据对象
     */
    @Override
    protected void TreeLoopHandle(SysMenuDto dto) {
        super.TreeLoopHandle(dto);
    }

    /**
     * 树型 增加/修改 父子节点逻辑校验
     *
     * @param operate 操作类型
     * @param dto     数据对象
     */
    @Override
    protected void AETreeStatusHandle(BaseConstants.Operate operate, SysMenuDto dto) {
        super.AETreeStatusHandle(operate, dto);
    }

    /**
     * 树型 删除 子节点存在与否校验
     *
     * @param idList 待删除Id集合
     */
    @Override
    protected void RHandleTreeChild(List<Long> idList) {
        super.RHandleTreeChild(idList);
    }

    /**
     * 前置校验 新增/修改
     */
    @Override
    protected void AEHandle(BaseConstants.Operate operate, SysMenuDto menu) {
        Boolean isNotUnique = baseService.checkNameUnique(menu.getId(), menu.getParentId(), menu.getName());
        if (isNotUnique) {
            warn(StrUtil.format("{}{}{}失败，{}名称已存在！", operate.getInfo(), getNodeName(), menu.getTitle(), getNodeName()));
        }
        if (menu.isCommon() ) {
            warn(StrUtil.format("{}{}{}失败，无操作权限！", operate.getInfo(), getNodeName(), menu.getTitle()));
        }
    }
}
