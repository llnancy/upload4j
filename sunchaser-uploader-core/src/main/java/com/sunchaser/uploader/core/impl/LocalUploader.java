package com.sunchaser.uploader.core.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 本地文件上传器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
public class LocalUploader extends AbstractUploader {

    private final String localPath;

    public static final String DEFAULT_LOCAL_PATH = "/sunchaser-uploader";

    public LocalUploader(String localPath) {
        this.localPath = StrUtil.isBlank(localPath) ? DEFAULT_LOCAL_PATH : localPath;
    }

    @Override
    protected String doUpload(MultipartFile multipartFile, String fileUri) throws Exception {
        String targetUri = localPath + fileUri;
        FileUtil.writeFromStream(multipartFile.getInputStream(), targetUri);
        return targetUri;
    }
}
