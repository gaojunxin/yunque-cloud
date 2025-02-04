package com.yunque.system.organize.service.impl;

import com.yunque.common.datascope.annotation.DataScope;
import com.yunque.common.web.correlate.contant.CorrelateConstants;
import com.yunque.common.web.entity.service.impl.TreeServiceImpl;
import com.yunque.system.api.organize.domain.dto.SysDeptDto;
import com.yunque.system.api.organize.domain.query.SysDeptQuery;
import com.yunque.system.organize.domain.correlate.SysDeptCorrelate;
import com.yunque.system.organize.manager.ISysDeptManager;
import com.yunque.system.organize.service.ISysDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统服务 | 组织模块 | 部门管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysDeptServiceImpl extends TreeServiceImpl<SysDeptQuery, SysDeptDto, SysDeptCorrelate, ISysDeptManager> implements ISysDeptService {

    /**
     * 默认方法关联配置定义
     */
    @Override
    protected Map<CorrelateConstants.ServiceType, SysDeptCorrelate> defaultCorrelate() {
        return new HashMap<>() {{
            put(CorrelateConstants.ServiceType.DELETE, SysDeptCorrelate.BASE_DEL);
        }};
    }

    /**
     * 根据Id查询部门信息对象 | 含角色组
     *
     * @param id Id
     * @return 部门信息对象
     */
    @Override
    public SysDeptDto selectDeptRoleById(Long id) {
        return subCorrelates(selectById(id), SysDeptCorrelate.ROLE_SEL);
    }

    /**
     * 修改部门角色组
     *
     * @param post 部门对象
     * @return 结果
     */
    @Override
    @Transactional
    public int editDeptRole(SysDeptDto post) {
        return editCorrelates(post, SysDeptCorrelate.ROLE_EDIT);
    }

    /**
     * 新增部门 | 内部调用
     *
     * @param dept 部门对象
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int addInner(SysDeptDto dept) {
        return super.insert(dept);
    }

    /**
     * 查询部门对象列表 | 数据权限 | 附加数据
     *
     * @param dept 部门对象
     * @return 部门对象集合
     */
    @Override
    @DataScope(deptAlias = "id", mapperScope = {"SysDeptMapper"})
    public List<SysDeptDto> selectListScope(SysDeptQuery dept) {
        return super.selectListScope(dept);
    }
}
