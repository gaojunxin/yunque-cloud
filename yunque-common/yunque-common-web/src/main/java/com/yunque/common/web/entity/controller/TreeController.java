package com.yunque.common.web.entity.controller;

import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.utils.TreeUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.web.entity.base.TreeEntity;
import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.core.web.validate.V_A;
import com.yunque.common.core.web.validate.V_E;
import com.yunque.common.web.entity.controller.handle.TreeHandleController;
import com.yunque.common.web.entity.service.ITreeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

/**
 * 操作层 树型通用数据处理
 *
 * @param <Q>   Query
 * @param <D>   Dto
 * @param <IDS> DtoService
 * @author xueyi
 */
public abstract class TreeController<Q extends TreeEntity<D>, D extends TreeEntity<D>, IDS extends ITreeService<Q, D>> extends TreeHandleController<Q, D, IDS> {

    /**
     * 查询树列表
     */
    @Override
    public AjaxResult list(Q query) {
        List<D> list;
        if (ObjectUtil.equals(Boolean.TRUE, query.getExNodes())) {
            Serializable id = query.getId();
            query.initId();
            list = baseService.selectListScope(query);
            SHandleExNodes(list, id);
        } else {
            list = baseService.selectListScope(query);
        }
        if (ObjectUtil.equals(Boolean.TRUE, query.getDefaultNode())) {
            list.add(TopNodeBuilder(query));
        }
        return success(TreeUtil.buildTree(list));
    }

    /**
     * 树型 新增
     * 考虑父节点状态
     */
    @Override
    public AjaxResult add(@Validated({V_A.class}) @RequestBody D dto) {
        dto.initOperate(BaseConstants.Operate.ADD);
        AEHandle(dto.getOperate(), dto);
        AETreeStatusHandle(dto.getOperate(), dto);
        return toAjax(baseService.insert(dto));
    }

    /**
     * 树型 修改
     * 考虑子节点状态
     */
    @Override
    public AjaxResult edit(@Validated({V_E.class}) @RequestBody D dto) {
        dto.initOperate(BaseConstants.Operate.EDIT);
        AEHandle(dto.getOperate(), dto);
        TreeLoopHandle(dto);
        AETreeStatusHandle(dto.getOperate(), dto);
        return toAjax(baseService.update(dto));
    }

    /**
     * 树型 修改状态
     * 考虑子节点状态
     */
    @Override
    public AjaxResult editStatus(@RequestBody D dto) {
        dto.initOperate(BaseConstants.Operate.EDIT_STATUS);
        AETreeStatusHandle(dto.getOperate(), dto);
        return toAjax(baseService.updateStatus(dto));
    }

    /**
     * 树型 批量删除
     * 考虑子节点存在与否
     */
    @Override
    public AjaxResult batchRemove(@PathVariable List<Long> idList) {
        RHandleEmpty(idList);
        RHandle(BaseConstants.Operate.DELETE, idList);
        RHandleEmpty(idList);
        RHandleTreeChild(idList);
        return toAjax(baseService.deleteByIds(idList));
    }
}
