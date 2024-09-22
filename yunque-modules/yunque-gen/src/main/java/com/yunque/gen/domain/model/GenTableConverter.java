package com.yunque.gen.domain.model;

import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.gen.domain.dto.GenTableDto;
import com.yunque.gen.domain.po.GenTablePo;
import com.yunque.gen.domain.query.GenTableQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 业务 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenTableConverter extends BaseConverter<GenTableQuery, GenTableDto, GenTablePo> {
}