package com.sunchaser.uploader.core.support;

import org.springframework.web.multipart.MultipartFile;

/**
 * 指定路径
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
public class SpecifyPathFileUriGenerator implements FileUriGenerator {

    private final String specifyPath;

    public SpecifyPathFileUriGenerator(String specifyPath) {
        this.specifyPath = specifyPath;
    }

    @Override
    public String generateFileUri(MultipartFile multipartFile, FileNameGenerator fileNameGenerator) throws Exception {
        return specifyPath + fileNameGenerator.generateFileName(multipartFile);
    }
}
