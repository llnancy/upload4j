package io.github.llnancy.upload4j.core.fn;

import io.github.llnancy.upload4j.api.FileGeneratorContext;
import io.github.llnancy.upload4j.api.FilenameGenerator;

/**
 * FileName: originalFileName
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/7/1
 */
public class OriginalFilenameGenerator implements FilenameGenerator {

    @Override
    public String generate(FileGeneratorContext context) {
        return context.filename();
    }
}
