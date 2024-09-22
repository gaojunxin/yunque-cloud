package com.yunque.system.file.controller.inner;

import com.yunque.common.core.web.result.R;
import com.yunque.common.security.annotation.InnerAuth;
import com.yunque.file.api.domain.SysFile;
import com.yunque.system.file.controller.base.BSysFileController;
import com.yunque.system.file.domain.dto.SysFileDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务 | 素材模块 | 文件管理 | 内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/file")
public class ISysFileController extends BSysFileController {

    /**
     * 保存文件记录
     */
    @PostMapping
    public R<Boolean> saveFile(@RequestBody SysFile file) {
        return R.success(baseService.insert(SysFileDto.copyFile(file)));
    }

    /**
     * 删除文件
     */
    @DeleteMapping
    public  R<Boolean> deleteFile(@RequestParam String url) {
        return R.success(baseService.deleteByUrl(url));
    }
}
