package com.yunque.common.web.correlate.handle;

import com.yunque.common.core.constant.basic.SqlConstants;
import com.yunque.common.core.utils.core.ArrayUtil;
import com.yunque.common.core.utils.core.ClassUtil;
import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.core.utils.core.NumberUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.SpringUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.web.entity.base.BaseEntity;
import com.yunque.common.core.web.entity.base.BasisEntity;
import com.yunque.common.web.correlate.contant.CorrelateConstants;
import com.yunque.common.web.correlate.domain.Indirect;
import com.yunque.common.web.correlate.domain.SqlField;
import com.yunque.common.web.correlate.utils.CorrelateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 主从关联数据处理 | 间接关联
 *
 * @author xueyi
 */
@Slf4j
@SuppressWarnings({"unchecked"})
public final class CorrelateIndirectHandle extends CorrelateBaseHandle {

    /**
     * 组装数据对象关联数据 | 间接关联
     *
     * @param dto      数据对象
     * @param indirect 间接关联映射对象
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity> void assembleIndirectObj(D dto, Indirect<D, M, S> indirect) {
        assembleIndirectBuild(dto, null, indirect);
    }

    /**
     * 组装集合关联数据 | 间接关联
     *
     * @param dtoList  数据对象集合
     * @param indirect 间接关联映射对象
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity, Coll extends Collection<D>> void assembleIndirectList(Coll dtoList, Indirect<D, M, S> indirect) {
        assembleIndirectBuild(null, dtoList, indirect);
    }

    /**
     * 新增关联数据 | 间接关联
     *
     * @param dto      数据对象
     * @param indirect 间接关联映射对象
     * @return 结果
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity> int insertIndirectObj(D dto, Indirect<D, M, S> indirect) {
        Indirect.ORM ormIndirect = indirect.getOrm();
        Collection<M> mergeList = insertIndirectBuild(dto, ormIndirect);
        if (CollUtil.isEmpty(mergeList))
            return NumberUtil.Zero;
        return SpringUtil.getBean(ormIndirect.getMergeMapper()).insertBatch(mergeList);
    }

    /**
     * 新增关联数据 | 间接关联
     *
     * @param dtoList 数据对象集合
     * @return 结果
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity, Coll extends Collection<D>> int insertIndirectList(Coll dtoList, Indirect<D, M, S> indirect) {
        Indirect.ORM ormIndirect = indirect.getOrm();
        List<M> subList = dtoList.stream().map(dto -> (List<M>) insertIndirectBuild(dto, ormIndirect)).filter(CollUtil::isNotEmpty).flatMap(Collection::stream).toList();
        if (CollUtil.isEmpty(subList))
            return NumberUtil.Zero;
        return SpringUtil.getBean(ormIndirect.getMergeMapper()).insertBatch(subList);
    }

    /**
     * 修改关联数据 | 间接关联
     *
     * @param newDto   新数据对象
     * @param indirect 间接关联映射对象
     * @return 结果
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity> int updateIndirectObj(D newDto, Indirect<D, M, S> indirect) {
        Indirect.ORM ormIndirect = indirect.getOrm();
        List<M> insertList = new ArrayList<>();
        // 1.组装操作数据
        updateIndirectBuild(null, newDto, ormIndirect, insertList, null);
        // 2.处理并返回操作结果
        return refreshIndirectList(insertList, null, newDto, null, ormIndirect);
    }

    /**
     * 修改关联数据 | 间接关联
     *
     * @param originDto 源数据对象
     * @param newDto    新数据对象
     * @param indirect  间接关联映射对象
     * @return 结果
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity> int updateIndirectObj(D originDto, D newDto, Indirect<D, M, S> indirect) {
        Indirect.ORM ormIndirect = indirect.getOrm();
        List<M> insertList = new ArrayList<>();
        Set<Object> delKeys = new HashSet<>();
        // 1.组装操作数据
        updateIndirectBuild(originDto, newDto, ormIndirect, insertList, delKeys);
        // 2.处理并返回操作结果
        return refreshIndirectList(insertList, delKeys, newDto, null, ormIndirect);
    }

    /**
     * 修改关联数据 | 间接关联
     *
     * @param newList  新数据对象集合
     * @param indirect 间接关联映射对象
     * @return 结果
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity, Coll extends Collection<D>> int updateIndirectList(Coll newList, Indirect<D, M, S> indirect) {
        Indirect.ORM ormIndirect = indirect.getOrm();
        List<M> insertList = new ArrayList<>();
        // 1.组装操作数据
        if (CollUtil.isNotEmpty(newList)) {
            newList.forEach(newDto -> updateIndirectBuild(null, newDto, ormIndirect, insertList, null));
        }
        // 2.处理并返回操作结果
        return refreshIndirectList(insertList, null, null, newList, ormIndirect);
    }

    /**
     * 修改关联数据 | 间接关联
     *
     * @param originList 源数据对象集合
     * @param newList    新数据对象集合
     * @param indirect   间接关联映射对象
     * @return 结果
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity, Coll extends Collection<D>> int updateIndirectList(Coll originList, Coll newList, Indirect<D, M, S> indirect) {
        Indirect.ORM ormIndirect = indirect.getOrm();
        List<M> insertList = new ArrayList<>();
        Set<Object> delKeys = new HashSet<>();
        // 1.组装操作数据
        Map<Object, D> originMap = CollUtil.isEmpty(originList)
                ? new HashMap<>()
                : originList.stream().collect(
                Collectors.toMap(D::getId, Function.identity()));
        if (CollUtil.isNotEmpty(newList)) {
            newList.forEach(newDto -> {
                D originDto = getMapObj(originMap, newDto.getId());
                updateIndirectBuild(originDto, newDto, ormIndirect, insertList, delKeys);
            });
        }
        // 2.处理并返回操作结果
        return refreshIndirectList(insertList, delKeys, null, newList, ormIndirect);
    }

    /**
     * 删除关联数据 | 间接关联
     *
     * @param dto      数据对象
     * @param indirect 间接关联映射对象
     * @return 结果
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity> int deleteIndirectObj(D dto, Indirect<D, M, S> indirect) {
        Indirect.ORM ormIndirect = indirect.getOrm();
        Object delKey = getFieldObj(dto, ormIndirect.getMainKeyField());
        SqlField sqlField = new SqlField(SqlConstants.OperateType.EQ, ormIndirect.getMergeMainSqlName(), delKey);
        List<M> mergeList = (List<M>) SpringUtil.getBean(ormIndirect.getMergeMapper()).selectListByField(sqlField);
        if (CollUtil.isNotEmpty(mergeList)) {
            Set<Object> mergeIds = mergeList.stream().map(M::getId).collect(Collectors.toSet());
            return SpringUtil.getBean(ormIndirect.getMergeMapper()).deleteBatchIds(mergeIds);
        }
        return NumberUtil.Zero;
    }

    /**
     * 删除关联数据 | 间接关联
     *
     * @param dtoList  数据对象集合
     * @param indirect 间接关联映射对象
     * @return 结果
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity, Coll extends Collection<D>> int deleteIndirectList(Coll dtoList, Indirect<D, M, S> indirect) {
        Indirect.ORM ormIndirect = indirect.getOrm();
        Set<Object> delKeys = dtoList.stream().map(item -> getFieldObj(item, ormIndirect.getMainKeyField())).collect(Collectors.toSet());
        SqlField sqlField = new SqlField(SqlConstants.OperateType.IN, ormIndirect.getMergeMainSqlName(), delKeys);
        List<M> mergeList = (List<M>) SpringUtil.getBean(ormIndirect.getMergeMapper()).selectListByField(sqlField);
        if (CollUtil.isNotEmpty(mergeList)) {
            Set<Object> mergeIds = mergeList.stream().map(M::getId).collect(Collectors.toSet());
            return SpringUtil.getBean(ormIndirect.getMergeMapper()).deleteBatchIds(mergeIds);
        }
        return NumberUtil.Zero;
    }

    /**
     * 查询关联数据 | 数据组装 | 间接关联
     *
     * @param dto      数据对象
     * @param dtoList  数据对象集合
     * @param indirect 间接关联映射对象
     */
    private static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity, Coll extends Collection<D>> void assembleIndirectBuild(D dto, Coll dtoList, Indirect<D, M, S> indirect) {
        Indirect.ORM ormIndirect = indirect.getOrm();
        Set<Object> findInSet = ObjectUtil.isNotNull(dto)
                ? getFieldKeys(dto, ormIndirect, ormIndirect.getMainKeyField())
                : getFieldKeys(dtoList, ormIndirect, ormIndirect.getMainKeyField());
        if (CollUtil.isEmpty(findInSet)) {
            return;
        }
        SqlField mergeSqlField = new SqlField(SqlConstants.OperateType.IN, ormIndirect.getMergeMainSqlName(), findInSet);
        List<M> mergeList = (List<M>) SpringUtil.getBean(ormIndirect.getMergeMapper()).selectListByField(mergeSqlField);
        CorrelateConstants.MergeType mergeType = CorrelateConstants.MergeType.DIRECT;
        if (ObjectUtil.isNotNull(dto)) {
            setSubField(dto, mergeList, ormIndirect.getSubDataRow(), mergeType, ormIndirect.getMainKeyField(), ormIndirect.getMergeMainField(), ormIndirect.getSubKeyField(), ormIndirect.getMergeSlaveField());
        } else if (CollUtil.isNotEmpty(dtoList)) {
            setSubField(dtoList, mergeList, ormIndirect.getSubDataRow(), mergeType, ormIndirect.getMainKeyField(), ormIndirect.getMergeMainField(), ormIndirect.getSubKeyField(), ormIndirect.getMergeSlaveField());
        }

        // select sub relation list
        if (ObjectUtil.isNotNull(ormIndirect.getSlaveService())) {
            Set<Object> subFindInSet = getFieldKeys(mergeList, ormIndirect, ormIndirect.getMergeSlaveField());
            if (CollUtil.isEmpty(subFindInSet)) {
                return;
            }
            SqlField subSqlField = new SqlField(SqlConstants.OperateType.IN, ormIndirect.getSlaveKeySqlName(), subFindInSet);
            // 子查询进行数据关联操作
            startCorrelates(indirect.getRelations());
            List<S> subList = (List<S>) SpringUtil.getBean(ormIndirect.getSlaveService()).selectListByField(subSqlField);

            CorrelateConstants.MergeType subType = ObjectUtil.equals(CorrelateConstants.DataRow.SINGLE.getCode(), ormIndirect.getSubDataRow()) ? CorrelateConstants.MergeType.DIRECT : CorrelateConstants.MergeType.INDIRECT;
            if (ObjectUtil.isNotNull(dto)) {
                setSubField(dto, subList, ormIndirect.getSubDataRow(), subType, ormIndirect.getSubKeyField(), ormIndirect.getSlaveKeyField(), ormIndirect.getSubInfoField());
            } else if (CollUtil.isNotEmpty(dtoList)) {
                setSubField(dtoList, subList, ormIndirect.getSubDataRow(), subType, ormIndirect.getSubKeyField(), ormIndirect.getSlaveKeyField(), ormIndirect.getSubInfoField());
            }
        }
    }

