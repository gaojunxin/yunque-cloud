package com.yunque.system.dict.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.api.dict.domain.dto.SysDictTypeDto;
import com.yunque.system.api.dict.domain.po.SysDictTypePo;
import com.yunque.system.api.dict.domain.query.SysDictTypeQuery;

/**
 * 系统服务 | 字典模块 | 字典类型管理 数据层
 *
 * @author xueyi
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictTypeQuery, SysDictTypeDto, SysDictTypePo> {
}
