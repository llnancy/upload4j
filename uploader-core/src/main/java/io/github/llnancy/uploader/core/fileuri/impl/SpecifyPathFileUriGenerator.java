package io.github.llnancy.uploader.core.fileuri.impl;

import io.github.llnancy.uploader.core.filename.FileNameGenerator;
import io.github.llnancy.uploader.core.filename.impl.OriginalFileNameGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 指定路径
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
public class SpecifyPathFileUriGenerator extends AbstractFileUriGenerator {

    public static final String DEFAULT_SPECIFY_PATH = StringUtils.EMPTY;

    private final String specifyPath;

    public SpecifyPathFileUriGenerator() {
        this(DEFAULT_SPECIFY_PATH);
    }

    public SpecifyPathFileUriGenerator(String specifyPath) {
        this(new OriginalFileNameGenerator(), specifyPath);
    }

    public SpecifyPathFileUriGenerator(FileNameGenerator fileNameGenerator, String specifyPath) {
        super(fileNameGenerator);
        this.specifyPath = specifyPath.endsWith("/") ? StringUtils.chop(specifyPath) : specifyPath;
    }

    @Override
    protected String doGenerateFileUri(MultipartFile multipartFile) {
        return StringUtils.isBlank(specifyPath) ? DEFAULT_SPECIFY_PATH : specifyPath;
    }
}