    /**
     * 新增关联数据 | 数据组装 | 间接关联
     *
     * @param dto         数据对象
     * @param ormIndirect 间接关联数据映射对象
     * @return 数据对象集合
     */
    private static <D extends BaseEntity, M extends BasisEntity> List<M> insertIndirectBuild(D dto, Indirect.ORM ormIndirect) {
        Object mainKey = getFieldObj(dto, ormIndirect.getMainKeyField());
        Object slaveKeyObj = getFieldObj(dto, ormIndirect.getSubKeyField());

        List<M> mergeList = new ArrayList<>();
        switch (ormIndirect.getSubDataRow()) {
            case SINGLE -> {
                M mergePo = (M) createObj(ormIndirect.getMergeInfoClazz());
                setField(mergePo, ormIndirect.getMergeMainField(), mainKey);
                setField(mergePo, ormIndirect.getMergeSlaveField(), slaveKeyObj);
                mergePo.setSort(NumberUtil.Zero);
                mergeList.add(mergePo);
            }
            case LIST -> {
                AtomicInteger index = new AtomicInteger(NumberUtil.Zero);
                List<Object> slaveKeyList = ormIndirect.getIsArray()
                        ? ArrayUtil.isNotEmpty((Object[]) slaveKeyObj) ? Arrays.stream(((Object[]) slaveKeyObj)).toList() : new ArrayList<>()
                        : CollUtil.isNotEmpty((Collection<Object>) slaveKeyObj) ? ((Collection<Object>) slaveKeyObj).stream().toList() : new ArrayList<>();
                List<M> addList = slaveKeyList.stream().map(slaveKey -> {
                    M mergePo = (M) createObj(ormIndirect.getMergeInfoClazz());
                    setField(mergePo, ormIndirect.getMergeMainField(), mainKey);
                    setField(mergePo, ormIndirect.getMergeSlaveField(), slaveKey);
                    mergePo.setSort(index.getAndIncrement());
                    return mergePo;
                }).toList();
                mergeList.addAll(addList);
            }
        }
        return mergeList;
    }

