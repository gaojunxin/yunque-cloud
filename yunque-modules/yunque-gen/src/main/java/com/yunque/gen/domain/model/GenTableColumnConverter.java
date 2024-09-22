package com.yunque.gen.domain.model;

import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.gen.domain.dto.GenTableColumnDto;
import com.yunque.gen.domain.po.GenTableColumnPo;
import com.yunque.gen.domain.query.GenTableColumnQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 业务字段 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenTableColumnConverter extends BaseConverter<GenTableColumnQuery, GenTableColumnDto, GenTableColumnPo> {
}