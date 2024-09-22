package com.yunque.job.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.job.api.domain.dto.SysJobDto;
import com.yunque.job.api.domain.po.SysJobPo;
import com.yunque.job.api.domain.query.SysJobQuery;
import com.yunque.job.domain.model.SysJobConverter;
import com.yunque.job.manager.ISysJobManager;
import com.yunque.job.mapper.SysJobMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 调度任务管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysJobManagerImpl extends BaseManagerImpl<SysJobQuery, SysJobDto, SysJobPo, SysJobMapper, SysJobConverter> implements ISysJobManager {

    /**
     * 项目启动时
     */
    @Override
    public List<SysJobDto> initScheduler() {
        List<SysJobPo> jobList = baseMapper.selectList(Wrappers.query());
        return baseConverter.mapperDto(jobList);
    }
}
