package com.yunque.gen.manager.impl;

import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.gen.domain.dto.GenTableDto;
import com.yunque.gen.domain.model.GenTableConverter;
import com.yunque.gen.domain.po.GenTablePo;
import com.yunque.gen.domain.query.GenTableQuery;
import com.yunque.gen.manager.IGenTableManager;
import com.yunque.gen.mapper.GenTableMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 业务管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class GenTableManagerImpl extends BaseManagerImpl<GenTableQuery, GenTableDto, GenTablePo, GenTableMapper, GenTableConverter> implements IGenTableManager {

    /**
     * 查询数据库列表
     *
     * @param table 业务对象
     * @return 数据库表集合
     */
    @Override
    public List<GenTableDto> selectDbTableList(GenTableQuery table) {
        return baseMapper.selectDbTableList(table);
    }

    /**
     * 根据表名称组查询数据库列表 | 主库
     *
     * @param names 表名称组
     * @return 数据库表集合
     */
    @Override
    public List<GenTableDto> selectDbTableListByNames(String[] names) {
        return baseMapper.selectDbTableListByNames(names);
    }
}
