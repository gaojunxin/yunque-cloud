package com.xueyi.system.authority.service;

import com.xueyi.common.web.entity.service.IBaseService;
import com.xueyi.system.api.authority.domain.dto.SysModuleDto;
import com.xueyi.system.api.authority.domain.query.SysModuleQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 系统服务 | 权限模块 | 模块管理 服务层
 *
 * @author xueyi
 */
public interface ISysModuleService extends IBaseService<SysModuleQuery, SysModuleDto> {

    /**
     * 根据Id查询模块信息
     *
     * @param id Id
     * @return 模块数据对象
     */
    SysModuleDto selectInfoById(Serializable id);

}