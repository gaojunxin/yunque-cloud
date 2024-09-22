package com.yunque.job.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.job.api.domain.dto.SysJobLogDto;
import com.yunque.job.api.domain.po.SysJobLogPo;
import com.yunque.job.api.domain.query.SysJobLogQuery;
import com.yunque.job.domain.model.SysJobLogConverter;
import com.yunque.job.manager.ISysJobLogManager;
import com.yunque.job.mapper.SysJobLogMapper;
import org.springframework.stereotype.Component;

/**
 * 调度任务日志管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysJobLogManagerImpl extends BaseManagerImpl<SysJobLogQuery, SysJobLogDto, SysJobLogPo, SysJobLogMapper, SysJobLogConverter> implements ISysJobLogManager {

    /**
     * 清空任务日志
     */
    @Override
    public void cleanLog() {
        baseMapper.delete(Wrappers.update());
    }
}
