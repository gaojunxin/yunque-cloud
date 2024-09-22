package com.yunque.system.dict.domain.model;

import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.system.api.dict.domain.dto.SysConfigDto;
import com.yunque.system.api.dict.domain.po.SysConfigPo;
import com.yunque.system.api.dict.domain.query.SysConfigQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务 | 字典模块 | 参数 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysConfigConverter extends BaseConverter<SysConfigQuery, SysConfigDto, SysConfigPo> {
}
