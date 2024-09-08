package com.xueyi.system.dict.domain.correlate;

import com.xueyi.common.web.correlate.domain.BaseCorrelate;
import com.xueyi.common.web.correlate.service.CorrelateService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 导入导出配置 关联映射
 */
@Getter
@AllArgsConstructor
public enum SysImExCorrelate implements CorrelateService {

    ;
    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}