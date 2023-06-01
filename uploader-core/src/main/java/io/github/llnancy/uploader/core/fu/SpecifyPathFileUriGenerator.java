package io.github.llnancy.uploader.core.fu;

import io.github.llnancy.uploader.api.FileNameGenerator;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 指定路径
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
public class SpecifyPathFileUriGenerator extends AbstractFileUriGenerator {

    public static final String DEFAULT_SPECIFY_PATH = StringUtils.EMPTY;

    @Setter
    private String specifyPath;

    public SpecifyPathFileUriGenerator() {
        this(DEFAULT_SPECIFY_PATH);
    }

    public SpecifyPathFileUriGenerator(String specifyPath) {
        this(NativeServiceLoader.getNativeServiceLoader(FileNameGenerator.class).getDefaultNativeService(), specifyPath);
    }

    public SpecifyPathFileUriGenerator(FileNameGenerator fileNameGenerator, String specifyPath) {
        super(fileNameGenerator);
        specifyPath = specifyPath.startsWith("/") ? specifyPath : "/" + specifyPath;
        specifyPath = specifyPath.endsWith("/") ? StringUtils.chop(specifyPath) : specifyPath;
        this.specifyPath = specifyPath;
    }

    @Override
    protected String doGenerate(MultipartFile mf) {
        return StringUtils.isBlank(specifyPath) ? DEFAULT_SPECIFY_PATH : specifyPath;
    }
}
