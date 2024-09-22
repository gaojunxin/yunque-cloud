package com.yunque.system.authority.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.constant.basic.DictConstants;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.system.api.authority.domain.dto.SysModuleDto;
import com.yunque.system.api.authority.domain.po.SysModulePo;
import com.yunque.system.api.authority.domain.query.SysModuleQuery;
import com.yunque.system.authority.domain.model.SysModuleConverter;
import com.yunque.system.authority.manager.ISysModuleManager;
import com.yunque.system.authority.mapper.SysModuleMapper;
import com.yunque.system.authority.mapper.merge.SysRoleModuleMergeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统服务 | 权限模块 | 模块管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysModuleManagerImpl extends BaseManagerImpl<SysModuleQuery, SysModuleDto, SysModulePo, SysModuleMapper, SysModuleConverter> implements ISysModuleManager {

    /**
     * 获取全部状态正常公共模块
     *
     * @return 模块对象集合
     */
    @Override
    public List<SysModuleDto> selectCommonList() {
        List<SysModulePo> moduleList = baseMapper.selectList(Wrappers.<SysModulePo>lambdaQuery()
                .eq(SysModulePo::getIsCommon, DictConstants.DicCommonPrivate.COMMON.getCode())
                .eq(SysModulePo::getStatus, BaseConstants.Status.NORMAL.getCode()));
        return mapperDto(moduleList);
    }

}
