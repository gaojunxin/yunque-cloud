package com.yunque.system.organize.service.impl;

import com.yunque.common.datascope.annotation.DataScope;
import com.yunque.common.web.correlate.contant.CorrelateConstants;
import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.system.api.organize.domain.dto.SysPostDto;
import com.yunque.system.api.organize.domain.query.SysPostQuery;
import com.yunque.system.organize.domain.correlate.SysPostCorrelate;
import com.yunque.system.organize.manager.ISysPostManager;
import com.yunque.system.organize.service.ISysPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统服务 | 组织模块 | 岗位管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysPostServiceImpl extends BaseServiceImpl<SysPostQuery, SysPostDto, SysPostCorrelate, ISysPostManager> implements ISysPostService {

    /**
     * 默认方法关联配置定义
     */
    @Override
    protected Map<CorrelateConstants.ServiceType, SysPostCorrelate> defaultCorrelate() {
        return new HashMap<>() {{
            put(CorrelateConstants.ServiceType.DELETE, SysPostCorrelate.BASE_DEL);
        }};
    }

    /**
     * 用户登录校验 | 根据部门Ids获取归属岗位对象集合
     *
     * @param deptIds 部门Ids
     * @return 岗位对象集合
     */
    @Override
    public List<SysPostDto> selectListByDeptIds(Collection<Long> deptIds) {
        return baseManager.selectListByDeptIds(deptIds);
    }


    /**
     * 根据Id查询岗位信息对象 | 含角色组
     *
     * @param id Id
     * @return 岗位信息对象
     */
    @Override
    public SysPostDto selectPostRoleById(Long id) {
        return subCorrelates(selectById(id), SysPostCorrelate.ROLE_SEL);
    }

    /**
     * 修改岗位角色组
     *
     * @param post 岗位对象
     * @return 结果
     */
    @Override
    public int editPostRole(SysPostDto post) {
        return editCorrelates(post, SysPostCorrelate.ROLE_EDIT);
    }

    /**
     * 新增岗位 | 内部调用
     *
     * @param post 岗位对象
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int addInner(SysPostDto post) {
        return super.insert(post);
    }

    /**
     * 查询岗位对象列表 | 数据权限 | 附加数据
     *
     * @param post 岗位对象
     * @return 岗位对象集合
     */
    @Override
    @DataScope(postAlias = "id", mapperScope = {"SysPostMapper"})
    public List<SysPostDto> selectListScope(SysPostQuery post) {
        List<SysPostDto> list = super.selectListScope(post);
        return subCorrelates(list, SysPostCorrelate.BASE_LIST);
    }
}
