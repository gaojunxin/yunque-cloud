package com.yunque.system.organize.controller.inner;

import com.yunque.common.core.web.result.R;
import com.yunque.common.security.annotation.InnerAuth;
import com.yunque.system.api.organize.domain.dto.SysPostDto;
import com.yunque.system.organize.controller.base.BSysPostController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务 | 组织模块 | 岗位管理 | 内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/post")
public class ISysPostController extends BSysPostController {

    /**
     * 新增岗位 | 内部调用
     */
    @PostMapping("/add")
    public R<SysPostDto> addInner(@RequestBody SysPostDto post) {
        return baseService.addInner(post) > 0 ? R.ok(post) : R.fail();
    }
}
