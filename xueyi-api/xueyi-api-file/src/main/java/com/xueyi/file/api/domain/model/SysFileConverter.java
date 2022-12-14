package com.xueyi.file.api.domain.model;

import com.xueyi.common.core.web.entity.model.BaseConverter;
import com.xueyi.file.api.domain.dto.SysFileDto;
import com.xueyi.file.api.domain.po.SysFilePo;
import com.xueyi.file.api.domain.query.SysFileQuery;
import org.mapstruct.Mapper;

/**
 * 文件 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = "spring")
public interface SysFileConverter extends BaseConverter<SysFileQuery, SysFileDto, SysFilePo> {
}
