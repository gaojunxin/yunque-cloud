package com.yunque.gen.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import com.alibaba.fastjson2.JSONObject;
import com.yunque.common.core.constant.basic.DictConstants;
import com.yunque.common.core.exception.ServiceException;
import com.yunque.common.core.utils.core.ArrayUtil;
import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.core.utils.core.IdUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.gen.config.GenConfig;
import com.yunque.gen.constant.GenConstants;
import com.yunque.gen.domain.dto.GenTableColumnDto;
import com.yunque.gen.domain.dto.GenTableDto;
import com.yunque.gen.domain.dto.GenTableOptionDto;
import com.yunque.system.api.authority.constant.AuthorityConstants;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 模板工具类
 *
 * @author xueyi
 */
public class VelocityUtil {

    /** 主目录 */
    private static final String PROJECT_PATH = "main/java";

    /** 隐藏字段数组 */
    private static final String HIDE = "hide";

    /** 覆写字段数组 */
    private static final String COVER = "cover";

    /**
     * 设置模板变量信息
     *
     * @param genTable 业务表数据传输对象
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTableDto genTable) {
        if (StrUtil.hasEmpty(genTable.getRdPackageName(), genTable.getFePackageName(), genTable.getModuleName(), genTable.getBusinessName(), genTable.getAuthorityName()))
            throw new ServiceException("请先编辑完善信息！");
        String businessName = genTable.getBusinessName();
        String rdPackageName = genTable.getRdPackageName();
        // 前端包路径
        String fePackageName = StrUtil.replace(genTable.getFePackageName(), StrUtil.DOT, StrUtil.SLASH);
        String tplCategory = genTable.getTplCategory();
        String moduleFunctionName = StrUtil.isNotEmpty(genTable.getFunctionName()) ? genTable.getFunctionName() : "【请填写功能名称】";
        String functionName = getFunctionName(moduleFunctionName);
        GenTableOptionDto optionsObj = genTable.getOptions();
        Map<String, Set<String>> domainMap = getCoverMap(genTable);
        initTableField(genTable);
        VelocityContext velocityContext = new VelocityContext();
        // 模板类型
        velocityContext.put("tplCategory", genTable.getTplCategory());
        // 数据库表名
        velocityContext.put("tableName", genTable.getName());
        // 生成功能名
        velocityContext.put("functionName", functionName);
        // 生成模块功能名
        velocityContext.put("moduleFunctionName", moduleFunctionName);
        // 实体类名称(首字母大写)
        velocityContext.put("ClassName", genTable.getClassName());
        // 实体类名称(首字母小写)
        velocityContext.put("className", StrUtil.uncapitalize(genTable.getClassName()));
        // 实体类名称(全大写 | _划分)
        velocityContext.put("ClASS_NAME", (StrUtil.toUnderlineCase(genTable.getClassName())).toUpperCase());
        //  判断genTable.getPrefix()是否为空
        String prefix = StrUtil.isEmpty(genTable.getPrefix()) ? StrUtil.EMPTY : genTable.getPrefix();
        // 实体类名称(首字母大写 | 无前缀)
        velocityContext.put("ClassNameNoPrefix", genTable.getClassName().replaceFirst(prefix, StrUtil.EMPTY));
        // 实体类名称(首字母小写 | 无前缀)
        velocityContext.put("classNameNoPrefix", StrUtil.uncapitalize(genTable.getClassName().replaceFirst(prefix, StrUtil.EMPTY)));
        // 生成模块名
        velocityContext.put("moduleName", genTable.getModuleName());
        // 生成模块名
        velocityContext.put("authorityName", genTable.getAuthorityName());
        // 生成业务名(首字母大写)
        velocityContext.put("BusinessName", StrUtil.capitalize(businessName));
        // 生成业务名(首字母小写)
        velocityContext.put("businessName", businessName);
        // 生成业务名(字母全大写)
        velocityContext.put("BUSINESSName", StrUtil.upperCase(businessName));
        // 生成后端包路径
        velocityContext.put("rdPackageName", rdPackageName);
        // 生成前端包路径
        velocityContext.put("fePackageName", fePackageName);
        // 作者
        velocityContext.put("author", genTable.getFunctionAuthor());
        // 主键字段
        velocityContext.put("pkColumn", genTable.getPkColumn());
        // 导入包集合
        velocityContext.put("importList", getImportList(genTable, domainMap.get(HIDE)));
        // 字段集合
        velocityContext.put("columns", genTable.getSubList());
        // 数据表排除字段
        velocityContext.put("excludeProperty", getExcludePropertyList(genTable));
        // 业务表信息
        velocityContext.put("table", genTable);
        // 字典组
        velocityContext.put("dictMap", getDictMap(genTable));
        // Po覆盖字段集合 (生成po的控制参数)
        velocityContext.put("coverField", domainMap.get(COVER));
        // Po隐藏字段集合 (生成po的控制参数)
        velocityContext.put("hideField", domainMap.get(HIDE));
        // 前端隐藏字段集合 (生成po的控制参数)
        velocityContext.put("frontHideField", getFrontHideField(genTable.getTplCategory()));
        // 必定隐藏字段集合 (全局隐藏的控制参数)
        velocityContext.put("mustHideField", Arrays.asList(GenConfig.getEntity().getMustHide()));
        // 是否为多租户（true | false）
        velocityContext.put("isTenant", isTenant(optionsObj));
        // 源策略模式
        velocityContext.put("sourceMode", getSourceMode(optionsObj));
        // 依赖缩写模式
        velocityContext.put("isDependMode", getDependMode(optionsObj));
        // 源策略是否为主库
        velocityContext.put("isMasterSource", isMasterSource(optionsObj));
        // 获取其他重要参数（名称、状态...）
        getOtherMainColum(velocityContext, genTable);
        // sql模板设置
        setMenuVelocityContext(velocityContext, optionsObj);
        // api设置
        setApiStatus(velocityContext, optionsObj);
        if (GenConstants.TemplateType.getByCode(tplCategory) == GenConstants.TemplateType.TREE) {
            setTreeVelocityContext(velocityContext, genTable, optionsObj);
        }
        return velocityContext;
    }

    /**
     * 获取附加参数信息
     *
     * @param context  模板列表
     * @param genTable 业务表数据传输对象
     */
    public static void getOtherMainColum(VelocityContext context, GenTableDto genTable) {
        for (GenTableColumnDto column : genTable.getSubList()) {
            if (StrUtil.equals(column.getJavaField(), GenConstants.OptionField.NAME.getCode())) {
                context.put("nameColumn", column); // 名称字段
            } else if (StrUtil.equals(column.getJavaField(), GenConstants.OptionField.STATUS.getCode())) {
                context.put("statusColumn", column); // 状态字段
            } else if (StrUtil.equals(column.getJavaField(), GenConstants.OptionField.SORT.getCode())) {
                context.put("sortColumn", column); // 排序字段
            }
        }
    }

