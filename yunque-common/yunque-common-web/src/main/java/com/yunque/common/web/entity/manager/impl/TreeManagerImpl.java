package com.yunque.common.web.entity.manager.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.constant.basic.SqlConstants;
import com.yunque.common.core.utils.core.ArrayUtil;
import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.core.utils.core.NumberUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.web.entity.base.TreeEntity;
import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.common.web.entity.manager.ITreeManager;
import com.yunque.common.web.entity.manager.impl.handle.TreeHandleManagerImpl;
import com.yunque.common.web.entity.mapper.TreeMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yunque.common.core.constant.basic.SqlConstants.ANCESTORS_FIND;
import static com.yunque.common.core.constant.basic.SqlConstants.ANCESTORS_PART_UPDATE;
import static com.yunque.common.core.constant.basic.SqlConstants.Entity;
import static com.yunque.common.core.constant.basic.SqlConstants.NONE_FIND;
import static com.yunque.common.core.constant.basic.SqlConstants.TREE_LEVEL_UPDATE;

/**
 * 数据封装层处理 树型通用数据处理
 *
 * @param <Q>  Query
 * @param <D>  Dto
 * @param <P>  Po
 * @param <PM> PoMapper
 * @author xueyi
 */
public class TreeManagerImpl<Q extends P, D extends P, P extends TreeEntity<D>, PM extends TreeMapper<Q, D, P>, CT extends BaseConverter<Q, D, P>> extends TreeHandleManagerImpl<Q, D, P, PM, CT> implements ITreeManager<Q, D> {

    /**
     * 根据Id查询本节点及其所有祖籍节点
     *
     * @param id Id
     * @return 本节点及其所有祖籍节点数据对象集合
     */
    @Override
    public List<D> selectAncestorsListById(Serializable id) {
        P po = baseMapper.selectById(id);
        if (ObjectUtil.isNull(po) || StrUtil.isBlank(po.getAncestors())) {
            return null;
        }
        List<P> poList = baseMapper.selectList(
                Wrappers.<P>query().lambda()
                        .eq(P::getId, id)
                        .or().in(P::getId, StrUtil.splitTrim(po.getAncestors(), StrUtil.COMMA)));
        return baseConverter.mapperDto(poList);
    }

    /**
     * 根据Id查询节点及子节点
     *
     * @param id Id
     * @return 节点及子节点数据对象集合
     */
    @Override
    public List<D> selectChildListById(Serializable id) {
        P po = baseMapper.selectById(id);
        if (ObjectUtil.isNull(po)) {
            return new ArrayList<>();
        }
        List<P> childList = baseMapper.selectList(
                Wrappers.<P>query().lambda()
                        .eq(P::getId, id)
                        .or().likeRight(P::getAncestors, po.getChildAncestors()));
        return baseConverter.mapperDto(childList);
    }

    /**
     * 根据Id集合查询节点及子节点（批量）
     *
     * @param idList Id集合
     * @return 节点及子节点数据对象集合
     */
    @Override
    public List<D> selectChildListByIds(Collection<? extends Serializable> idList) {
        if (CollUtil.isEmpty(idList)) {
            return new ArrayList<>();
        }
        List<P> poList = baseMapper.selectList(Wrappers.<P>query().lambda()
                .in(P::getId, idList));
        if (CollUtil.isEmpty(poList)) {
            return new ArrayList<>();
        }
        List<P> childList = baseMapper.selectList(
                Wrappers.<P>query().lambda()
                        .in(P::getId, idList)
                        .func(i -> poList.forEach(item -> i.or().likeRight(P::getAncestors, item.getChildAncestors()))));
        return baseConverter.mapperDto(childList);
    }

    /**
     * 修改其子节点的祖籍
     *
     * @param dto 数据对象
     * @return 结果
     */
    @Override
    public int updateChildrenAncestors(D dto) {
        String newAncestors = dto.getChildAncestors();
        String oldAncestors = dto.getOldChildAncestors();
        if (StrUtil.equals(newAncestors, oldAncestors)) {
            return NumberUtil.Zero;
        }
        LambdaUpdateWrapper<P> updateWrapper = updateChildrenWrapper(dto);
        updateWrapper.setSql(StrUtil.format(ANCESTORS_PART_UPDATE, Entity.ANCESTORS.getCode(), Entity.ANCESTORS.getCode(), NumberUtil.One, oldAncestors.length(), newAncestors))
                .setSql(StrUtil.format(TREE_LEVEL_UPDATE, Entity.LEVEL.getCode(), Entity.LEVEL.getCode(), dto.getLevelChange()))
                .likeRight(P::getAncestors, oldAncestors);
        return baseMapper.update(updateWrapper);
    }

