package com.yunque.system.dict.service;

import com.yunque.common.web.entity.service.IBaseService;
import com.yunque.system.api.dict.domain.dto.SysConfigDto;
import com.yunque.system.api.dict.domain.query.SysConfigQuery;

/**
 * 系统服务 | 字典模块 | 参数管理 服务层
 *
 * @author xueyi
 */
public interface ISysConfigService extends IBaseService<SysConfigQuery, SysConfigDto> {

    /**
     * 根据参数编码查询参数值
     *
     * @param code 参数编码
     * @return 参数对象
     */
    SysConfigDto selectConfigByCode(String code);

    /**
     * 更新缓存数据
     */
    Boolean syncCache();

    /**
     * 清空缓存数据
     */
    void clearCache();

    /**
     * 校验参数编码是否唯一
     *
     * @param id         参数Id
     * @param configCode 参数编码
     * @return 结果 | true/false 唯一/不唯一
     */
    boolean checkConfigCodeUnique(Long id, String configCode);

    /**
     * 校验是否为内置参数
     *
     * @param id 参数Id
     * @return 结果 | true/false 是/否
     */
    boolean checkIsBuiltIn(Long id);
}