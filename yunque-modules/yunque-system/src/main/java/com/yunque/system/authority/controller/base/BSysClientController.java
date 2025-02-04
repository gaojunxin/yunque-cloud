package com.yunque.system.authority.controller.base;

import com.yunque.common.web.entity.controller.BaseController;
import com.yunque.system.api.authority.domain.dto.SysClientDto;
import com.yunque.system.api.authority.domain.query.SysClientQuery;
import com.yunque.system.authority.service.ISysClientService;

/**
 * 系统服务 | 权限模块 | 客户端管理 | 通用 业务处理
 *
 * @author xueyi
 */
public class BSysClientController extends BaseController<SysClientQuery, SysClientDto, ISysClientService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "客户端";
    }

}
