package com.yunque.file.service.impl;

import com.alibaba.nacos.common.utils.IoUtils;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.utils.file.FileTypeUtil;
import com.yunque.common.core.utils.file.FileUtil;
import com.yunque.file.api.domain.SysFile;
import com.yunque.file.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * FastDFS 文件存储
 *
 * @author xueyi
 */
@Service
public class FastDfsSysFileServiceImpl implements ISysFileService {

    /**
     * 域名或本机访问地址
     */
    @Value("${fdfs.domain}")
    public String domain;

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * FastDfs文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public SysFile uploadFile(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        StorePath storePath = storageClient.uploadFile(inputStream, file.getSize(),
                FileTypeUtil.getExtension(file), null);
        IoUtils.closeQuietly(inputStream);
        SysFile sysFile = new SysFile();
        sysFile.setUrl(domain + StrUtil.SLASH + storePath.getFullPath());
        sysFile.setPath(storePath.getFullPath());
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
        return true;
    }
}
