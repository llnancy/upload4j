package io.github.llnancy.uploader.core.fileuri.impl;

import io.github.llnancy.uploader.core.filename.FileNameGenerator;
import io.github.llnancy.uploader.core.filename.impl.OriginalFileNameGenerator;
import io.github.llnancy.uploader.core.fileuri.FileUriGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * an abstract {@link FileUriGenerator } implementation.
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2023/1/4
 */
@RequiredArgsConstructor
public abstract class AbstractFileUriGenerator implements FileUriGenerator {

    @Getter
    private final FileNameGenerator fileNameGenerator;

    public AbstractFileUriGenerator() {
        this.fileNameGenerator = new OriginalFileNameGenerator();
    }

    @Override
    public String generateFileUri(MultipartFile multipartFile) {
        return doGenerateFileUri(multipartFile) + "/" + fileNameGenerator.generateFileName(multipartFile);
    }

    protected abstract String doGenerateFileUri(MultipartFile multipartFile);
}
