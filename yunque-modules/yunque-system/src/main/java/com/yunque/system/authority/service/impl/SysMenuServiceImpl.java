package com.yunque.system.authority.service.impl;

import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.constant.basic.OperateConstants;
import com.yunque.common.core.constant.basic.TenantConstants;
import com.yunque.common.core.context.SecurityContextHolder;
import com.yunque.common.core.exception.ServiceException;
import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.core.utils.core.IdUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.security.utils.SecurityUserUtils;
import com.yunque.common.web.correlate.contant.CorrelateConstants;
import com.yunque.common.web.entity.service.impl.TreeServiceImpl;
import com.yunque.system.api.authority.domain.dto.SysMenuDto;
import com.yunque.system.api.authority.domain.dto.SysModuleDto;
import com.yunque.system.api.authority.domain.query.SysMenuQuery;
import com.yunque.system.authority.domain.correlate.SysMenuCorrelate;
import com.yunque.system.authority.manager.ISysMenuManager;
import com.yunque.system.authority.service.ISysMenuService;
import com.yunque.system.authority.service.ISysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统服务 | 权限模块 | 菜单管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysMenuServiceImpl extends TreeServiceImpl<SysMenuQuery, SysMenuDto, SysMenuCorrelate, ISysMenuManager> implements ISysMenuService {

    @Autowired
    protected ISysModuleService moduleService;

    /**
     * 默认方法关联配置定义
     */
    @Override
    protected Map<CorrelateConstants.ServiceType, SysMenuCorrelate> defaultCorrelate() {
        return new HashMap<>() {{
            put(CorrelateConstants.ServiceType.DELETE, SysMenuCorrelate.BASE_DEL);
        }};
    }

    /**
     * 查询菜单对象列表 | 数据权限 | 附加数据
     *
     * @param menu 菜单对象
     * @return 菜单对象集合
     */
    @Override
//    @DataScope(userAlias = CREATE_BY, mapperScope = {"SysMenuMapper"})
    public List<SysMenuDto> selectListScope(SysMenuQuery menu) {
        List<SysMenuDto> list = super.selectListScope(menu);
        subCorrelates(list, SysMenuCorrelate.INFO_LIST);
        return list;
    }

    /**
     * 根据Id查询菜单信息
     *
     * @param id Id
     * @return 菜单数据对象
     */
    @Override
    public SysMenuDto selectInfoById(Serializable id) {
        SysMenuDto menu = selectById(id);
        subCorrelates(menu, SysMenuCorrelate.INFO_LIST);
        return menu;
    }

    /**
     * 获取有权限且状态正常的菜单
     *
     * @param roleIds      角色Id集合
     * @param userType     用户标识
     * @return 菜单对象集合
     */
    @Override
    public List<SysMenuDto> selectEnterpriseList(Set<Long> roleIds, String userType) {
        return baseManager.selectEnterpriseList(roleIds, userType);
    }

    /**
     * 根据模块Id查询菜单路由
     *
     * @param moduleId 模块Id
     * @param menuIds  菜单Ids
     * @return 菜单列表
     */
    @Override
    public List<SysMenuDto> getRoutes(Long moduleId, Collection<Long> menuIds) {
        return baseManager.getRoutes(moduleId, menuIds);
    }

    /**
     * 新增菜单对象
     *
     * @param menu 菜单对象
     * @return 结果
     */
    @Override
    public int insert(SysMenuDto menu) {
        menu.setName(IdUtil.simpleUUID());
        return super.insert(menu);
    }

    /**
     * 新增菜单对象（批量）
     *
     * @param menuList 菜单对象集合
     * @return 结果
     */
    @Override
    public int insertBatch(Collection<SysMenuDto> menuList) {
        if (CollUtil.isNotEmpty(menuList)) {
            menuList.forEach(menu -> menu.setName(IdUtil.simpleUUID()));
        }
        return super.insertBatch(menuList);
    }

    /**
     * 单条操作 - 开始处理
     *
     * @param operate 服务层 - 操作类型
     * @param newDto  新数据对象（删除时不存在）
     * @param id      Id集合（非删除时不存在）
     */
    @Override
    protected SysMenuDto startHandle(OperateConstants.ServiceType operate, SysMenuDto newDto, Serializable id) {
        SysMenuDto originDto = super.startHandle(operate, newDto, id);
        switch (operate) {
            case ADD -> {

            }
            case EDIT -> {
                if (ObjectUtil.isNull(originDto)) {
                    throw new ServiceException("数据不存在！");
                } else if (ObjectUtil.notEqual(originDto.getIsCommon(), newDto.getIsCommon())) {
                    throw new ServiceException(StrUtil.format("{}菜单{}失败，不允许变更公共类型！", operate.getInfo(), newDto.getName()));
                }
                newDto.setIsCommon(originDto.getIsCommon());
            }
            case EDIT_STATUS -> {
                newDto.setIsCommon(originDto.getIsCommon());
            }
            case DELETE -> {
                if ((originDto.isCommon() )) {
                    throw new ServiceException("无操作权限，公共菜单不允许删除！");
                }
            }
        }

        switch (operate) {
            case ADD, EDIT -> {
                if (newDto.isCommon()) {
                    SysModuleDto module = moduleService.selectById(newDto.getModuleId());
                    if (ObjectUtil.isNull(module)) {
                        throw new ServiceException("数据不存在！");
                    }
                    if (module.isNotCommon()) {
                        throw new ServiceException(StrUtil.format("{}菜单{}失败，公共菜单必须挂载在公共模块下！", operate.getInfo(), newDto.getTitle()));
                    }

                    if (ObjectUtil.notEqual(BaseConstants.TOP_ID, newDto.getParentId())) {
                        SysMenuDto parentMenu = baseManager.selectById(newDto.getParentId());
                        if (ObjectUtil.isNull(parentMenu)) {
                            throw new ServiceException("数据不存在！");
                        }
                        if (parentMenu.isNotCommon()) {
                            throw new ServiceException(StrUtil.format("{}菜单{}失败，公共菜单必须挂载在公共菜单下！", operate.getInfo(), newDto.getTitle()));
                        }
                    }
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
    protected void endHandle(OperateConstants.ServiceType operate, int row, SysMenuDto originDto, SysMenuDto newDto) {
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
    protected List<SysMenuDto> startBatchHandle(OperateConstants.ServiceType operate, Collection<SysMenuDto> newList, Collection<? extends Serializable> idList) {
        List<SysMenuDto> originList = super.startBatchHandle(operate, newList, idList);
        if (operate == OperateConstants.ServiceType.BATCH_DELETE) {
            originList = originList.stream().filter(item -> (item.isNotCommon() ))
                    .collect(Collectors.toList());
            if (CollUtil.isEmpty(originList)) {
                throw new ServiceException("无待删除菜单！");
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
    protected void endBatchHandle(OperateConstants.ServiceType operate, int rows, Collection<SysMenuDto> originList, Collection<SysMenuDto> newList) {
        super.endBatchHandle(operate, rows, originList, newList);
    }

    /**
     * 构建树结构
     * 存在默认参数 详见BaseConstants.Entity
     * IdName = ID | FIdName = PARENT_ID | childrenName = CHILDREN | topNode = TOP_NODE | killScattered = false | killNoneChild = true
     *
     * @param list 组装列表
     * @return 树结构列表
     */
    public List<SysMenuDto> buildTree(List<SysMenuDto> list) {
        return buildTree(list, BaseConstants.Entity.ID.getCode(),
                BaseConstants.Entity.PARENT_ID.getCode(),
                BaseConstants.Entity.CHILDREN.getCode(),
                BaseConstants.TOP_ID,
                false,
                true);
    }

    /**
     * 构建树结构
     *
     * @param list          组装列表
     * @param IdName        Id字段名称
     * @param FIdName       父级Id字段名称
     * @param childrenName  children字段名称
     * @param topNode       顶级节点
     * @param killScattered 是否移除无法追溯到顶级节点 (true 是 | false 否)
     * @param killNoneChild 是否移除空子节点集合
     * @return 树结构列表
     */
    public List<SysMenuDto> buildTree(List<SysMenuDto> list, String IdName, String FIdName, String childrenName, Serializable topNode, boolean killScattered, boolean killNoneChild) {
        List<SysMenuDto> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        SysMenuDto top = null;
        boolean hasTopNode = false;
        if (CollUtil.isNotEmpty(list)) {
            for (SysMenuDto vo : list) {
                if (ObjectUtil.equal(vo.getId(), topNode)) {
                    hasTopNode = true;
                    top = vo;
                    list.remove(vo);
                    break;
                }
            }
            for (SysMenuDto vo : list) {
                tempList.add(vo.getId());
            }
            for (SysMenuDto vo : list) {
                // 如果是顶级节点, 遍历该父节点的所有子节点
                if (!tempList.contains((vo.getParentId()))) {
                    recursionFn(list, vo, killNoneChild);
                    returnList.add(vo);
                }
            }
        }

        if (returnList.isEmpty()) {
            returnList = list;
        }
        if (killScattered) {
            deleteNoTopNode(returnList, topNode);
        }
        if (hasTopNode && ObjectUtil.isNotNull(top)) {
            List<SysMenuDto> topList = new ArrayList<>();
            if (killNoneChild) {
                if (CollUtil.isNotEmpty(returnList)) {
                    top.setChildren(returnList);
                }
            } else {
                top.setChildren(returnList);
            }
            topList.add(top);
            return topList;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysMenuDto> list, SysMenuDto t, boolean killNoneChild) {
        // 得到子节点列表
        List<SysMenuDto> childList = getChildList(list, t);
        if (killNoneChild) {
            if (CollUtil.isNotEmpty(childList)) {
                t.setChildren(childList);
            }
        } else {
            t.setChildren(childList);
        }
        for (SysMenuDto tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild, killNoneChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenuDto> getChildList(List<SysMenuDto> list, SysMenuDto t) {
        List<SysMenuDto> tList = new ArrayList<>();
        for (SysMenuDto n : list) {
            if (ObjectUtil.isNotNull(n.getParentId()) && (n.getParentId()).longValue() == (t.getId()).longValue()) {
                tList.add(n);
            }
        }
        return tList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenuDto> list, SysMenuDto t) {
        return CollUtil.isNotEmpty(getChildList(list, t));
    }

    /**
     * 删除无法溯源至顶级节点的值
     */
    private void deleteNoTopNode(List<SysMenuDto> list, Serializable topNode) {
        list.removeIf(vo -> !Objects.equals(vo.getParentId(), topNode));
    }
}
