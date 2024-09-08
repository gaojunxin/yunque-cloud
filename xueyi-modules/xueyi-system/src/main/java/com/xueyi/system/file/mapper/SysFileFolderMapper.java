package com.xueyi.system.file.mapper;

import com.xueyi.system.file.domain.query.SysFileFolderQuery;
import com.xueyi.system.file.domain.dto.SysFileFolderDto;
import com.xueyi.system.file.domain.po.SysFileFolderPo;
import com.xueyi.common.web.entity.mapper.TreeMapper;

/**
 * 系统服务 | 素材模块 | 文件分类管理 数据层
 *
 * @author xueyi
 */
public interface SysFileFolderMapper extends TreeMapper<SysFileFolderQuery, SysFileFolderDto, SysFileFolderPo> {
}