package com.yunque.file.controller.inner;

import com.yunque.common.core.web.result.R;
import com.yunque.common.security.annotation.InnerAuth;
import com.yunque.file.api.domain.SysFile;
import com.yunque.file.controller.base.BSysFileController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务 | 文件管理 | 内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner")
public class ISysFileController extends BSysFileController {

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public R<SysFile> uploadInner(MultipartFile file) {
        return uploadFile(file);
    }

    /**
     * 删除文件
     */
    @DeleteMapping(value = "/delete")
    public R<Boolean> deleteInner(@RequestParam String url) {
        return deleteFile(url);
    }
}