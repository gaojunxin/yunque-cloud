package com.yunque.system.file.manager.impl;

import com.yunque.system.file.domain.po.SysFileFolderPo;
import com.yunque.system.file.domain.dto.SysFileFolderDto;
import com.yunque.system.file.domain.query.SysFileFolderQuery;
import com.yunque.system.file.domain.model.SysFileFolderConverter;
import com.yunque.system.file.mapper.SysFileFolderMapper;
import com.yunque.common.web.entity.manager.impl.TreeManagerImpl;
import com.yunque.system.file.manager.ISysFileFolderManager;
import org.springframework.stereotype.Component;

/**
 * 系统服务 | 素材模块 | 文件分类管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysFileFolderManagerImpl extends TreeManagerImpl<SysFileFolderQuery, SysFileFolderDto, SysFileFolderPo, SysFileFolderMapper, SysFileFolderConverter> implements ISysFileFolderManager {
}