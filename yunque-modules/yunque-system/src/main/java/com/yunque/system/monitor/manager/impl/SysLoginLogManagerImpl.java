package com.yunque.system.monitor.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.system.api.log.domain.dto.SysLoginLogDto;
import com.yunque.system.api.log.domain.po.SysLoginLogPo;
import com.yunque.system.api.log.domain.query.SysLoginLogQuery;
import com.yunque.system.monitor.domain.model.SysLoginLogConverter;
import com.yunque.system.monitor.manager.ISysLoginLogManager;
import com.yunque.system.monitor.mapper.SysLoginLogMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务 | 监控模块 | 访问日志管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysLoginLogManagerImpl extends BaseManagerImpl<SysLoginLogQuery, SysLoginLogDto, SysLoginLogPo, SysLoginLogMapper, SysLoginLogConverter> implements ISysLoginLogManager {

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginLog() {
        baseMapper.delete(Wrappers.update());
    }

    /**
     * 查询条件构造 | 列表查询
     *
     * @param loginLog 数据查询对象
     */
    @Override
    protected LambdaQueryWrapper<SysLoginLogPo> selectListQuery(SysLoginLogQuery loginLog) {
        return Wrappers.<SysLoginLogPo>query(loginLog).lambda()
                .func(i -> {
                    if (ObjectUtil.isAllNotEmpty(loginLog.getAccessTimeStart(), loginLog.getAccessTimeEnd())) {
                        i.between(SysLoginLogPo::getAccessTime, loginLog.getAccessTimeStart(), loginLog.getAccessTimeEnd());
                    }
                });
    }
}
