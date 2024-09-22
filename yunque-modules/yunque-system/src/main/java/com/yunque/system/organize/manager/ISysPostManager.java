package com.yunque.system.organize.manager;

import com.yunque.common.web.entity.manager.IBaseManager;
import com.yunque.system.api.organize.domain.dto.SysPostDto;
import com.yunque.system.api.organize.domain.query.SysPostQuery;

import java.util.Collection;
import java.util.List;

/**
 * 系统服务 | 组织模块 | 岗位管理 数据封装层
 *
 * @author xueyi
 */
public interface ISysPostManager extends IBaseManager<SysPostQuery, SysPostDto> {

    /**
     * 用户登录校验 | 根据部门Ids获取归属岗位对象集合
     *
     * @param deptIds 部门Ids
     * @return 岗位对象集合
     */
    List<SysPostDto> selectListByDeptIds(Collection<Long> deptIds);
}