    /**
     * 设置sql模板变量信息
     *
     * @param context    模板列表
     * @param optionsObj 生成其他选项
     */
    public static void setMenuVelocityContext(VelocityContext context, GenTableOptionDto optionsObj) {
        GenTableOptionDto.MenuInfo menuInfo = optionsObj.getMenuInfo();
        context.put("parentModuleId", Optional.ofNullable(menuInfo.getParentModuleId()).orElse(AuthorityConstants.MODULE_DEFAULT_NODE));
        context.put("parentMenuId", Optional.ofNullable(menuInfo.getParentMenuId()).orElse(AuthorityConstants.MENU_TOP_NODE));
        String parentMenuAncestors = menuInfo.getParentMenuAncestors();
        context.put("parentMenuAncestors", parentMenuAncestors);
        List<String> ancestorsArr = StrUtil.split(parentMenuAncestors, StrUtil.COMMA);
        context.put("level", ancestorsArr.size() + 1);
        boolean hasIdGenerator = StrUtil.isNotBlank(menuInfo.getIdGenerator());
        // 生成菜单menuId0-9
        for (int i = 0; i < 10; i++) {
            context.put("menuId" + i, hasIdGenerator ? StrUtil.format(menuInfo.getIdGenerator(), i) : IdUtil.getSnowflake(0, 0).nextId());
            context.put("menuName" + i, IdUtil.simpleUUID());
        }
    }

