package com.yunque.system.dict.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.core.constant.basic.SqlConstants;
import com.yunque.common.core.constant.basic.TenantConstants;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.system.api.dict.domain.dto.SysDictTypeDto;
import com.yunque.system.api.dict.domain.po.SysDictTypePo;
import com.yunque.system.api.dict.domain.query.SysDictTypeQuery;
import com.yunque.system.dict.domain.model.SysDictTypeConverter;
import com.yunque.system.dict.manager.ISysDictTypeManager;
import com.yunque.system.dict.mapper.SysDictTypeMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务 | 字典模块 | 字典类型管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysDictTypeManagerImpl extends BaseManagerImpl<SysDictTypeQuery, SysDictTypeDto, SysDictTypePo, SysDictTypeMapper, SysDictTypeConverter> implements ISysDictTypeManager {

    /**
     * 校验字典编码是否唯一
     *
     * @param Id   字典类型Id
     * @param code 字典类型编码
     * @return 字典类型对象
     */
    @Override
    public SysDictTypeDto checkDictCodeUnique(Long Id, String code) {
        SysDictTypePo dictType = baseMapper.selectOne(
                Wrappers.<SysDictTypePo>lambdaQuery()
                        .ne(SysDictTypePo::getId, Id)
                        .eq(SysDictTypePo::getCode, code)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(dictType);
    }
}
