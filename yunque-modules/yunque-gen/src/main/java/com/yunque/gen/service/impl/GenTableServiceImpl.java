package com.yunque.gen.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.yunque.common.core.constant.basic.DictConstants;
import com.yunque.common.core.constant.basic.HttpConstants;
import com.yunque.common.core.constant.basic.TenantConstants;
import com.yunque.common.core.context.SecurityContextHolder;
import com.yunque.common.core.utils.core.ArrayUtil;
import com.yunque.common.core.utils.core.CharsetUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.core.web.result.R;
import com.yunque.common.web.correlate.contant.CorrelateConstants;
import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.gen.config.GenConfig;
import com.yunque.gen.constant.GenConstants.TemplateType;
import com.yunque.gen.domain.correlate.GenTableCorrelate;
import com.yunque.gen.domain.dto.GenTableColumnDto;
import com.yunque.gen.domain.dto.GenTableDto;
import com.yunque.gen.domain.dto.GenTableOptionDto;
import com.yunque.gen.domain.query.GenTableQuery;
import com.yunque.gen.manager.IGenTableManager;
import com.yunque.gen.service.IGenTableColumnService;
import com.yunque.gen.service.IGenTableService;
import com.yunque.gen.util.GenUtil;
import com.yunque.gen.util.VelocityInitializer;
import com.yunque.gen.util.VelocityUtil;
import com.yunque.system.api.authority.domain.dto.SysMenuDto;
import com.yunque.system.api.authority.feign.RemoteMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.yunque.system.api.authority.constant.AuthorityConstants.MENU_TOP_NODE;

/**
 * 业务管理 业务层处理
 *
 * @author xueyi
 */
@Slf4j
@Service
public class GenTableServiceImpl extends BaseServiceImpl<GenTableQuery, GenTableDto, GenTableCorrelate, IGenTableManager> implements IGenTableService {

    @Autowired
    private RemoteMenuService remoteMenuService;

    @Autowired
    private IGenTableColumnService subService;

    /**
     * 默认方法关联配置定义
     */
    @Override
    protected Map<CorrelateConstants.ServiceType, GenTableCorrelate> defaultCorrelate() {
        return new HashMap<>() {{
            put(CorrelateConstants.ServiceType.SELECT, GenTableCorrelate.INFO_LIST);
            put(CorrelateConstants.ServiceType.DELETE, GenTableCorrelate.BASE_DEL);
        }};
    }

    /**
     * 查询数据库列表
     *
     * @param table 业务对象
     * @return 数据库表集合
     */
    @Override
    public List<GenTableDto> selectDbTableList(GenTableQuery table) {
        return baseManager.selectDbTableList(table);
    }

    /**
     * 根据表名称组查询数据库列表
     *
     * @param tableNames 表名称组
     * @param sourceName 数据源
     * @return 数据库表集合
     */
    @Override
    public List<GenTableDto> selectDbTableListByNames(String[] tableNames, String sourceName) {
        return baseManager.selectDbTableListByNames(tableNames);
    }

    /**
     * 导入表结构
     *
     * @param tableList  导入表列表
     * @param sourceName 数据源
     */

    @Override
    public void importGenTable(List<GenTableDto> tableList, String sourceName) {
        try {
            tableList.forEach(table -> {
                GenUtil.initTable(table);
                int row = baseManager.insert(table);
                if (row > 0) {
                    List<GenTableColumnDto> columnList = subService.selectDbTableColumnsByName(table.getName(), sourceName);
                    columnList.forEach(column -> GenUtil.initColumnField(column, table));
                    subService.insertBatch(columnList);
                    GenUtil.initTableOptions(columnList, table);
                    baseManager.update(table);
                }
            });
        } catch (Exception e) {
            AjaxResult.warn(StrUtil.format("导入失败：{}", e.getMessage()));
        }
    }

    /**
     * 修改业务
     *
     * @param table 业务信息
     */
    @Override
    public int update(GenTableDto table) {
        int row = baseManager.update(table);
        if (row > 0) {
            GenUtil.updateCheckColumn(table);
        }
        table.getSubList().forEach(column -> subService.update(column));
        return row;
    }