    /**
     * 设置树表模板变量信息
     *
     * @param context    模板列表
     * @param genTable   业务表数据传输对象
     * @param optionsObj 生成其他选项
     */
    public static void setTreeVelocityContext(VelocityContext context, GenTableDto genTable, GenTableOptionDto optionsObj) {
        JSONObject treeObject = new JSONObject();
        GenTableOptionDto.FieldInfo fieldInfo = optionsObj.getFieldInfo();
        genTable.getSubList().forEach(column -> {
            if (ObjectUtil.equals(column.getId(), fieldInfo.getTreeCode())) {
                treeObject.put("idColumn", column);
            } else if (ObjectUtil.equals(column.getId(), fieldInfo.getParentId())) {
                treeObject.put("parentIdColumn", column);
            } else if (ObjectUtil.equals(column.getId(), fieldInfo.getTreeName())) {
                treeObject.put("nameColumn", column);
            }
        });
        context.put("treeMap", treeObject);
    }

    /**
     * 设置接口变量信息
     *
     * @param context    模板列表
     * @param optionsObj 生成其他选项
     */
    public static void setApiStatus(VelocityContext context, GenTableOptionDto optionsObj) {
        GenTableOptionDto.ApiInfo apiInfo = optionsObj.getApiInfo();
        JSONObject apiJSon = new JSONObject();
        apiJSon.put("list", StrUtil.equals(apiInfo.getApiList(), DictConstants.DicYesNo.YES.getCode()));
        apiJSon.put("getInfo", StrUtil.equals(apiInfo.getApiGetInfo(), DictConstants.DicYesNo.YES.getCode()));
        apiJSon.put("add", StrUtil.equals(apiInfo.getApiAdd(), DictConstants.DicYesNo.YES.getCode()));
        apiJSon.put("edit", StrUtil.equals(apiInfo.getApiEdit(), DictConstants.DicYesNo.YES.getCode()));
        apiJSon.put("editStatus", StrUtil.equals(apiInfo.getApiEditStatus(), DictConstants.DicYesNo.YES.getCode()));
        apiJSon.put("batchRemove", StrUtil.equals(apiInfo.getApiBatchRemove(), DictConstants.DicYesNo.YES.getCode()));
        apiJSon.put("import", StrUtil.equals(apiInfo.getApiImport(), DictConstants.DicYesNo.YES.getCode()));
        apiJSon.put("export", StrUtil.equals(apiInfo.getApiExport(), DictConstants.DicYesNo.YES.getCode()));
        apiJSon.put("cache", StrUtil.equals(apiInfo.getApiCache(), DictConstants.DicYesNo.YES.getCode()));
        context.put("api", apiJSon);
    }

