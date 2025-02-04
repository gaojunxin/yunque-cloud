package com.yunque.common.web.entity.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.core.utils.core.ArrayUtil;
import com.yunque.common.core.web.entity.base.BasisEntity;
import com.yunque.common.web.annotation.AutoInject;
import com.yunque.common.web.correlate.domain.SqlField;
import com.yunque.common.web.correlate.utils.SqlHandleUtil;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数据层 基类通用数据处理
 *
 * @param <P> Po
 * @author xueyi
 */
public interface BasicMapper<P extends BasisEntity> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<P> {

    /**
     * 自定义批量插入
     */
    @AutoInject
    int insertBatch(@Param("collection") Collection<P> list);

    /**
     * 自定义批量更新，条件为主键
     */
    @AutoInject(key = false, isInsert = false)
    int updateBatch(@Param("collection") Collection<P> list);

    /**
     * 根据动态SQL控制对象查询数据对象集合
     *
     * @param field 动态SQL控制对象
     * @return 数据对象集合
     */
    default List<P> selectListByField(SqlField... field) {
        if (ArrayUtil.isNotEmpty(field)) {
            return selectList(
                    Wrappers.<P>query().lambda()
                            .func(i -> SqlHandleUtil.fieldCondition(i, field)));
        }
        return new ArrayList<>();
    }

}
