package io.github.llnancy.upload4j.core.fn;

import io.github.llnancy.mojian.base.util.IdUtils;
import io.github.llnancy.upload4j.api.FileGeneratorContext;

/**
 * FileName: UUID.fileSuffix
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class UUIDFileNameGenerator extends AbstractFileNameGenerator {

    @Override
    protected String doGenerate(FileGeneratorContext context, String fileSuffix) {
        return IdUtils.simpleUUIDWithSuffix(fileSuffix);
    }
}