    /**
     * 获取模板信息
     *
     * @param tplCategory 表模板类型
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory) {
        List<String> templates = new ArrayList<>();
        if (StrUtil.equals(tplCategory, GenConstants.TemplateType.MERGE.getCode())) {
            templates.add("vm/java/domain/merge.java.vm");
            templates.add("vm/java/mapper/mergeMapper.java.vm");
        } else {
            templates.add("vm/java/domain/correlate.java.vm");
            templates.add("vm/java/domain/query.java.vm");
            templates.add("vm/java/domain/dto.java.vm");
            templates.add("vm/java/domain/po.java.vm");
            templates.add("vm/java/domain/converter.java.vm");
            templates.add("vm/java/controller/aController.java.vm");
            templates.add("vm/java/controller/bController.java.vm");
            templates.add("vm/java/controller/iController.java.vm");
            templates.add("vm/java/service/service.java.vm");
            templates.add("vm/java/service/serviceImpl.java.vm");
            templates.add("vm/java/manager/manager.java.vm");
            templates.add("vm/java/manager/managerImpl.java.vm");
            templates.add("vm/java/mapper/mapper.java.vm");
            templates.add("vm/sql/sql.sql.vm");
            templates.add("vm/multi/ts/api.ts.vm");
            templates.add("vm/multi/ts/data.ts.vm");
            templates.add("vm/multi/ts/auth.ts.vm");
            templates.add("vm/multi/ts/enum.ts.vm");
            templates.add("vm/multi/ts/infoModel.ts.vm");
            templates.add("vm/multi/vue/detail.vue.vm");
            templates.add("vm/multi/vue/index.vue.vm");
            templates.add("vm/multi/vue/drawer.vue.vm");
        }
        return templates;
    }

    /**
     * 获取文件名
     *
     * @param template 文件名
     * @param table    业务表数据传输对象
     * @param isZip    生成到ZIP
     */
    public static String getFileName(String template, GenTableDto table, Boolean isZip) {
        // 后端包路径
        String rdPackageName = table.getRdPackageName();
        // 前端包路径
        String fePackageName = StrUtil.replace(table.getFePackageName(), StrUtil.DOT, StrUtil.SLASH);
        // 大写类名
        String className = table.getClassName();
        // 业务名称
        String businessName = table.getBusinessName();

        GenTableOptionDto optionsObj = table.getOptions();
        // 获取依赖缩写模式
        boolean isDependMode = getDependMode(optionsObj);

        String realGenPath, realUiPath;
        if (isZip) {
            realGenPath = StrUtil.EMPTY;
            realUiPath = StrUtil.EMPTY;
        } else if (StrUtil.equals(GenConstants.GenType.DEFAULT.getCode(), table.getGenType()) || StrUtil.isBlank(table.getGenPath())) {
            realGenPath = System.getProperty("user.dir") + File.separator + "genFile" + File.separator + "src" + File.separator;
            realUiPath = System.getProperty("user.dir") + File.separator + "genFile" + File.separator;
        } else {
            realGenPath = table.getGenPath();
            realUiPath = table.getUiPath();
        }

        String javaPath = PROJECT_PATH + StrUtil.SLASH + StrUtil.replace(rdPackageName, StrUtil.DOT, StrUtil.SLASH);
        String uiPath = "multi-ui";

        if (template.contains("query.java.vm")) {
            return StrUtil.format("{}{}/domain/query/{}Query.java", realGenPath, javaPath, className);
        } else if (template.contains("dto.java.vm")) {
            return StrUtil.format("{}{}/domain/dto/{}Dto.java", realGenPath, javaPath, className);
        } else if (template.contains("po.java.vm")) {
            return StrUtil.format("{}{}/domain/po/{}Po.java", realGenPath, javaPath, className);
        } else if (template.contains("converter.java.vm")) {
            return StrUtil.format("{}{}/domain/model/{}Converter.java", realGenPath, javaPath, className);
        } else if (template.contains("correlate.java.vm")) {
            return StrUtil.format("{}{}/domain/correlate/{}Correlate.java", realGenPath, javaPath, className);
        } else if (template.contains("aController.java.vm")) {
            return StrUtil.format("{}{}/controller/admin/A{}Controller.java", realGenPath, javaPath, className);
        } else if (template.contains("bController.java.vm")) {
            return StrUtil.format("{}{}/controller/base/B{}Controller.java", realGenPath, javaPath, className);
        } else if (template.contains("iController.java.vm")) {
            return StrUtil.format("{}{}/controller/inner/I{}Controller.java", realGenPath, javaPath, className);
        } else if (template.contains("service.java.vm")) {
            return StrUtil.format("{}{}/service/I{}Service.java", realGenPath, javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")) {
            return StrUtil.format("{}{}/service/impl/{}ServiceImpl.java", realGenPath, javaPath, className);
        } else if (template.contains("manager.java.vm")) {
            return StrUtil.format("{}{}/manager/I{}Manager.java", realGenPath, javaPath, className);
        } else if (template.contains("managerImpl.java.vm")) {
            return StrUtil.format("{}{}/manager/impl/{}ManagerImpl.java", realGenPath, javaPath, className);
        } else if (template.contains("mapper.java.vm")) {
            return StrUtil.format("{}{}/mapper/{}Mapper.java", realGenPath, javaPath, className);
        } else if (template.contains("merge.java.vm")) {
            return StrUtil.format("{}{}/domain/merge/{}.java", realGenPath, javaPath, className);
        } else if (template.contains("mergeMapper.java.vm")) {
            return StrUtil.format("{}{}/mapper/merge/{}Mapper.java", realGenPath, javaPath, className);
        } else if (template.contains("sql.sql.vm")) {
            return StrUtil.format("{}sql/{}.sql", realUiPath, businessName);
        }

        if (template.contains("api.ts.vm")) {
            String prefixPath = StrUtil.format("{}{}/src/api", realUiPath, uiPath);
            String suffixFile = ".api";
            return StrUtil.format("{}/{}/{}{}.ts", prefixPath, fePackageName, businessName, suffixFile);
        } else if (template.contains("infoModel.ts.vm")) {
            String prefixPath = StrUtil.format("{}{}/src/model", realUiPath, uiPath);
            String suffixFile = ".model";
            if (!isZip && isDependMode) {
                initIndexFile(prefixPath, suffixFile, fePackageName, businessName);
            }
            return StrUtil.format("{}/{}/{}{}.ts", prefixPath, fePackageName, businessName, suffixFile);
        } else if (template.contains("auth.ts.vm")) {
            String prefixPath = StrUtil.format("{}{}/src/auth", realUiPath, uiPath);
            String suffixFile = ".auth";
            if (!isZip && isDependMode) {
                initIndexFile(prefixPath, suffixFile, fePackageName, businessName);
            }
            return StrUtil.format("{}/{}/{}{}.ts", prefixPath, fePackageName, businessName, suffixFile);
        } else if (template.contains("enum.ts.vm")) {
            String prefixPath = StrUtil.format("{}{}/src/enums", realUiPath, uiPath);
            String suffixFile = ".enum";
            if (!isZip && isDependMode) {
                initIndexFile(prefixPath, suffixFile, fePackageName, businessName);
            }
            return StrUtil.format("{}/{}/{}{}.ts", prefixPath, fePackageName, businessName, suffixFile);
        } else if (template.contains("data.ts.vm")) {
            String prefixPath = StrUtil.format("{}{}/src/views", realUiPath, uiPath);
            return StrUtil.format("{}/{}/{}/data.ts", prefixPath, fePackageName, businessName);
        } else if (template.contains("index.vue.vm")) {
            String prefixPath = StrUtil.format("{}{}/src/views", realUiPath, uiPath);
            return StrUtil.format("{}/{}/{}/index.vue", prefixPath, fePackageName, businessName);
        } else if (template.contains("detail.vue.vm")) {
            String prefixPath = StrUtil.format("{}{}/src/views", realUiPath, uiPath);
            return StrUtil.format("{}/{}/{}/Detail.vue", prefixPath, fePackageName, businessName);
        } else if (template.contains("drawer.vue.vm")) {
            String prefixPath = StrUtil.format("{}{}/src/views", realUiPath, uiPath);
            return StrUtil.format("{}/{}/{}/Drawer.vue", prefixPath, fePackageName, businessName);
        }
        throw new ServiceException("未知文件无法生成！");
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getFunctionName(String packageName) {
        int lastIndex = packageName.lastIndexOf("|");
        String str = lastIndex > 0 ? StrUtil.subSuf(packageName, lastIndex + 1) : packageName;
        return StrUtil.cleanBlank(str);
    }

    /**
     * 初始化表字段
     *
     * @param genTable 业务表对象
     */
    public static void initTableField(GenTableDto genTable) {
        genTable.getSubList().forEach(item -> {
            item.setIsDivideTable(StrUtil.notEquals(item.getJavaField(), StrUtil.toCamelCase(item.getName())));
            item.setIsPo(Boolean.TRUE);
            if (item.getIsHide()) {
                item.setIsPo(Boolean.FALSE);
            } else if (ArrayUtil.contains(GenConfig.getEntity().getMustHide(), item.getJavaField())) {
                item.setIsPo(Boolean.FALSE);
            } else if (!genTable.isMerge() && ArrayUtil.contains(GenConfig.getEntity().getBack().getBase(), item.getJavaField())) {
                item.setIsPo(Boolean.FALSE);
            } else if (genTable.isTree() && ArrayUtil.contains(GenConfig.getEntity().getBack().getTree(), item.getJavaField())) {
                item.setIsPo(Boolean.FALSE);
            }
            GenConstants.GenField field = GenConstants.GenField.getByCode(item.getJavaField());
            if (ObjectUtil.isNotNull(field)) {
                switch (field) {
                    case ID, PARENT_ID -> {
                        if (item.getIsDivideTable()
                                || StrUtil.notEquals(item.getJavaType(), GenConstants.JavaType.LONG.getCode())
                                || StrUtil.notEquals(item.getQueryType(), GenConstants.QueryType.EQ.getCode())) {
                            item.setIsPo(Boolean.TRUE);
                        }
                    }
                    case NAME, REMARK -> {
                        if (item.getIsDivideTable()
                                || StrUtil.notEquals(item.getJavaType(), GenConstants.JavaType.STRING.getCode())
                                || StrUtil.notEquals(item.getQueryType(), GenConstants.QueryType.LIKE.getCode())) {
                            item.setIsPo(Boolean.TRUE);
                        }
                    }
                    case STATUS -> {
                        if (item.getIsDivideTable()
                                || StrUtil.notEquals(item.getJavaType(), GenConstants.JavaType.STRING.getCode())
                                || StrUtil.notEquals(item.getQueryType(), GenConstants.QueryType.EQ.getCode())
                                || !StrUtil.contains(item.getComment(), GenConstants.GenCheck.NORMAL_DISABLE.getInfo())) {
                            item.setIsPo(Boolean.TRUE);
                        }
                    }
                    case SORT -> {
                        if (item.getIsDivideTable()
                                || StrUtil.notEquals(item.getJavaType(), GenConstants.JavaType.INTEGER.getCode())
                                || StrUtil.notEquals(item.getQueryType(), GenConstants.QueryType.EQ.getCode())) {
                            item.setIsPo(Boolean.TRUE);
                        }
                    }
                    case ANCESTORS -> {
                        if (item.getIsDivideTable()
                                || StrUtil.notEquals(item.getJavaType(), GenConstants.JavaType.STRING.getCode())
                                || StrUtil.notEquals(item.getQueryType(), GenConstants.QueryType.EQ.getCode())) {
                            item.setIsPo(Boolean.TRUE);
                        }
                    }
                    default -> {
                    }
                }
            }
        });
    }

    /**
     * 获取覆盖与隐藏字段信息
     */
    public static Map<String, Set<String>> getCoverMap(GenTableDto genTable) {
        Set<String> coverSet = genTable.getSubList().stream().filter(GenTableColumnDto::getIsCover).map(GenTableColumnDto::getJavaField).collect(Collectors.toSet());
        Set<String> hideSet = genTable.getSubList().stream().filter(GenTableColumnDto::getIsHide).map(GenTableColumnDto::getJavaField).collect(Collectors.toSet());
        switch (GenConstants.TemplateType.getByCode(genTable.getTplCategory())) {
            case TREE -> {
                Set<String> treeSet = new HashSet<>(Arrays.asList(ArrayUtil.addAll(GenConfig.getEntity().getBack().getBase(), GenConfig.getEntity().getBack().getTree())));
                treeSet.removeAll(coverSet);
                hideSet.addAll(treeSet);
            }
            case BASE -> {
                Set<String> baseSet = new HashSet<>(Arrays.asList(ArrayUtil.addAll(GenConfig.getEntity().getBack().getBase())));
                baseSet.removeAll(coverSet);
                hideSet.addAll(baseSet);
            }
        }
        Map<String, Set<String>> map = new HashMap<>();
        map.put(COVER, coverSet);
        map.put(HIDE, hideSet);
        return map;
    }

    /**
     * 是否为多租户
     *
     * @param optionsObj 生成其他选项
     * @return 是否为多租户
     */
    public static boolean isTenant(GenTableOptionDto optionsObj) {
        return StrUtil.equals(optionsObj.getBasicInfo().getIsTenant(), DictConstants.DicYesNo.YES.getCode());
    }

    /**
     * 获取源策略
     *
     * @param optionsObj 生成其他选项
     * @return 是否为多租户
     */
    public static String getSourceMode(GenTableOptionDto optionsObj) {
        return optionsObj.getBasicInfo().getSourceMode();
    }

    /**
     * 获取依赖缩写模式
     *
     * @param optionsObj 生成其他选项
     * @return 是否为依赖缩写模式
     */
    public static Boolean getDependMode(GenTableOptionDto optionsObj) {
        return StrUtil.equals(DictConstants.DicYesNo.YES.getCode(), optionsObj.getBasicInfo().getDependMode());
    }

    /**
     * 获取前端隐藏字段
     *
     * @param tplCategory 表模板类型
     * @return 前端隐藏字段集合
     */
    public static Set<String> getFrontHideField(String tplCategory) {
        Set<String> stringSet = new HashSet<>(Arrays.asList(GenConfig.getEntity().getMustHide()));
        switch (GenConstants.TemplateType.getByCode(tplCategory)) {
            case TREE -> {
                stringSet.addAll(Arrays.asList(GenConfig.getEntity().getFront().getTree()));
                stringSet.addAll(Arrays.asList(GenConfig.getEntity().getFront().getBase()));
            }
            case BASE -> stringSet.addAll(Arrays.asList(GenConfig.getEntity().getFront().getBase()));
        }
        return stringSet;
    }

    /**
     * 源策略是否为主库
     *
     * @param optionsObj 生成其他选项
     * @return 是否为多租户
     */
    public static boolean isMasterSource(GenTableOptionDto optionsObj) {
        return StrUtil.equals(optionsObj.getBasicInfo().getSourceMode(), GenConstants.SourceMode.MASTER.getCode());
    }

    /**
     * 根据列类型获取导入包
     *
     * @param genTable 业务表对象
     * @return 返回需要导入的包列表
     */
    public static HashSet<String> getImportList(GenTableDto genTable, Set<String> hideList) {
        List<GenTableColumnDto> columns = genTable.getSubList();
        GenTableDto subGenTableDto = genTable.getSubTable();
        HashSet<String> importList = new HashSet<>();
        if (ObjectUtil.isNotNull(subGenTableDto)) {
            importList.add("java.util.List");
        }
        int hideLength = hideList.size();
        String[] hides = hideList.toArray(new String[hideLength]);
        for (GenTableColumnDto column : columns) {
            if (StrUtil.equals(GenConstants.JavaType.DATE.getCode(), column.getJavaType()) && !StrUtil.equalsAny(column.getJavaField(), hides)) {
                importList.add("java.util.Date");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            } else if (StrUtil.equals(GenConstants.JavaType.BIG_DECIMAL.getCode(), column.getJavaType()) && !StrUtil.equalsAny(column.getJavaField(), hides)) {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }

    /**
     * 根据列类型获取字典组
     *
     * @param genTable 业务表对象
     * @return 返回字典组
     */
    public static Map<String, String> getDictMap(GenTableDto genTable) {
        Set<String> dictTypeSet = new HashSet<>();
        for (GenTableColumnDto column : genTable.getSubList()) {
            if (StrUtil.isNotEmpty(column.getDictType()) && StrUtil.equalsAny(column.getHtmlType(), GenConstants.DisplayType.SELECT.getCode(), GenConstants.DisplayType.CHECKBOX_GROUP.getCode(), GenConstants.DisplayType.RADIO_BUTTON_GROUP.getCode(), GenConstants.DisplayType.RADIO_GROUP.getCode())) {
                dictTypeSet.add(column.getDictType());
                column.setDictName(getDictName(column.getDictType()));
            }
        }
        Map<String, String> dictMap = new HashMap<>();
        for (String dictType : dictTypeSet) {
            dictMap.put(dictType, getDictName(dictType));
        }
        return CollUtil.isNotEmpty(dictTypeSet) ? dictMap : null;
    }

    /**
     * 字典名称组装
     *
     * @param dictType 字典类型
     * @return 返回字典名称
     */
    public static String getDictName(String dictType) {
        for (String removeName : GenConfig.getDictTypeRemove()) {
            if (dictType.startsWith(removeName))
                return StrUtil.format(GenConstants.DICT_NAME, StrUtil.convertToCamelCase(StrUtil.removePrefix(dictType, removeName)));
        }
        return StrUtil.format(GenConstants.DICT_NAME, StrUtil.convertToCamelCase(dictType));
    }

    /**
     * 获取数据表排除字段
     *
     * @param genTable 业务表对象
     * @return 返回字典组
     */
    public static Set<String> getExcludePropertyList(GenTableDto genTable) {
        String[] columnNames = genTable.getSubList().stream().map(GenTableColumnDto::getJavaField).toArray(String[]::new);
        if (genTable.isMerge())
            return new HashSet<>();
        Set<String> excludeList = new HashSet<>();
        if (ArrayUtil.isNotEmpty(GenConfig.getEntity().getBack().getBase())) {
            Set<String> filedSet = Arrays.stream(GenConfig.getEntity().getBack().getBase())
                    .filter(item -> !ArrayUtil.contains(columnNames, item)).map(item -> StrUtil.toUnderlineCase(item).toUpperCase()).collect(Collectors.toSet());
            if (CollUtil.isNotEmpty(filedSet))
                excludeList.addAll(filedSet);
        }
        if (genTable.isTree() && ArrayUtil.isNotEmpty(GenConfig.getEntity().getBack().getTree())) {
            Set<String> filedSet = Arrays.stream(GenConfig.getEntity().getBack().getTree())
                    .filter(item -> !ArrayUtil.contains(columnNames, item)).map(item -> StrUtil.toUnderlineCase(item).toUpperCase()).collect(Collectors.toSet());
            if (CollUtil.isNotEmpty(filedSet))
                excludeList.addAll(filedSet);
        }
        return excludeList;
    }

    /**
     * 生成前端index文件
     *
     * @param prefixPath    路径前缀
     * @param suffixFile    文件后缀
     * @param fePackageName 生成前端路径
     * @param businessName  生成业务名
     */
    public static void initIndexFile(String prefixPath, String suffixFile, String fePackageName, String businessName) {
        if (StrUtil.isBlank(prefixPath)) return;
        String url = StrUtil.replace(fePackageName, StrUtil.SLASH, File.separator);
        outIndexFile(prefixPath + File.separator + url + File.separator + "index.ts", StrUtil.format("export * from './{}{}'", businessName, suffixFile));
    }

    /**
     * 生成前端index文件
     *
     * @param path     文件地址
     * @param sentence 校验语句
     */
    public static void outIndexFile(String path, String sentence) {
        if (FileUtil.exist(path)) {
            FileReader fileReader = new FileReader(path);
            if (!StrUtil.contains(fileReader.readString(), sentence)) {
                FileWriter writer = new FileWriter(path);
                writer.append(sentence + ";\r\n");
            }
        } else {
            FileUtil.touch(path);
            FileWriter writer = new FileWriter(path);
            writer.write(sentence + ";\r\n");
        }
    }
}