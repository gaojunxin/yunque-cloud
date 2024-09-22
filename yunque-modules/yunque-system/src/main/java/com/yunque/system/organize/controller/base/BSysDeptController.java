package com.yunque.system.organize.controller.base;

import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.web.entity.controller.TreeController;
import com.yunque.system.api.organize.domain.dto.SysDeptDto;
import com.yunque.system.api.organize.domain.query.SysDeptQuery;
import com.yunque.system.organize.service.ISysDeptService;

/**
 * 系统服务 | 组织模块 | 部门管理 | 通用 业务处理
 *
 * @author xueyi
 */
public class BSysDeptController extends TreeController<SysDeptQuery, SysDeptDto, ISysDeptService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "部门";
    }

    /**
     * 前置校验 增加/修改
     */
    @Override
    protected void AEHandle(BaseConstants.Operate operate, SysDeptDto dept) {
        if (baseService.checkNameUnique(dept.getId(), dept.getParentId(), dept.getName()))
            warn(StrUtil.format("{}{}{}失败，部门名称已存在", operate.getInfo(), getNodeName(), dept.getName()));
    }
}
