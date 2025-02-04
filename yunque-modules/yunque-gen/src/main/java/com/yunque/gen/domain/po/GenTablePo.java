package com.yunque.gen.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.yunque.common.core.web.entity.base.BaseEntity;
import com.yunque.gen.domain.dto.GenTableOptionDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.yunque.common.core.constant.basic.EntityConstants.DEL_FLAG;
import static com.yunque.common.core.constant.basic.EntityConstants.SORT;
import static com.yunque.common.core.constant.basic.EntityConstants.STATUS;


/**
 * 业务 持久化对象
 *
 * @author xueyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "gen_table", excludeProperty = {STATUS, SORT, DEL_FLAG}, autoResultMap = true)
public class GenTablePo extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 表描述 */
    @NotBlank(message = "表描述不能为空")
    @TableField(condition = LIKE)
    protected String comment;

    /** 实体类名称(首字母大写) */
    @NotBlank(message = "实体类名称不能为空")
    protected String className;

    /** 实体类名称前缀(首字母大写) */
    protected String prefix;

    /** 使用的模板（base单表操作 tree树表操作） */
    protected String tplCategory;

    /** 生成后端包路径 */
    @NotBlank(message = "生成后端包路径不能为空")
    protected String rdPackageName;

    /** 生成权限名 */
    @NotBlank(message = "生成前端包路径不能为空")
    protected String fePackageName;

    /** 生成模块路径 */
    @NotBlank(message = "生成模块路径不能为空")
    protected String moduleName;

    /** 生成业务名 */
    @NotBlank(message = "生成业务名不能为空")
    protected String businessName;

    /** 生成权限标识 */
    @NotBlank(message = "生成权限标识不能为空")
    protected String authorityName;

    /** 生成功能名 */
    @NotBlank(message = "生成功能名不能为空")
    protected String functionName;

    /** 生成作者 */
    @NotBlank(message = "作者不能为空")
    protected String functionAuthor;

    /** 生成路径类型（0默认路径 1自定义路径） */
    protected String genType;

    /** 后端生成路径（不填默认项目路径） */
    protected String genPath;

    /** 前端生成路径（不填默认项目路径） */
    protected String uiPath;

    /** 其它生成选项 */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    protected GenTableOptionDto options;

}