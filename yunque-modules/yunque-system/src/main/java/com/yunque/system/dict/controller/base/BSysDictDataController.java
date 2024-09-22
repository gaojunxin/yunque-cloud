package com.yunque.system.dict.controller.base;

import com.yunque.common.web.entity.controller.BaseController;
import com.yunque.system.api.dict.domain.dto.SysDictDataDto;
import com.yunque.system.api.dict.domain.query.SysDictDataQuery;
import com.yunque.system.dict.service.ISysDictDataService;

/**
 * 系统服务 | 字典模块 | 字典数据管理 | 通用 业务处理
 *
 * @author xueyi
 */
public class BSysDictDataController extends BaseController<SysDictDataQuery, SysDictDataDto, ISysDictDataService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "字典数据";
    }
}
