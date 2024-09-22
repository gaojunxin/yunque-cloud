package com.yunque.common.security.auth.pool;

import com.yunque.common.security.auth.pool.system.SysAuthorityPool;
import com.yunque.common.security.auth.pool.system.SysDictPool;
import com.yunque.common.security.auth.pool.system.SysFilePool;
import com.yunque.common.security.auth.pool.system.SysMonitorPool;
import com.yunque.common.security.auth.pool.system.SysNoticePool;
import com.yunque.common.security.auth.pool.system.SysOrganizePool;

/**
 * 系统服务 权限标识常量
 *
 * @author xueyi
 */
public interface SystemPool extends SysAuthorityPool, SysDictPool, SysFilePool, SysMonitorPool, SysNoticePool, SysOrganizePool {
}
