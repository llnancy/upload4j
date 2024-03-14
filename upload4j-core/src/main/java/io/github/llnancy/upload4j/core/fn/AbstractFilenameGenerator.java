package io.github.llnancy.upload4j.core.fn;

import cn.hutool.core.text.StrPool;
import io.github.llnancy.upload4j.api.FileGeneratorContext;
import io.github.llnancy.upload4j.api.FilenameGenerator;
import io.github.llnancy.upload4j.api.util.FileUtils;

/**
 * an abstract {@link FilenameGenerator } implementation.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public abstract class AbstractFilenameGenerator implements FilenameGenerator {

    @Override
    public String generate(FileGeneratorContext context) {
        String fileSuffix = StrPool.DOT + FileUtils.getFileExtension(context.filename());
        return doGenerate(context, fileSuffix);
    }

    protected abstract String doGenerate(FileGeneratorContext context, String fileSuffix);
}
