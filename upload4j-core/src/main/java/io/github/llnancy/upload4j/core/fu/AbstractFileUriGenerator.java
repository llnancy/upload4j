package io.github.llnancy.upload4j.core.fu;

import io.github.llnancy.upload4j.api.FileGeneratorContext;
import io.github.llnancy.upload4j.api.FileNameGenerator;
import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * an abstract {@link FileUriGenerator } implementation.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2023/1/4
 */
@Setter
public abstract class AbstractFileUriGenerator implements FileUriGenerator {

    private FileNameGenerator fileNameGenerator;

    public AbstractFileUriGenerator() {
        this.fileNameGenerator = NativeServiceLoader.getNativeServiceLoader(FileNameGenerator.class).getDefaultNativeService();
    }

    public AbstractFileUriGenerator(FileNameGenerator fileNameGenerator) {
        this.fileNameGenerator = fileNameGenerator;
    }

    @Override
    public String generate(FileGeneratorContext context) {
        String fileUri = doGenerate(context);
        if (StringUtils.isNotBlank(fileUri)) {
            return fileUri + "/" + fileNameGenerator.generate(context);
        } else {
            return "/" + fileNameGenerator.generate(context);
        }
    }

    protected abstract String doGenerate(FileGeneratorContext context);
}
