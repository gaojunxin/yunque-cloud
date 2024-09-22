package com.yunque.system.file.mapper;

import com.yunque.system.file.domain.query.SysFileQuery;
import com.yunque.system.file.domain.dto.SysFileDto;
import com.yunque.system.file.domain.po.SysFilePo;
import com.yunque.common.web.entity.mapper.BaseMapper;

/**
 * 系统服务 | 素材模块 | 文件管理 数据层
 *
 * @author xueyi
 */
public interface SysFileMapper extends BaseMapper<SysFileQuery, SysFileDto, SysFilePo> {
}