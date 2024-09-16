package com.xueyi.common.web.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.xueyi.common.core.utils.core.IdUtil;

/**
 * Mybatis-plus自定义ID生成器
 *
 * @author xueyi
 */
public class XueYiCustomIdGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        return IdUtil.getSnowflakeNextId();
    }
}