    /**
     * 修改子节点的祖籍和状态
     *
     * @param dto 数据对象
     * @return 结果
     */
    @Override
    public int updateChildren(D dto) {
        String newAncestors = dto.getChildAncestors();
        String oldAncestors = dto.getOldChildAncestors();
        LambdaUpdateWrapper<P> updateWrapper = updateChildrenWrapper(dto);
        updateWrapper.set(P::getStatus, dto.getStatus())
                .func(i -> {
                    if (StrUtil.notEquals(newAncestors, oldAncestors)) {
                        i.setSql(StrUtil.format(ANCESTORS_PART_UPDATE, Entity.ANCESTORS.getCode(), Entity.ANCESTORS.getCode(), NumberUtil.One, oldAncestors.length(), newAncestors))
                                .setSql(StrUtil.format(TREE_LEVEL_UPDATE, Entity.LEVEL.getCode(), Entity.LEVEL.getCode(), dto.getLevelChange()));
                    }
                })
                .likeRight(P::getAncestors, oldAncestors);
        return baseMapper.update(updateWrapper);
    }

    /**
     * 根据Id删除其子节点
     *
     * @param id Id
     * @return 结果
     */
    @Override
    public int deleteChildrenById(Serializable id) {
        return baseMapper.delete(
                Wrappers.<P>lambdaUpdate()
                        .eq(P::getId, id)
                        .apply(ANCESTORS_FIND, id));
    }

    /**
     * 根据祖籍删除对应子节点
     *
     * @param ancestors 祖籍
     * @return 结果
     */
    @Override
    public int deleteChildByAncestors(String... ancestors) {
        if (ArrayUtil.isEmpty(ancestors)) {
            return NumberUtil.Zero;
        }
        return baseMapper.delete(
                Wrappers.<P>lambdaUpdate()
                        .apply(NONE_FIND)
                        .func(i -> {
                            for (String ancestor : ancestors) {
                                i.or().likeRight(P::getAncestors, ancestor);
                            }
                        }));
    }

    /**
     * 校验是否为父级的子级
     *
     * @param id       Id
     * @param parentId 父级Id
     * @return 结果 | true/false 是/否
     */
    @Override
    public D checkIsChild(Serializable id, Serializable parentId) {
        P po = baseMapper.selectOne(
                Wrappers.<P>query().lambda()
                        .eq(P::getId, id)
                        .apply(ANCESTORS_FIND, parentId)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(po);
    }

    /**
     * 校验是否存在子节点
     *
     * @param id Id
     * @return 结果 | true/false 有/无
     */
    @Override
    public D checkHasChild(Serializable id) {
        P po = baseMapper.selectOne(
                Wrappers.<P>query().lambda()
                        .apply(ANCESTORS_FIND, id)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(po);
    }

    /**
     * 校验是否有启用(正常状态)的子节点
     *
     * @param id Id
     * @return 结果 | true/false 有/无
     */
    @Override
    public D checkHasNormalChild(Serializable id) {
        P po = baseMapper.selectOne(
                Wrappers.<P>query().lambda()
                        .eq(P::getStatus, BaseConstants.Status.NORMAL.getCode())
                        .apply(ANCESTORS_FIND, id)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(po);
    }

    /**
     * 校验名称是否唯一
     *
     * @param id       Id
     * @param parentId 父级Id
     * @param name     名称
     * @return 数据对象
     */
    @Override
    public D checkNameUnique(Serializable id, Serializable parentId, String name) {
        P po = baseMapper.selectOne(
                Wrappers.<P>query().lambda()
                        .ne(P::getId, id)
                        .eq(P::getParentId, parentId)
                        .eq(P::getName, name)
                        .last(SqlConstants.LIMIT_ONE));
        return baseConverter.mapperDto(po);
    }
}
