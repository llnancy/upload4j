package io.github.llnancy.upload4j.core.fn;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrPool;
import io.github.llnancy.upload4j.api.FileNameGenerator;
import org.springframework.web.multipart.MultipartFile;

/**
 * an abstract {@link FileNameGenerator } implementation.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public abstract class AbstractFileNameGenerator implements FileNameGenerator {

    @Override
    public String generate(MultipartFile mf) {
        String fileSuffix = StrPool.DOT + FileUtil.extName(mf.getOriginalFilename());
        return doGenerate(mf, fileSuffix);
    }

    protected abstract String doGenerate(MultipartFile mf, String fileSuffix);
}
