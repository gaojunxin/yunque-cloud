package com.yunque.system.notice.service.impl;

import com.yunque.common.datascope.annotation.DataScope;
import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.system.notice.correlate.SysNoticeCorrelate;
import com.yunque.system.notice.domain.dto.SysNoticeDto;
import com.yunque.system.notice.domain.query.SysNoticeQuery;
import com.yunque.system.notice.manager.ISysNoticeManager;
import com.yunque.system.notice.service.ISysNoticeService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yunque.common.core.constant.basic.SecurityConstants.CREATE_BY;

/**
 * 系统服务 | 消息模块 | 通知公告管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNoticeQuery, SysNoticeDto, SysNoticeCorrelate, ISysNoticeManager> implements ISysNoticeService {

    /**
     * 查询通知公告对象列表 | 数据权限
     *
     * @param notice 通知公告对象
     * @return 通知公告对象集合
     */
    @Override
    @DataScope(userAlias = CREATE_BY, mapperScope = {"SysNoticeMapper"})
    public List<SysNoticeDto> selectListScope(SysNoticeQuery notice) {
        return super.selectListScope(notice);
    }
}