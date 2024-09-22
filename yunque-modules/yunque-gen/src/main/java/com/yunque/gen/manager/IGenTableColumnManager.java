package com.yunque.gen.manager;

import com.yunque.common.web.entity.manager.IBaseManager;
import com.yunque.gen.domain.dto.GenTableColumnDto;
import com.yunque.gen.domain.query.GenTableColumnQuery;

import java.util.List;

/**
 * 业务字段管理 数据封装层
 *
 * @author xueyi
 */
public interface IGenTableColumnManager extends IBaseManager<GenTableColumnQuery, GenTableColumnDto> {

    /**
     * 根据表名称查询数据库表列信息 | 主库
     *
     * @param tableName 表名称
     * @return 数据库表列信息
     */
    List<GenTableColumnDto> selectDbTableColumnsByName(String tableName);
    
}
