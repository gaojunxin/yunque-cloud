package com.yunque.system.dict.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.constant.basic.SqlConstants;
import com.yunque.common.core.constant.basic.TenantConstants;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.system.api.dict.domain.dto.SysConfigDto;
import com.yunque.system.api.dict.domain.po.SysConfigPo;
import com.yunque.system.api.dict.domain.query.SysConfigQuery;
import com.yunque.system.dict.domain.model.SysConfigConverter;
import com.yunque.system.dict.manager.ISysConfigManager;
import com.yunque.system.dict.mapper.SysConfigMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务 | 字典模块 | 参数管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysConfigManagerImpl extends BaseManagerImpl<SysConfigQuery, SysConfigDto, SysConfigPo, SysConfigMapper, SysConfigConverter> implements ISysConfigManager {

    /**
     * 根据参数编码查询参数对象
     *
     * @param code 参数编码
     * @return 参数对象
     */
    @Override
    public SysConfigDto selectConfigByCode(String code) {
        SysConfigPo config = baseMapper.selectOne(
                Wrappers.<SysConfigPo>lambdaQuery()
                        .eq(SysConfigPo::getCode, code)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(config);
    }

    /**
     * 校验参数编码是否唯一
     *
     * @param id   参数Id
     * @param code 参数编码
     * @return 参数对象
     */
    @Override
    public SysConfigDto checkConfigCodeUnique(Long id, String code) {
        SysConfigPo config = baseMapper.selectOne(
                Wrappers.<SysConfigPo>lambdaQuery()
                        .ne(SysConfigPo::getId, id)
                        .eq(SysConfigPo::getCode, code)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(config);
    }

    /**
     * 校验是否为内置参数
     *
     * @param id 参数Id
     * @return 参数对象
     */
    @Override
    public SysConfigDto checkIsBuiltIn(Long id) {
        SysConfigPo config = baseMapper.selectOne(
                Wrappers.<SysConfigPo>lambdaQuery()
                        .eq(SysConfigPo::getId, id)
                        .eq(SysConfigPo::getType, BaseConstants.Whether.YES.getCode())
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(config);
    }
}
