package com.yunque.gen.service.impl;

import com.yunque.common.core.constant.basic.TenantConstants;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.gen.domain.correlate.GenTableColumnCorrelate;
import com.yunque.gen.domain.dto.GenTableColumnDto;
import com.yunque.gen.domain.query.GenTableColumnQuery;
import com.yunque.gen.manager.IGenTableColumnManager;
import com.yunque.gen.service.IGenTableColumnService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务字段管理 服务层实现
 *
 * @author xueyi
 */
@Service
public class GenTableColumnServiceImpl extends BaseServiceImpl<GenTableColumnQuery, GenTableColumnDto, GenTableColumnCorrelate, IGenTableColumnManager> implements IGenTableColumnService {

    /**
     * 根据表名称查询数据库表列信息
     *
     * @param tableName  表名称
     * @param sourceName 数据源
     * @return 数据库表列信息
     */
    @Override
    public List<GenTableColumnDto> selectDbTableColumnsByName(String tableName, String sourceName) {
        return baseManager.selectDbTableColumnsByName(tableName);
    }
}