    /**
     * 修改关联数据 | 数据组装 | 间接关联
     *
     * @param originDto   源数据对象
     * @param newDto      新数据对象
     * @param ormIndirect 间接关联数据映射对象
     * @param insertList  待新增数据对象集合
     * @param delKeys     待删除键值集合
     */
    private static <D extends BaseEntity, M extends BasisEntity> void updateIndirectBuild(D originDto, D newDto, Indirect.ORM ormIndirect, List<M> insertList, Set<Object> delKeys) {
        Set<Object> insertSet;
        if (ObjectUtil.isNotNull(originDto)) {
            Set<Object> originSet = new HashSet<>();
            Set<Object> newSet = new HashSet<>();
            Object originKey = getFieldObj(originDto, ormIndirect.getSubKeyField());
            Object newKey = getFieldObj(newDto, ormIndirect.getSubKeyField());
            switch (ormIndirect.getSubDataRow()) {
                case SINGLE -> {
                    if (ObjectUtil.isNotNull(originKey)) {
                        originSet.add(originKey);
                    }
                    if (ObjectUtil.isNotNull(newKey)) {
                        newSet.add(newKey);
                    }
                }
                case LIST -> {
                    if (ormIndirect.getIsArray()) {
                        Object[] originKeys = (Object[]) originKey;
                        if (ArrayUtil.isNotEmpty(originKeys)) {
                            originSet.addAll(Arrays.stream(originKeys).collect(Collectors.toSet()));
                        }
                        Object[] newKeys = (Object[]) newKey;
                        if (ArrayUtil.isNotEmpty(newKeys)) {
                            newSet.addAll(Arrays.stream(newKeys).collect(Collectors.toSet()));
                        }
                    } else {
                        Collection<Object> originKeys = (Collection<Object>) originKey;
                        if (CollUtil.isNotEmpty(originKeys)) {
                            originSet.addAll(originKeys);
                        }
                        Collection<Object> newKeys = (Collection<Object>) newKey;
                        if (CollUtil.isNotEmpty(newKeys)) {
                            newSet.addAll(newKeys);
                        }
                    }
                }
            }

            // 1.获取待删除从数据键值
            Set<Object> delSet = new HashSet<>(originSet);
            delSet.removeAll(newSet);
            if (CollUtil.isNotEmpty(delSet)) {
                delKeys.addAll(delSet);
            }
            // 2.获取待新增从数据键值
            insertSet = new HashSet<>(newSet);
            insertSet.removeAll(originSet);
        } else {
            insertSet = new HashSet<>();
            Object newKey = getFieldObj(newDto, ormIndirect.getSubKeyField());
            switch (ormIndirect.getSubDataRow()) {
                case SINGLE -> {
                    if (ObjectUtil.isNotNull(newKey)) {
                        insertSet.add(newKey);
                    }
                }
                case LIST -> {
                    if (ormIndirect.getIsArray()) {
                        Object[] newKeys = (Object[]) newKey;
                        if (ArrayUtil.isNotEmpty(newKeys)) {
                            insertSet.addAll(Arrays.stream(newKeys).collect(Collectors.toSet()));
                        }
                    } else {
                        Collection<Object> newKeys = (Collection<Object>) newKey;
                        if (CollUtil.isNotEmpty(newKeys)) {
                            insertSet.addAll(newKeys);
                        }
                    }
                }
            }
        }
        // 3.组装待新增从数据对象
        if (CollUtil.isNotEmpty(insertSet)) {
            Object mainKey = getFieldObj(newDto, ormIndirect.getMainKeyField());
            AtomicInteger index = new AtomicInteger(NumberUtil.Zero);
            List<M> addList = insertSet.stream().filter(ObjectUtil::isNotNull).map(slaveKey -> {
                M mergePo = (M) createObj(ormIndirect.getMergeInfoClazz());
                setField(mergePo, ormIndirect.getMergeSlaveField(), slaveKey);
                setField(mergePo, ormIndirect.getMergeMainField(), mainKey);
                mergePo.setSort(index.getAndIncrement());
                return mergePo;
            }).toList();
            if (CollUtil.isNotEmpty(addList)) {
                insertList.addAll(addList);
            }
        }
    }

