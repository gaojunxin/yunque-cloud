package com.yunque.system.dict.controller.admin;

import com.yunque.common.core.context.SecurityContextHolder;
import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.core.web.validate.V_A;
import com.yunque.common.core.web.validate.V_E;
import com.yunque.common.log.annotation.Log;
import com.yunque.common.log.enums.BusinessType;
import com.yunque.common.security.annotation.AdminAuth;
import com.yunque.common.security.utils.SecurityUserUtils;
import com.yunque.system.api.dict.domain.dto.SysConfigDto;
import com.yunque.system.api.dict.domain.query.SysConfigQuery;
import com.yunque.system.dict.controller.base.BSysConfigController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 系统服务 | 字典模块 | 参数管理 | 管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/config")
public class ASysConfigController extends BSysConfigController {

    /**
     * 根据参数类型查询参数数据信息
     */
    @GetMapping(value = "config")
    public AjaxResult listByCode(@RequestParam String code) {
        return getDictByCode(code);
    }

    /**
     * 根据参数类型查询参数数据信息
     */
    @GetMapping(value = "/configs")
    public AjaxResult listByCodeList(@RequestParam List<String> codeList) {
        return getDictListByCodeList(codeList);
    }

    /**
     * 查询参数对象
     *
     * @param code 参数编码
     * @return 参数对象
     */
    @GetMapping("/code/{code}")
    public AjaxResult getConfigByCode(@PathVariable("code") String code) {
        return success(baseService.selectConfigByCode(code));
    }

    /**
     * 查询参数
     *
     * @param code 参数编码
     * @return 参数
     */
    @Override
    @GetMapping("/value/{code}")
    public AjaxResult getValueByCode(@PathVariable("code") String code) {
        return super.getValueByCode(code);
    }

    /**
     * 查询参数列表
     */
    @Override
    @GetMapping("/list")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_CONFIG_LIST)")
    public AjaxResult list(SysConfigQuery query) {
        return super.list(query);
    }

    /**
     * 查询参数详细
     */
    @Override
    @GetMapping(value = "/{id}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_CONFIG_SINGLE)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return super.getInfo(id);
    }

    /**
     * 参数新增
     */
    @Override
    @PostMapping
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_CONFIG_ADD)")
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    public AjaxResult add(@Validated({V_A.class}) @RequestBody SysConfigDto config) {
        return super.add(config);
    }

    /**
     * 参数修改
     */
    @Override
    @PutMapping
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_CONFIG_EDIT)")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody SysConfigDto config) {
        return super.edit(config);
    }

    /**
     * 参数修改状态
     */
    @Override
    @PutMapping("/status")
    @PreAuthorize("@ss.hasAnyAuthority(@Auth.SYS_CONFIG_EDIT, @Auth.SYS_CONFIG_ES)")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE_STATUS)
    public AjaxResult editStatus(@RequestBody SysConfigDto config) {
        return super.editStatus(config);
    }

    /**
     * 参数批量删除
     */
    @Override
    @DeleteMapping("/batch/{idList}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_CONFIG_DEL)")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        return super.batchRemove(idList);
    }

    /**
     * 刷新参数缓存
     */
    @Override
    @GetMapping("/refresh")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_CONFIG_EDIT)")
    @Log(title = "参数管理", businessType = BusinessType.REFRESH)
    public AjaxResult refreshCache() {
        baseService.clearCache();
        return AjaxResult.success();
    }
}
