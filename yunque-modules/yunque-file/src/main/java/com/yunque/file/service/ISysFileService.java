package com.yunque.file.service;

import com.yunque.file.api.domain.SysFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 *
 * @author xueyi
 */
public interface ISysFileService {

    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    SysFile uploadFile(MultipartFile file) throws Exception;

    /**
     * 文件删除接口
     *
     * @param url 文件地址
     * @return 结果
     */
    Boolean deleteFile(String url) throws Exception;
}
