package io.github.llnancy.uploader.core.fn;

import io.github.llnancy.mojian.base.util.IdUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileName: UUID.fileSuffix
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class UUIDFileNameGenerator extends AbstractFileNameGenerator {

    @Override
    protected String doGenerate(MultipartFile mf, String fileSuffix) {
        return IdUtils.simpleUUIDWithSuffix(fileSuffix);
    }
}
