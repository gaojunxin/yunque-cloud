package com.yunque.system.organize.domain.model;

import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.system.api.organize.domain.dto.SysUserDto;
import com.yunque.system.api.organize.domain.po.SysUserPo;
import com.yunque.system.api.organize.domain.query.SysUserQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务 | 组织模块 | 用户 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysUserConverter extends BaseConverter<SysUserQuery, SysUserDto, SysUserPo> {
}