    /**
     * 预览代码
     *
     * @param id Id
     * @return 预览数据列表
     */
    @Override
    public List<JSONObject> previewCode(Long id) {
        List<JSONObject> dataMap = new ArrayList<>();
        // 查询表信息
        GenTableDto table = initTable(id);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtil.prepareContext(table);

        // 获取模板列表
        JSONObject data;
        List<String> templates = VelocityUtil.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, HttpConstants.Character.UTF8.getCode());
            tpl.merge(context, sw);
            data = new JSONObject();
            String vmName = StrUtil.subAfter(template, StrUtil.SLASH, true);
            vmName = StrUtil.removeSuffix(vmName, (".vm"));
            data.put("name", vmName);
            data.put("language", StrUtil.subAfter(vmName, StrUtil.DOT, true));
            data.put("template", sw.toString());
            dataMap.add(data);
        }
        return dataMap;
    }

    /**
     * 生成代码（下载方式）
     *
     * @param id Id
     * @return 数据
     */
    @Override
    public byte[] downloadCode(Long id) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(id, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 生成代码（自定义路径）
     *
     * @param id Id
     */
    @Override
    public void generatorCode(Long id) {
        // 查询表信息
        GenTableDto table = initTable(id);

        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtil.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtil.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, HttpConstants.Character.UTF8.getCode());
            tpl.merge(context, sw);
            try {
                String path = VelocityUtil.getFileName(template, table, Boolean.FALSE);
                FileUtils.writeStringToFile(new File(path), sw.toString(), CharsetUtil.UTF_8);
            } catch (IOException e) {
                AjaxResult.warn(StrUtil.format("渲染模板失败，表名：{}", table.getName()));
            }
        }
    }

    /**
     * 批量生成代码（下载方式）
     *
     * @param ids Ids数组
     * @return 数据
     */
    @Override
    public byte[] downloadCode(Long[] ids) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (Long id : ids) {
            generatorCode(id, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 查询表信息并生成代码
     *
     * @param id  Id
     * @param zip 压缩包流
     */
    private void generatorCode(Long id, ZipOutputStream zip) {
        // 查询表信息
        GenTableDto table = initTable(id);

        VelocityInitializer.initVelocity();
        VelocityContext context = VelocityUtil.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtil.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, HttpConstants.Character.UTF8.getCode());
            tpl.merge(context, sw);
            try {
                String fileUrl = VelocityUtil.getFileName(template, table, Boolean.TRUE);
                // 添加到zip
                zip.putNextEntry(new ZipEntry(fileUrl));
                IOUtils.write(sw.toString(), zip, HttpConstants.Character.UTF8.getCode());
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                log.error("渲染模板失败，表名：" + table.getName(), e);
            }
        }
    }

    /**
     * 修改保存参数校验
     *
     * @param genTable 业务信息
     */
    @Override
    public void validateEdit(GenTableDto genTable) {
        GenTableOptionDto optionInfo = genTable.getOptions();
        checkTclBasic(genTable, optionInfo);
        switch (TemplateType.getByCode(genTable.getTplCategory())) {
            case TREE:
                checkTclTree(optionInfo);
            case BASE:
                checkTclBase(optionInfo);
        }
    }

    /**
     * 校验基础表配置
     *
     * @param genTable   业务表
     * @param optionInfo 其它生成选项信息
     */
    private void checkTclBasic(GenTableDto genTable, GenTableOptionDto optionInfo) {
        checkSourceMode(genTable, optionInfo);
        checkCommonMode(genTable, optionInfo);

    }

    /**
     * 校验基础源策略模式配置
     *
     * @param genTable   业务表
     * @param optionInfo 其它生成选项信息
     */
    private void checkSourceMode(GenTableDto genTable, GenTableOptionDto optionInfo) {
        GenTableOptionDto.BasicInfo basicInfo = optionInfo.getBasicInfo();
        if (StrUtil.isEmpty(basicInfo.getSourceMode())) {
            AjaxResult.warn("未设置源策略模式！");
        }
        if (StrUtil.equals(basicInfo.getIsTenant(), DictConstants.DicYesNo.YES.getCode())) {
            for (GenTableColumnDto column : genTable.getSubList()) {
                if (ArrayUtil.contains(GenConfig.getEntity().getBack().getTenant(), column.getJavaField())) {
                    return;
                }
            }
            AjaxResult.warn("未在业务表中发现多租户关键字，请关闭多租户模式重试！");
        }
    }

    /**
     * 校验数据混合模式配置
     *
     * @param genTable   业务表
     * @param optionInfo 其它生成选项信息
     */
    private void checkCommonMode(GenTableDto genTable, GenTableOptionDto optionInfo) {
        GenTableOptionDto.BasicInfo basicInfo = optionInfo.getBasicInfo();
//        if (StrUtil.isEmpty(optionsObj.getString(OptionField.COMMON_MODE.getCode()))) {
//            // 暂不开启 | 兼容vue2 next-ui变更
////            AjaxResult.warn("未设置数据混合模式！");
//            return;
//        }
//        if (StrUtil.isNotEmpty(optionsObj.getString(OptionField.IS_COMMON.getCode())) && StrUtil.equals(optionsObj.getString(OptionField.IS_COMMON.getCode()), DictConstants.DicYesNo.YES.getCode())) {
//            for (GenTableColumnDto column : genTable.getSubList()) {
//                if (ArrayUtil.contains(GenConfig.getEntity().getBack().getCommon(), column.getJavaField())) {
//                    return;
//                }
//            }
//            AjaxResult.warn("未在业务表中发现公共数据关键字，请关闭数据混合模式重试！");
//        }
    }

    /**
     * 校验单表配置
     *
     * @param optionsObj 其它生成选项信息
     */
    private void checkTclBase(GenTableOptionDto optionsObj) {
        GenTableOptionDto.MenuInfo menuInfo = optionsObj.getMenuInfo();
        if (ObjectUtil.isEmpty(menuInfo.getParentModuleId())) {
            AjaxResult.warn("归属模块不能为空");
        } else if (ObjectUtil.isEmpty(menuInfo.getParentMenuId())) {
            AjaxResult.warn("归属菜单不能为空");
        }
    }

    /**
     * 校验树表配置
     *
     * @param optionsObj 其它生成选项信息
     */
    private void checkTclTree(GenTableOptionDto optionsObj) {
        GenTableOptionDto.FieldInfo fieldInfo = optionsObj.getFieldInfo();
        if (ObjectUtil.isEmpty(fieldInfo.getTreeCode())) {
            AjaxResult.warn("树编码字段不能为空");
        } else if (ObjectUtil.isEmpty(fieldInfo.getParentId())) {
            AjaxResult.warn("树父编码字段不能为空");
        } else if (ObjectUtil.isEmpty(fieldInfo.getTreeName())) {
            AjaxResult.warn("树名称字段不能为空");
        } else if (ObjectUtil.isEmpty(fieldInfo.getAncestors())) {
            AjaxResult.warn("树祖籍列表字段不能为空");
        }
    }

    /**
     * 初始化代码生成表数据
     *
     * @param id Id
     * @return 业务表对象
     */
    private GenTableDto initTable(Long id) {
        GenTableDto table = selectById(id);
        GenTableOptionDto optionsObj = table.getOptions();
        // 设置列信息
        switch (TemplateType.getByCode(table.getTplCategory())) {
            case TREE:
                setTreeTable(table, optionsObj);
            case BASE:
                setBaseTable(table, optionsObj);
                setMenuOptions(table, optionsObj);
        }
        return table;
    }

    /**
     * 设置基础表信息
     *
     * @param table      业务表信息
     * @param optionsObj 其它生成选项信息
     */
    private void setBaseTable(GenTableDto table, GenTableOptionDto optionsObj) {
        table.getSubList().forEach(column -> {
            if (column.getIsPk()) {
                table.setPkColumn(column);
            }
        });
    }

    /**
     * 设置树表信息
     *
     * @param table      业务表信息
     * @param optionsObj 其它生成选项信息
     */
    private void setTreeTable(GenTableDto table, GenTableOptionDto optionsObj) {
    }

    /**
     * 设置菜单信息
     *
     * @param table      业务表信息
     * @param optionsObj 其它生成选项信息
     */
    private void setMenuOptions(GenTableDto table, GenTableOptionDto optionsObj) {
        GenTableOptionDto.MenuInfo menuInfo = optionsObj.getMenuInfo();
        Long menuId = menuInfo.getParentMenuId();
        if (ObjectUtil.equals(MENU_TOP_NODE, menuId)) {
            menuInfo.setParentMenuAncestors(menuId.toString());
        } else {
            R<SysMenuDto> result = remoteMenuService.getInfoInner(menuId);
            if (result.isFail()) {
                AjaxResult.warn("菜单服务异常，请联系管理员！");
            } else if (ObjectUtil.isNull(result.getData())) {
                AjaxResult.warn("该服务对应的菜单已被删除，请先修改后再生成代码！");
            }
            SysMenuDto menu = result.getData();
            if (StrUtil.isEmpty(menu.getAncestors())) {
                menuInfo.setParentMenuAncestors(menu.getId().toString());
            } else {
                menuInfo.setParentMenuAncestors(StrUtil.format("{},{}", menu.getAncestors(), menu.getId()));
            }
        }
        table.setOptions(optionsObj);
    }
}
