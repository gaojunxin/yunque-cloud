package com.yunque.system.authority.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.system.api.authority.domain.dto.SysClientDto;
import com.yunque.system.api.authority.domain.po.SysClientPo;
import com.yunque.system.api.authority.domain.query.SysClientQuery;
import com.yunque.system.authority.domain.model.SysClientConverter;
import com.yunque.system.authority.manager.ISysClientManager;
import com.yunque.system.authority.mapper.SysClientMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务 | 权限模块 | 客户端管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysClientManagerImpl extends BaseManagerImpl<SysClientQuery, SysClientDto, SysClientPo, SysClientMapper, SysClientConverter> implements ISysClientManager {

    /**
     * 根据客户端Id查询客户端信息
     *
     * @param clientId 客户端Id
     * @return 客户端对象
     */
    @Override
    public SysClientDto selectByClientId(String clientId) {
        SysClientPo po = baseMapper.selectOne(Wrappers.<SysClientPo>lambdaQuery()
                .eq(SysClientPo::getClientId, clientId));
        return mapperDto(po);
    }
}
