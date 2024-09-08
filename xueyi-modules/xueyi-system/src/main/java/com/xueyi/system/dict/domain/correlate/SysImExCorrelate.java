package com.xueyi.system.dict.domain.correlate;

import com.xueyi.common.web.correlate.domain.BaseCorrelate;
import com.xueyi.common.web.correlate.domain.Direct;
import com.xueyi.common.web.correlate.service.CorrelateService;
import com.xueyi.system.api.dict.domain.dto.SysConfigDto;
import com.xueyi.system.api.organize.domain.dto.SysEnterpriseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.xueyi.common.web.correlate.contant.CorrelateConstants.SubOperate.SELECT;

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