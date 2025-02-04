package com.yunque.system.file.domain.model;

import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.system.file.domain.dto.SysFileDto;
import com.yunque.system.file.domain.po.SysFilePo;
import com.yunque.system.file.domain.query.SysFileQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务 | 素材模块 | 文件 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysFileConverter extends BaseConverter<SysFileQuery, SysFileDto, SysFilePo> {
}
