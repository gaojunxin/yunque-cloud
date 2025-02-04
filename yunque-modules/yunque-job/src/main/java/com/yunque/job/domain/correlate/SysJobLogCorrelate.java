package com.yunque.job.domain.correlate;

import com.yunque.common.web.correlate.domain.BaseCorrelate;
import com.yunque.common.web.correlate.service.CorrelateService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 调度日志 关联映射
 *
 * @author xueyi
 */
@Getter
@AllArgsConstructor
public enum SysJobLogCorrelate implements CorrelateService {

   ;

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}
