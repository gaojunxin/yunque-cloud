package com.yunque.system.authority.domain.correlate;

import com.yunque.common.web.correlate.domain.BaseCorrelate;
import com.yunque.common.web.correlate.domain.Direct;
import com.yunque.common.web.correlate.domain.Indirect;
import com.yunque.common.web.correlate.service.CorrelateService;
import com.yunque.system.api.authority.domain.dto.SysMenuDto;
import com.yunque.system.api.authority.domain.dto.SysModuleDto;
import com.yunque.system.authority.domain.merge.SysRoleModuleMerge;
import com.yunque.system.authority.mapper.merge.SysRoleModuleMergeMapper;
import com.yunque.system.authority.service.ISysMenuService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.yunque.common.web.correlate.contant.CorrelateConstants.SubOperate.DELETE;

/**
 * 系统服务 | 权限模块 | 模块 关联映射
 *
 * @author xueyi
 */
@Getter
@AllArgsConstructor
public enum SysModuleCorrelate implements CorrelateService {

    BASE_DEL("默认删除|（菜单 | 菜单|角色-模块关联 | 企业权限组-模块关联）", new ArrayList<>() {{
        // 模块 | 菜单
        add(new Direct<>(DELETE, ISysMenuService.class, SysModuleDto::getId, SysMenuDto::getModuleId));
        // 模块 | 角色-模块关联
        add(new Indirect<>(DELETE, SysRoleModuleMergeMapper.class, SysRoleModuleMerge::getModuleId, SysModuleDto::getId));
    }});

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}
