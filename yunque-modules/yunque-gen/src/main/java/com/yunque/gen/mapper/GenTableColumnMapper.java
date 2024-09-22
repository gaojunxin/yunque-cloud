package com.yunque.gen.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.gen.domain.dto.GenTableColumnDto;
import com.yunque.gen.domain.po.GenTableColumnPo;
import com.yunque.gen.domain.query.GenTableColumnQuery;

import java.util.List;

/**
 * 业务字段管理 数据层
 *
 * @author xueyi
 */
public interface GenTableColumnMapper extends BaseMapper<GenTableColumnQuery, GenTableColumnDto, GenTableColumnPo> {

    /**
     * 根据表名称查询数据库表列信息
     *
     * @param tableName 表名称
     * @return 数据库表列信息
     */
    @InterceptorIgnore(tenantLine = "1")
    List<GenTableColumnDto> selectDbTableColumnsByName(String tableName);

}