    /**
     * 修改关联数据 | 数据更新 | 间接关联
     *
     * @param insertList  待更新数据集合
     * @param delKeys     待删除数据集合
     * @param newDto      新数据对象
     * @param newList     新数据集合
     * @param ormIndirect 间接关联映射ORM对象
     * @return 结果
     */
    private static <D extends BaseEntity, M extends BasisEntity, Coll extends Collection<D>> int refreshIndirectList(List<M> insertList, Set<Object> delKeys, D newDto, Coll newList, Indirect.ORM ormIndirect) {
        int rows = NumberUtil.Zero;
        // 判别需要全量先删 or 指定删除
        if (ObjectUtil.isNull(delKeys) || CollUtil.isNotEmpty(delKeys)) {
            SqlField sqlMainField;
            if (ObjectUtil.isNotNull(newDto)) {
                Object delMainKey = getFieldObj(newDto, ormIndirect.getMainKeyField());
                sqlMainField = new SqlField(SqlConstants.OperateType.EQ, ormIndirect.getMergeMainSqlName(), delMainKey);
            } else {
                Set<Object> delMainKeys = newList.stream().map(item -> getFieldObj(item, ormIndirect.getMainKeyField())).collect(Collectors.toSet());
                sqlMainField = new SqlField(SqlConstants.OperateType.IN, ormIndirect.getMergeMainSqlName(), delMainKeys);
            }
            List<M> mergeList;
            // 1.判断删除为指定删除 or 全量删除
            if (CollUtil.isNotEmpty(delKeys)) {
                SqlField sqlArrField = new SqlField(SqlConstants.OperateType.IN, ormIndirect.getMergeSlaveSqlName(), delKeys);
                mergeList = (List<M>) SpringUtil.getBean(ormIndirect.getMergeMapper()).selectListByField(sqlMainField, sqlArrField);
            } else {
                mergeList = (List<M>) SpringUtil.getBean(ormIndirect.getMergeMapper()).selectListByField(sqlMainField);
            }
            if (CollUtil.isNotEmpty(mergeList)) {
                Set<Object> mergeIds = mergeList.stream().map(BasisEntity::getId).collect(Collectors.toSet());
                rows += SpringUtil.getBean(ormIndirect.getMergeMapper()).deleteBatchIds(mergeIds);
            }
        }
        // 2.判断是否执行新增
        if (CollUtil.isNotEmpty(insertList)) {
            rows += SpringUtil.getBean(ormIndirect.getMergeMapper()).insertBatch(insertList);
        }
        // 3.返回操作结果
        return rows;
    }

