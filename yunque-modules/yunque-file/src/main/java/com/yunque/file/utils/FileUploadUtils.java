package com.yunque.file.utils;

import com.yunque.common.core.exception.file.FileException;
import com.yunque.common.core.exception.file.FileNameLengthLimitExceededException;
import com.yunque.common.core.exception.file.FileSizeLimitExceededException;
import com.yunque.common.core.exception.file.InvalidExtensionException;
import com.yunque.common.core.utils.DateUtil;
import com.yunque.common.core.utils.core.IdUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.utils.file.FileTypeUtil;
import com.yunque.common.core.utils.file.MimeTypeUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 文件上传工具类
 *
 * @author xueyi
 */
public class FileUploadUtils {

    /** 默认大小 50M */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /** 默认的文件名最大长度 100 */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     */
    public static String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtil.DEFAULT_ALLOWED_EXTENSION);
        } catch (FileException fe) {
            throw new IOException(fe.getDefaultMessage(), fe);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     * @throws InvalidExtensionException            文件校验异常
     */
    public static String upload(String baseDir, MultipartFile file, String[] allowedExtension) throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException, InvalidExtensionException {
        int fileNameLength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        return getPathFileName(fileName);
    }

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file) {
        return StrUtil.format("{}/{}_{}.{}", DateUtil.datePath(), FilenameUtils.getBaseName(file.getOriginalFilename()), IdUtil.simpleUUID(), FileTypeUtil.getExtension(file));
    }

    private static File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc.isAbsolute() ? desc : desc.getAbsoluteFile();
    }

    private static String getPathFileName(String fileName) {
        return StrUtil.SLASH + fileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException      文件校验异常
     */
    public static void assertAllowed(MultipartFile file, String[] allowedExtension) throws FileSizeLimitExceededException, InvalidExtensionException {
        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = FileTypeUtil.getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtil.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension, fileName);
            } else if (allowedExtension == MimeTypeUtil.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension, fileName);
            } else if (allowedExtension == MimeTypeUtil.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension, fileName);
            } else if (allowedExtension == MimeTypeUtil.VIDEO_EXTENSION) {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension, fileName);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }

    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension        上传文件类型
     * @param allowedExtension 允许上传文件类型
     * @return true/false
     */
    public static boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}