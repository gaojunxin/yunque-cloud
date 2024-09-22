package com.yunque.system.notice.manager.impl;

import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.system.notice.domain.dto.SysNoticeDto;
import com.yunque.system.notice.domain.model.SysNoticeConverter;
import com.yunque.system.notice.domain.po.SysNoticePo;
import com.yunque.system.notice.domain.query.SysNoticeQuery;
import com.yunque.system.notice.manager.ISysNoticeManager;
import com.yunque.system.notice.mapper.SysNoticeMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务 | 消息模块 | 通知公告管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysNoticeManagerImpl extends BaseManagerImpl<SysNoticeQuery, SysNoticeDto, SysNoticePo, SysNoticeMapper, SysNoticeConverter> implements ISysNoticeManager {
}
