package com.yunque.system.file.domain.model;

import com.yunque.common.core.web.entity.model.TreeConverter;
import com.yunque.system.file.domain.dto.SysFileFolderDto;
import com.yunque.system.file.domain.po.SysFileFolderPo;
import com.yunque.system.file.domain.query.SysFileFolderQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务 | 素材模块 | 文件分类 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysFileFolderConverter extends TreeConverter<SysFileFolderQuery, SysFileFolderDto, SysFileFolderPo> {
}
