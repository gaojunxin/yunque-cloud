package com.yunque.gen.service;

import com.yunque.common.web.entity.service.IBaseService;
import com.yunque.gen.domain.dto.GenTableColumnDto;
import com.yunque.gen.domain.query.GenTableColumnQuery;

import java.util.List;

/**
 * 业务字段管理 服务层
 *
 * @author xueyi
 */
public interface IGenTableColumnService extends IBaseService<GenTableColumnQuery, GenTableColumnDto> {

    /**
     * 根据表名称查询数据库表列信息
     *
     * @param tableName  表名称
     * @param sourceName 数据源
     * @return 数据库表列信息
     */
    List<GenTableColumnDto> selectDbTableColumnsByName(String tableName, String sourceName);
}