package com.yunque.system.organize.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.system.api.organize.domain.dto.SysPostDto;
import com.yunque.system.api.organize.domain.po.SysPostPo;
import com.yunque.system.api.organize.domain.query.SysPostQuery;
import com.yunque.system.organize.domain.model.SysPostConverter;
import com.yunque.system.organize.manager.ISysPostManager;
import com.yunque.system.organize.mapper.SysPostMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 系统服务 | 组织模块 | 岗位管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysPostManagerImpl extends BaseManagerImpl<SysPostQuery, SysPostDto, SysPostPo, SysPostMapper, SysPostConverter> implements ISysPostManager {

    /**
     * 用户登录校验 | 根据部门Ids获取归属岗位对象集合
     *
     * @param deptIds 部门Ids
     * @return 岗位对象集合
     */
    @Override
    public List<SysPostDto> selectListByDeptIds(Collection<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds))
            return new ArrayList<>();
        List<SysPostPo> postList = baseMapper.selectList(
                Wrappers.<SysPostPo>query().lambda()
                        .in(SysPostPo::getDeptId, deptIds));
        return mapperDto(postList);
    }
}
