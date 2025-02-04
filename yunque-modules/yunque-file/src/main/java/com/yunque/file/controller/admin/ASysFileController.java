package com.yunque.file.controller.admin;

import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.core.web.result.R;
import com.yunque.common.security.annotation.AdminAuth;
import com.yunque.file.api.domain.SysFile;
import com.yunque.file.controller.base.BSysFileController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务 | 文件管理 | 管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin")
public class ASysFileController extends BSysFileController {

    /**
     * 文件上传请求
     */
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file) {
        R<SysFile> R = uploadFile(file);
        return R.isOk()
                ? AjaxResult.success("上传成功！", R.getData().getUrl())
                : AjaxResult.error("上传失败！");
    }
}