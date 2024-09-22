package com.yunque.system.dict.manager;

import com.yunque.common.web.entity.manager.IBaseManager;
import com.yunque.system.api.dict.domain.dto.SysDictTypeDto;
import com.yunque.system.api.dict.domain.query.SysDictTypeQuery;

/**
 * 系统服务 | 字典模块 | 字典类型管理 数据封装层
 *
 * @author xueyi
 */
public interface ISysDictTypeManager extends IBaseManager<SysDictTypeQuery, SysDictTypeDto> {

    /**
     * 校验字典编码是否唯一
     *
     * @param Id   字典类型Id
     * @param code 字典类型编码
     * @return 字典类型对象
     */
    SysDictTypeDto checkDictCodeUnique(Long Id, String code);
}
