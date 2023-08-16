package io.github.llnancy.upload4j.core.fn;

import org.springframework.web.multipart.MultipartFile;

/**
 * FileName: originalFileName
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/7/1
 */
public class OriginalFileNameGenerator extends AbstractFileNameGenerator {

    @Override
    protected String doGenerate(MultipartFile mf, String fileSuffix) {
        return mf.getOriginalFilename();
    }
}
