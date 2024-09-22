package com.yunque.system.organize.manager;

import com.yunque.common.web.entity.manager.ITreeManager;
import com.yunque.system.api.organize.domain.dto.SysDeptDto;
import com.yunque.system.api.organize.domain.query.SysDeptQuery;

/**
 * 系统服务 | 组织模块 | 部门管理 数据封装层
 *
 * @author xueyi
 */
public interface ISysDeptManager extends ITreeManager<SysDeptQuery, SysDeptDto> {
}
