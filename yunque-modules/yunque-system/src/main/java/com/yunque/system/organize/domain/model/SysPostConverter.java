package com.yunque.system.organize.domain.model;

import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.system.api.organize.domain.dto.SysPostDto;
import com.yunque.system.api.organize.domain.po.SysPostPo;
import com.yunque.system.api.organize.domain.query.SysPostQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务 | 组织模块 | 岗位 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysPostConverter extends BaseConverter<SysPostQuery, SysPostDto, SysPostPo> {
}
