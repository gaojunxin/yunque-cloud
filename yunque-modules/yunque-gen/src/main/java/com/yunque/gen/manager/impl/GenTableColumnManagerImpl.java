package com.yunque.gen.manager.impl;

import com.yunque.common.core.context.SecurityContextHolder;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.gen.domain.dto.GenTableColumnDto;
import com.yunque.gen.domain.model.GenTableColumnConverter;
import com.yunque.gen.domain.po.GenTableColumnPo;
import com.yunque.gen.domain.query.GenTableColumnQuery;
import com.yunque.gen.manager.IGenTableColumnManager;
import com.yunque.gen.mapper.GenTableColumnMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 业务字段管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class GenTableColumnManagerImpl extends BaseManagerImpl<GenTableColumnQuery, GenTableColumnDto, GenTableColumnPo, GenTableColumnMapper, GenTableColumnConverter> implements IGenTableColumnManager {

    /**
     * 根据表名称查询数据库表列信息 | 主库
     *
     * @param tableName 表名称
     * @return 数据库表列信息
     */
    @Override
    public List<GenTableColumnDto> selectDbTableColumnsByName(String tableName) {
        return baseMapper.selectDbTableColumnsByName(tableName);
    }

}
