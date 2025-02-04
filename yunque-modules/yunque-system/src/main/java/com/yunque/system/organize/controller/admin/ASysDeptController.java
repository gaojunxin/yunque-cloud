package com.yunque.system.organize.controller.admin;

import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.core.web.validate.V_A;
import com.yunque.common.core.web.validate.V_E;
import com.yunque.common.log.annotation.Log;
import com.yunque.common.log.enums.BusinessType;
import com.yunque.common.security.annotation.AdminAuth;
import com.yunque.system.api.organize.domain.dto.SysDeptDto;
import com.yunque.system.api.organize.domain.query.SysDeptQuery;
import com.yunque.system.organize.controller.base.BSysDeptController;
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
 * 系统服务 | 组织模块 | 部门管理 | 管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/dept")
public class ASysDeptController extends BSysDeptController {

    /**
     * 查询部门列表
     */
    @Override
    @GetMapping("/list")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_DEPT_LIST)")
    public AjaxResult list(SysDeptQuery dept) {
        return super.list(dept);
    }

    /**
     * 查询部门详细
     */
    @Override
    @GetMapping(value = "/{id}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_DEPT_SINGLE)")
    public AjaxResult getInfo(@PathVariable Serializable id) {
        return super.getInfo(id);
    }

    /**
     * 查询部门关联的角色Id集
     */
    @GetMapping(value = "/auth")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_DEPT_AUTH)")
    public AjaxResult getRoleAuth(@RequestParam Long id) {
        return success(baseService.selectDeptRoleById(id));
    }

    /**
     * 部门新增
     */
    @Override
    @PostMapping
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_DEPT_ADD)")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    public AjaxResult add(@Validated({V_A.class}) @RequestBody SysDeptDto dept) {
        return super.add(dept);
    }

    /**
     * 部门修改
     */
    @Override
    @PutMapping
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_DEPT_EDIT)")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody SysDeptDto dept) {
        return super.edit(dept);
    }

    /**
     * 查询部门关联的角色Id集
     */
    @PutMapping(value = "/auth")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_DEPT_AUTH)")
    public AjaxResult editRoleAuth(@RequestBody SysDeptDto dept) {
        baseService.editDeptRole(dept);
        return success();
    }

    /**
     * 部门修改状态
     */
    @Override
    @PutMapping("/status")
    @PreAuthorize("@ss.hasAnyAuthority(@Auth.SYS_DEPT_EDIT, @Auth.SYS_DEPT_ES)")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE_STATUS)
    public AjaxResult editStatus(@RequestBody SysDeptDto dept) {
        return super.editStatus(dept);
    }

    /**
     * 部门批量删除
     */
    @Override
    @DeleteMapping("/batch/{idList}")
    @PreAuthorize("@ss.hasAuthority(@Auth.SYS_DEPT_DEL)")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        return super.batchRemove(idList);
    }

}
