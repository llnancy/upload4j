package io.github.llnancy.uploader.core.fu;

import io.github.llnancy.uploader.api.FileNameGenerator;
import io.github.llnancy.uploader.api.FileUriGenerator;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
    public String generate(MultipartFile mf) {
        String fileUri = doGenerate(mf);
        if (StringUtils.isNotBlank(fileUri)) {
            return fileUri + "/" + fileNameGenerator.generate(mf);
        } else {
            return fileNameGenerator.generate(mf);
        }
    }

    protected abstract String doGenerate(MultipartFile mf);
}
