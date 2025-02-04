package com.yunque.file.service.impl;

import com.yunque.common.core.utils.file.FileUtil;
import com.yunque.file.api.domain.SysFile;
import com.yunque.file.service.ISysFileService;
import com.yunque.file.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 本地文件存储
 *
 * @author xueyi
 */
@Primary
@Service
public class LocalSysFileServiceImpl implements ISysFileService {

    /** 资源映射路径 前缀 */
    @Value("${file.prefix}")
    public String localFilePrefix;

    /** 域名或本机访问地址 */
    @Value("${file.domain}")
    public String domain;

    /** 上传文件存储在本地的根路径 */
    @Value("${file.path}")
    private String localFilePath;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    @Override
    public SysFile uploadFile(MultipartFile file) throws Exception {
        String name = FileUploadUtils.upload(localFilePath, file);
        SysFile sysFile = new SysFile();
        sysFile.setUrl(domain + localFilePrefix + name);
        sysFile.setPath(name);
        sysFile.setSize(file.getSize());
        sysFile.setName(FileUtil.getName(sysFile.getUrl()));
        sysFile.setNick(sysFile.getName());
        return sysFile;
    }

    /**
     * 文件删除接口
     *
     * @param url 文件地址
     * @return 结果
     */
    public Boolean deleteFile(String url) throws Exception {
        String localPath = url.replace(domain + localFilePrefix, localFilePath);
        File file = new File(localPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }
}
