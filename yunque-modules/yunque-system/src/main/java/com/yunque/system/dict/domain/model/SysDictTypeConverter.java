package com.yunque.system.dict.domain.model;

import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.system.api.dict.domain.dto.SysDictTypeDto;
import com.yunque.system.api.dict.domain.po.SysDictTypePo;
import com.yunque.system.api.dict.domain.query.SysDictTypeQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务 | 字典模块 | 字典类型 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysDictTypeConverter extends BaseConverter<SysDictTypeQuery, SysDictTypeDto, SysDictTypePo> {
}
