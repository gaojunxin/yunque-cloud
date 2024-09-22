package com.yunque.system.file.domain.po;

import com.yunque.common.core.web.tenant.base.TTreeEntity;
import com.yunque.system.file.domain.dto.SysFileFolderDto;
import com.yunque.common.core.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

import static com.yunque.common.core.constant.basic.EntityConstants.REMARK;

/**
 * 系统服务 | 素材模块 | 文件分类 持久化对象
 *
 * @author xueyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_file_folder", excludeProperty = { REMARK })
public class SysFileFolderPo extends TTreeEntity<SysFileFolderDto> {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 分类类型（0默认文件夹 1系统文件夹） */
    @Excel(name = "分类类型", readConverterExp = "0=默认文件夹,1=系统文件夹")
    protected String type;

}