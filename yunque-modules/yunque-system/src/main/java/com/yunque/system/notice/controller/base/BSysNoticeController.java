package com.yunque.system.notice.controller.base;


import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.web.entity.controller.BaseController;
import com.yunque.system.notice.constant.NoticeConstants;
import com.yunque.system.notice.domain.dto.SysNoticeDto;
import com.yunque.system.notice.domain.query.SysNoticeQuery;
import com.yunque.system.notice.service.ISysNoticeService;

/**
 * 系统服务 | 消息模块 | 通知公告管理 | 通用 业务处理
 *
 * @author xueyi
 */
public class BSysNoticeController extends BaseController<SysNoticeQuery, SysNoticeDto, ISysNoticeService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "通知公告";
    }

    /**
     * 前置校验 增加/修改
     */
    @Override
    protected void AEHandle(BaseConstants.Operate operate, SysNoticeDto notice) {
        // 初始化发送状态
        if (operate.isAdd())
            notice.setStatus(NoticeConstants.NoticeStatus.READY.getCode());
    }
}
