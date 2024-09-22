package com.yunque.system.notice.domain.model;

import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.system.notice.domain.dto.SysNoticeDto;
import com.yunque.system.notice.domain.po.SysNoticePo;
import com.yunque.system.notice.domain.query.SysNoticeQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务 | 消息模块 | 通知公告 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysNoticeConverter extends BaseConverter<SysNoticeQuery, SysNoticeDto, SysNoticePo> {
}
