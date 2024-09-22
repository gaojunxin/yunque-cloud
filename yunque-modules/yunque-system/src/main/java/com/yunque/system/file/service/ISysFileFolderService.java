package com.yunque.system.file.service;

import com.yunque.system.file.domain.query.SysFileFolderQuery;
import com.yunque.system.file.domain.dto.SysFileFolderDto;
import com.yunque.common.web.entity.service.ITreeService;

/**
 * 系统服务 | 素材模块 | 文件分类管理 服务层
 *
 * @author xueyi
 */
public interface ISysFileFolderService extends ITreeService<SysFileFolderQuery, SysFileFolderDto> {
}