    /**
     * 校验关联映射是否合规
     *
     * @param indirect 间接关联映射对象
     */
    public static <D extends BaseEntity, M extends BasisEntity, S extends BaseEntity> void checkORMLegal(Indirect<D, M, S> indirect) {
        Indirect.ORM ormIndirect = indirect.getOrm();

        if (ObjectUtil.isNull(ormIndirect.getSlaveService())) {
            switch (indirect.getOperateType()) {
                case SELECT, ADD, EDIT -> {
                    if (ObjectUtil.isNull(ormIndirect.getSubKeyField())) {
                        logReturn(StrUtil.format("groupName: {}, slaveService can not be null", indirect.getGroupName()));
                    }
                }
                case DELETE -> {
                }
            }
        } else {
            if (ObjectUtil.isNull(ormIndirect.getMergeSlaveField()) || ClassUtil.isNotSimpleType(ormIndirect.getMergeSlaveField().getType())) {
                logReturn(StrUtil.format("groupName: {}, mergeSlaveField can not be null or not BasicType", indirect.getGroupName()));
            } else if (ObjectUtil.isNull(ormIndirect.getSubKeyField())) {
                logReturn(StrUtil.format("groupName: {}, subKeyField can not be null", indirect.getGroupName()));
            } else if (ObjectUtil.isNull(ormIndirect.getSubInfoField())) {
                logReturn(StrUtil.format("groupName: {}, subInfoField can not be null", indirect.getGroupName()));
            }
        }

        if (ObjectUtil.isNull(ormIndirect.getMergeMapper())) {
            logReturn(StrUtil.format("groupName: {}, mergeMapper can not be null", indirect.getGroupName()));
        } else if (ObjectUtil.isNull(ormIndirect.getMergeMainField()) || ClassUtil.isNotSimpleType(ormIndirect.getMergeMainField().getType())) {
            logReturn(StrUtil.format("groupName: {}, mergeMainField can not be null or not BasicType", indirect.getGroupName()));
        } else if (ClassUtil.isNotSimpleType(ormIndirect.getMainKeyField().getType())) {
            logReturn(StrUtil.format("groupName: {}, mainKeyField must be basicType", indirect.getGroupName()));
        }

        // 数据初始化
        if (ObjectUtil.isNotNull(ormIndirect.getMergeMainField())) {
            ormIndirect.setMergeMainSqlName(CorrelateUtil.getFieldSqlName(ormIndirect.getMergeMainField()));
        }
        if (ObjectUtil.isNotNull(ormIndirect.getMergeSlaveField())) {
            ormIndirect.setMergeSlaveSqlName(CorrelateUtil.getFieldSqlName(ormIndirect.getMergeSlaveField()));
        }

        // 从数据关联校验
        if (ObjectUtil.isNotNull(ormIndirect.getSubKeyField())) {
            Class<?> subKeyClazz = ormIndirect.getSubKeyField().getType();
            CorrelateConstants.DataRow subKey = null;
            if (ClassUtil.isSimpleType(subKeyClazz)) {
                subKey = CorrelateConstants.DataRow.SINGLE;
                ormIndirect.setIsArray(Boolean.FALSE);
            } else if (ClassUtil.isCollection(subKeyClazz) || ClassUtil.isArray(subKeyClazz)) {
                ormIndirect.setIsArray(ClassUtil.isArray(subKeyClazz));
                subKey = CorrelateConstants.DataRow.LIST;
            } else {
                logReturn(StrUtil.format("groupName: {}, subKeyField class not in compliance with regulations, must be Array or Collection or Primitive", indirect.getGroupName()));
            }
            if (ObjectUtil.isNotNull(ormIndirect.getSubDataRow())) {
                if (ObjectUtil.notEqual(ormIndirect.getSubDataRow(), subKey)) {
                    logReturn(StrUtil.format("groupName: {}, subKeyField and subInfoField dataRow not equal", indirect.getGroupName()));
                }
            } else {
                ormIndirect.setSubDataRow(subKey);
            }
        }
    }
}
