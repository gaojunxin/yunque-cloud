package com.yunque.system.api.authority.feign.factory;

import com.yunque.common.core.web.result.R;
import com.yunque.system.api.authority.domain.dto.SysMenuDto;
import com.yunque.system.api.authority.feign.RemoteMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 系统服务 | 权限模块 | 菜单服务 降级处理
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteMenuFallbackFactory implements FallbackFactory<RemoteMenuService> {

    @Override
    public RemoteMenuService create(Throwable throwable) {
        log.error("菜单服务调用失败:{}", throwable.getMessage());
        return new RemoteMenuService() {
            @Override
            public R<SysMenuDto> getInfoInner(Long id) {
                return R.fail("获取菜单信息失败:" + throwable.getMessage());
            }
        };
    }
}