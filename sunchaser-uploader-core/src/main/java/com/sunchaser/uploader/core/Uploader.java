package com.sunchaser.uploader.core;

import org.springframework.web.multipart.MultipartFile;

/**
 * Uploader 接口
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
public interface Uploader {

    String upload(MultipartFile multipartFile) throws Exception;

    default FileTypeEnum getFileTypeEnum() {
        return FileTypeEnum.ALL;
    }
}
