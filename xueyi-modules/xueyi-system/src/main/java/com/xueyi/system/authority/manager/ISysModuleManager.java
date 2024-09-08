package com.xueyi.system.authority.manager;

import com.xueyi.common.web.entity.manager.IBaseManager;
import com.xueyi.system.api.authority.domain.dto.SysModuleDto;
import com.xueyi.system.api.authority.domain.query.SysModuleQuery;

import java.util.List;
import java.util.Set;

/**
 * 系统服务 | 权限模块 | 模块管理 数据封装层
 *
 * @author xueyi
 */
public interface ISysModuleManager extends IBaseManager<SysModuleQuery, SysModuleDto> {

    /**
     * 获取全部状态正常公共模块
     *
     * @return 模块对象集合
     */
    List<SysModuleDto> selectCommonList();

}
