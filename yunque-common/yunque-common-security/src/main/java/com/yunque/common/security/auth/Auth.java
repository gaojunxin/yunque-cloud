package com.yunque.common.security.auth;

import com.yunque.common.security.auth.pool.GenPool;
import com.yunque.common.security.auth.pool.JobPool;
import com.yunque.common.security.auth.pool.SystemPool;
import com.yunque.common.security.auth.pool.TenantPool;

/**
 * 权限标识常量
 *
 * @author xueyi
 */
public class Auth implements SystemPool, JobPool, GenPool, TenantPool {
}
