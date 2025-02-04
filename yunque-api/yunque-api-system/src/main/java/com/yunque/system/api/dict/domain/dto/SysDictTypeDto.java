package com.yunque.system.api.dict.domain.dto;

import com.yunque.system.api.dict.domain.po.SysDictTypePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * 系统服务 | 字典模块 | 字典类型 数据传输对象
 *
 * @author xueyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictTypeDto extends SysDictTypePo {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 字典数据 */
    private List<SysDictDataDto> subList;

}
