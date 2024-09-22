package com.yunque.system.organize.manager.impl;

import com.yunque.common.web.entity.manager.impl.TreeManagerImpl;
import com.yunque.system.api.organize.domain.dto.SysDeptDto;
import com.yunque.system.api.organize.domain.po.SysDeptPo;
import com.yunque.system.api.organize.domain.query.SysDeptQuery;
import com.yunque.system.organize.domain.model.SysDeptConverter;
import com.yunque.system.organize.manager.ISysDeptManager;
import com.yunque.system.organize.mapper.SysDeptMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务 | 组织模块 | 部门管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysDeptManagerImpl extends TreeManagerImpl<SysDeptQuery, SysDeptDto, SysDeptPo, SysDeptMapper, SysDeptConverter> implements ISysDeptManager {
}
