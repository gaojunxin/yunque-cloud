insert into sys_dict_type (id, name, code, remark, data_type, cache_type, tenant_id)
values (10033, '租户服务|策略模块|源策略|策略类型', 'te_strategy_source_type', '租户服务|策略模块|源策略|策略类型列表（additional_a控制是否必填（Y必填/N非必填））', '3', '1', 0);

-- ----------------------------
-- 初始化 - 字典数据表数据
-- ----------------------------
insert into sys_dict_data (id, sort, label, value, code, additional_a, css_class, list_class, is_default, remark, tenant_id)
values (1003301, 1, '业务源A', 'sourceType1', 'te_strategy_source_type', 'N', '', 'blue', 'N', '租户服务|策略模块|源策略|策略类型：业务源A（非必填）', 0);

alter table te_strategy
    add source_type_info json null comment '策略组类型配置信息' after status;
