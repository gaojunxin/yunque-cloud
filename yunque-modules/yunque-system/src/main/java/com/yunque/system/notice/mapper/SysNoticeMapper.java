package com.yunque.system.notice.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.notice.domain.dto.SysNoticeDto;
import com.yunque.system.notice.domain.po.SysNoticePo;
import com.yunque.system.notice.domain.query.SysNoticeQuery;

/**
 * 系统服务 | 消息模块 | 通知公告管理 数据层
 *
 * @author xueyi
 */
public interface SysNoticeMapper extends BaseMapper<SysNoticeQuery, SysNoticeDto, SysNoticePo> {
}
