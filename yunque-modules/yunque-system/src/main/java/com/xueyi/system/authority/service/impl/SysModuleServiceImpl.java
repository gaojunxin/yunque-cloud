package com.xueyi.system.authority.service.impl;

import com.xueyi.common.core.constant.basic.OperateConstants;
import com.xueyi.common.core.constant.basic.TenantConstants;
import com.xueyi.common.core.context.SecurityContextHolder;
import com.xueyi.common.core.exception.ServiceException;
import com.xueyi.common.core.utils.core.CollUtil;
import com.xueyi.common.core.utils.core.ObjectUtil;
import com.xueyi.common.core.utils.core.StrUtil;
import com.xueyi.common.security.utils.SecurityUserUtils;
import com.xueyi.common.web.correlate.contant.CorrelateConstants;
import com.xueyi.common.web.entity.service.impl.BaseServiceImpl;
import com.xueyi.system.api.authority.domain.dto.SysModuleDto;
import com.xueyi.system.api.authority.domain.query.SysModuleQuery;
import com.xueyi.system.authority.domain.correlate.SysModuleCorrelate;
import com.xueyi.system.authority.manager.ISysModuleManager;
import com.xueyi.system.authority.service.ISysModuleService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统服务 | 权限模块 | 模块管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysModuleServiceImpl extends BaseServiceImpl<SysModuleQuery, SysModuleDto, SysModuleCorrelate, ISysModuleManager> implements ISysModuleService {

    /**
     * 默认方法关联配置定义
     */
    @Override
    protected Map<CorrelateConstants.ServiceType, SysModuleCorrelate> defaultCorrelate() {
        return new HashMap<>() {{
            put(CorrelateConstants.ServiceType.DELETE, SysModuleCorrelate.BASE_DEL);
        }};
    }

    /**
     * 查询模块对象列表 | 数据权限 | 附加数据
     *
     * @param module 模块对象
     * @return 模块对象集合
     */
    @Override
//    @DataScope(userAlias = CREATE_BY, mapperScope = {"SysModuleMapper"})
    public List<SysModuleDto> selectListScope(SysModuleQuery module) {
        return super.selectListScope(module);
    }

    /**
     * 根据Id查询模块信息
     *
     * @param id Id
     * @return 模块数据对象
     */
    @Override
    public SysModuleDto selectInfoById(Serializable id) {
        return selectById(id);
    }

    /**
     * 单条操作 - 开始处理
     *
     * @param operate 服务层 - 操作类型
     * @param newDto  新数据对象（删除时不存在）
     * @param id      Id集合（非删除时不存在）
     */
    @Override
    protected SysModuleDto startHandle(OperateConstants.ServiceType operate, SysModuleDto newDto, Serializable id) {
        SysModuleDto originDto = super.startHandle(operate, newDto, id);
        switch (operate) {
            case EDIT -> {
                if (ObjectUtil.isNull(originDto)) {
                    throw new ServiceException("数据不存在！");
                } else if (ObjectUtil.notEqual(originDto.getIsCommon(), newDto.getIsCommon())) {
                    throw new ServiceException(StrUtil.format("{}模块{}失败，不允许变更公共类型！", operate.getInfo(), newDto.getName()));
                }
                newDto.setIsCommon(originDto.getIsCommon());
            }
            case EDIT_STATUS -> {
                newDto.setIsCommon(originDto.getIsCommon());
            }
            case DELETE -> {
                if ((originDto.isCommon() )) {
                    throw new ServiceException("无操作权限，公共模块不允许删除！");
                }
            }
        }
        return originDto;
    }

    /**
     * 单条操作 - 结束处理
     *
     * @param operate   服务层 - 操作类型
     * @param row       操作数据条数
     * @param originDto 源数据对象（新增时不存在）
     * @param newDto    新数据对象（删除时不存在）
     */
    @Override
    protected void endHandle(OperateConstants.ServiceType operate, int row, SysModuleDto originDto, SysModuleDto newDto) {
        super.endHandle(operate, row, originDto, newDto);
    }

    /**
     * 批量操作 - 开始处理
     *
     * @param operate 服务层 - 操作类型
     * @param newList 新数据对象集合（删除时不存在）
     * @param idList  Id集合（非删除时不存在）
     */
    @Override
    protected List<SysModuleDto> startBatchHandle(OperateConstants.ServiceType operate, Collection<SysModuleDto> newList, Collection<? extends Serializable> idList) {
        List<SysModuleDto> originList = super.startBatchHandle(operate, newList, idList);
        if (operate == OperateConstants.ServiceType.BATCH_DELETE) {
            originList = originList.stream().filter(item -> (item.isNotCommon() ))
                    .collect(Collectors.toList());
            if (CollUtil.isEmpty(originList)) {
                throw new ServiceException("无待删除模块！");
            }

        }
        return originList;
    }

    /**
     * 批量操作 - 结束处理
     *
     * @param operate    服务层 - 操作类型
     * @param rows       操作数据条数
     * @param originList 源数据对象集合（新增时不存在）
     * @param newList    新数据对象集合（删除时不存在）
     */
    @Override
    protected void endBatchHandle(OperateConstants.ServiceType operate, int rows, Collection<SysModuleDto> originList, Collection<SysModuleDto> newList) {
        super.endBatchHandle(operate, rows, originList, newList);
    }
}
