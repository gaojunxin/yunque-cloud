package com.xueyi.system.dict.mapper;

import com.xueyi.common.web.entity.mapper.BaseMapper;
import com.xueyi.system.api.dict.domain.dto.SysImExDto;
import com.xueyi.system.api.dict.domain.po.SysImExPo;
import com.xueyi.system.api.dict.domain.query.SysImExQuery;

/**
 * 导入导出配置管理 数据层
 *
 * @author xueyi
 */
public interface SysImExMapper extends BaseMapper<SysImExQuery, SysImExDto, SysImExPo> {
}