package com.yunque.file.api.feign;

import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.constant.basic.ServiceConstants;
import com.yunque.common.core.web.result.R;
import com.yunque.file.api.domain.SysFile;
import com.yunque.file.api.feign.factory.RemoteFileManageFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件管理服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteFileManageService", path = "/inner/file", value = ServiceConstants.SYSTEM_SERVICE, fallbackFactory = RemoteFileManageFallbackFactory.class)
public interface RemoteFileManageService {

    /**
     * 保存文件记录
     *
     * @param file 文件实体
     * @return 结果
     */
    @PostMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> saveFile(@RequestBody SysFile file);

    /**
     * 保存文件记录
     *
     * @param url 文件路径
     * @return 结果
     */
    @DeleteMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> delFile(@RequestParam("url") String url);
}