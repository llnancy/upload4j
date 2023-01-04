package io.github.llnancy.uploader.core.filename.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrPool;
import io.github.llnancy.uploader.core.filename.FileNameGenerator;
import org.springframework.web.multipart.MultipartFile;

/**
 * an abstract {@link FileNameGenerator } implementation.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public abstract class AbstractFileNameGenerator implements FileNameGenerator {

    @Override
    public String generateFileName(MultipartFile multipartFile) {
        String fileSuffix = StrPool.DOT + FileUtil.extName(multipartFile.getOriginalFilename());
        return doGenerateFileName(multipartFile, fileSuffix);
    }

    protected abstract String doGenerateFileName(MultipartFile multipartFile, String fileSuffix);
}
