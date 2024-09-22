package com.yunque.system.dict.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.api.dict.domain.dto.SysImExDto;
import com.yunque.system.api.dict.domain.po.SysImExPo;
import com.yunque.system.api.dict.domain.query.SysImExQuery;

/**
 * 导入导出配置管理 数据层
 *
 * @author xueyi
 */
public interface SysImExMapper extends BaseMapper<SysImExQuery, SysImExDto, SysImExPo> {
}