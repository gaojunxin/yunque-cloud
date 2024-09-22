package com.yunque.system.file.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yunque.common.web.entity.manager.impl.BaseManagerImpl;
import com.yunque.system.file.domain.dto.SysFileDto;
import com.yunque.system.file.domain.model.SysFileConverter;
import com.yunque.system.file.domain.po.SysFilePo;
import com.yunque.system.file.domain.query.SysFileQuery;
import com.yunque.system.file.manager.ISysFileManager;
import com.yunque.system.file.mapper.SysFileMapper;
import org.springframework.stereotype.Component;

/**
 * 系统服务 | 素材模块 | 文件管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysFileManagerImpl extends BaseManagerImpl<SysFileQuery, SysFileDto, SysFilePo, SysFileMapper, SysFileConverter> implements ISysFileManager {

    /**
     * 根据文件Url删除文件
     *
     * @param url 文件路径
     * @return 结果
     */
    @Override
    public int deleteByUrl(String url) {
        return baseMapper.delete(Wrappers.<SysFilePo>lambdaUpdate()
                .eq(SysFilePo::getUrl, url));
    }
}