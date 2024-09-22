package com.yunque.system.dict.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.core.constant.basic.SqlConstants;
import com.yunque.common.core.constant.basic.TenantConstants;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.system.api.dict.domain.dto.SysImExDto;
import com.yunque.system.api.dict.domain.po.SysImExPo;
import com.yunque.system.api.dict.domain.query.SysImExQuery;
import com.yunque.system.dict.domain.model.SysImExConverter;
import com.yunque.system.dict.manager.ISysImExManager;
import com.yunque.system.dict.mapper.SysImExMapper;
import org.springframework.stereotype.Component;

/**
 * 导入导出配置管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysImExManagerImpl extends BaseManagerImpl<SysImExQuery, SysImExDto, SysImExPo, SysImExMapper, SysImExConverter> implements ISysImExManager {


    /**
     * 根据配置编码查询配置对象
     *
     * @param code 配置编码
     * @return 配置对象
     */
    @Override
    public SysImExDto selectByCode(String code) {
        SysImExPo config = baseMapper.selectOne(
                Wrappers.<SysImExPo>lambdaQuery()
                        .eq(SysImExPo::getCode, code)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(config);
    }

    /**
     * 校验配置编码是否唯一
     *
     * @param id   配置Id
     * @param code 配置编码
     * @return 配置对象
     */
    @Override
    public SysImExDto checkCodeUnique(Long id, String code) {
        SysImExPo config = baseMapper.selectOne(
                Wrappers.<SysImExPo>lambdaQuery()
                        .ne(SysImExPo::getId, id)
                        .eq(SysImExPo::getCode, code)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(config);
    }
}