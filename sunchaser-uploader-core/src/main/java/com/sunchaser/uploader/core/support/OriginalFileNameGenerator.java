package com.sunchaser.uploader.core.support;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/7/1
 */
public class OriginalFileNameGenerator extends AbstractFileNameGenerator {

    @Override
    protected String doGenerateFileName(MultipartFile multipartFile, String fileSuffix) throws Exception {
        return multipartFile.getOriginalFilename();
    }
}
