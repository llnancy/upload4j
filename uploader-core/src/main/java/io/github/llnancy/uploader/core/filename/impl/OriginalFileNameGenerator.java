package io.github.llnancy.uploader.core.filename.impl;

import org.springframework.web.multipart.MultipartFile;

/**
 * FileName: originalFileName
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/7/1
 */
public class OriginalFileNameGenerator extends AbstractFileNameGenerator {

    @Override
    protected String doGenerateFileName(MultipartFile multipartFile, String fileSuffix) {
        return multipartFile.getOriginalFilename();
    }
}
