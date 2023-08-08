package com.xueyi.gen.domain.correlate;

import com.xueyi.common.web.correlate.domain.BaseCorrelate;
import com.xueyi.common.web.correlate.domain.Direct;
import com.xueyi.common.web.correlate.service.CorrelateService;
import com.xueyi.gen.domain.dto.GenTableColumnDto;
import com.xueyi.gen.domain.dto.GenTableDto;
import com.xueyi.gen.service.IGenTableColumnService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.xueyi.common.web.correlate.contant.CorrelateConstants.SubOperate.*;

/**
 * 业务 关联映射
 *
 * @author xueyi
 */
@Getter
@AllArgsConstructor
public enum GenTableCorrelate implements CorrelateService {

    INFO_LIST("默认列表|（业务字段）", new ArrayList<>() {{
        // 业务 | 业务字段
        add(new Direct<>(SELECT, IGenTableColumnService.class, GenTableDto::getId, GenTableColumnDto::getTableId));
    }}),
    BASE_ADD("默认新增|（业务字段）", new ArrayList<>() {{
        // 业务 | 业务字段
        add(new Direct<>(ADD, IGenTableColumnService.class, GenTableDto::getId, GenTableColumnDto::getTableId));
    }}),
    BASE_EDIT("默认修改|（业务字段）", new ArrayList<>() {{
        // 业务 | 业务字段
        add(new Direct<>(EDIT, IGenTableColumnService.class, GenTableDto::getId, GenTableColumnDto::getTableId));
    }}),
    BASE_DEL("默认删除|（业务字段）", new ArrayList<>() {{
        // 业务 | 业务字段
        add(new Direct<>(DELETE, IGenTableColumnService.class, GenTableDto::getId, GenTableColumnDto::getTableId));
    }});

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}
