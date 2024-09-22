package com.yunque.common.web.correlate.service;

import com.yunque.common.web.correlate.domain.BaseCorrelate;

import java.util.List;

/**
 * 关联映射 枚举映射器
 */
public interface CorrelateService {

    List<? extends BaseCorrelate<?>> getCorrelates();
}
