package com.yunque.system.dict.controller.base;

import com.yunque.common.cache.utils.DictUtil;
import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.web.entity.controller.BaseController;
import com.yunque.system.api.dict.domain.dto.SysConfigDto;
import com.yunque.system.api.dict.domain.query.SysConfigQuery;
import com.yunque.system.dict.service.ISysConfigService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统服务 | 字典模块 | 参数管理 | 通用 业务处理
 *
 * @author xueyi
 */
public class BSysConfigController extends BaseController<SysConfigQuery, SysConfigDto, ISysConfigService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "参数";
    }

    /**
     * 根据参数类型查询参数数据信息
     */
    public AjaxResult getDictByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            warn("请传入编码后再查询字典");
        }
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(code, DictUtil.getConfigCacheToObj(code));
        return AjaxResult.success(configMap);
    }

    /**
     * 根据参数类型查询参数数据信息
     */
    public AjaxResult getDictListByCodeList(List<String> codeList) {
        if (CollUtil.isEmpty(codeList)) {
            warn("请传入编码后再查询字典");
        }
        Map<String, Object> configMap = new HashMap<>();
        codeList.forEach(code -> configMap.put(code, DictUtil.getConfigCacheToObj(code)));
        return AjaxResult.success(configMap);
    }

    /**
     * 查询参数
     *
     * @param code 参数编码
     * @return 参数
     */
    public AjaxResult getValueByCode(String code) {
        Object obj = DictUtil.getConfigCacheToObj(code);
        return success(obj);
    }

    /**
     * 前置校验 增加/修改
     */
    @Override
    protected void AEHandle(BaseConstants.Operate operate, SysConfigDto config) {
        if (Objects.requireNonNull(operate) == BaseConstants.Operate.ADD) {
            if ( baseService.checkConfigCodeUnique(config.getId(), config.getCode())) {
                warn(StrUtil.format("{}{}{}失败，参数编码已存在", operate.getInfo(), getNodeName(), config.getName()));
            }
        }
    }

    /**
     * 前置校验 删除
     *
     * @param idList Id集合
     */
    @Override
    protected void RHandle(BaseConstants.Operate operate, List<Long> idList) {
        if (operate.isDelete()) {
            int size = idList.size();
            // remove oneself or admin
            for (int i = idList.size() - 1; i >= 0; i--) {
                if (baseService.checkIsBuiltIn(idList.get(i))) {
                    idList.remove(i);
                }
            }
            if (CollUtil.isEmpty(idList)) {
                warn(StrUtil.format("{}失败，不能删除内置参数！", operate.getInfo()));
            } else if (idList.size() != size) {
                baseService.deleteByIds(idList);
                warn(StrUtil.format("成功{}除内置参数外的所有参数！", operate.getInfo()));
            }
        }
    }
}
