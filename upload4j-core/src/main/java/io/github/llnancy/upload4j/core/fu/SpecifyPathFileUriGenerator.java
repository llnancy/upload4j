package io.github.llnancy.upload4j.core.fu;

import io.github.llnancy.upload4j.api.FileGeneratorContext;
import io.github.llnancy.upload4j.api.FilenameGenerator;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 指定路径
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@Getter
public class SpecifyPathFileUriGenerator extends AbstractFileUriGenerator {

    public static final String DEFAULT_SPECIFY_PATH = StringUtils.EMPTY;

    @Setter
    private String specifyPath;

    public SpecifyPathFileUriGenerator() {
        this(DEFAULT_SPECIFY_PATH);
    }

    public SpecifyPathFileUriGenerator(String specifyPath) {
        this(NativeServiceLoader.getNativeServiceLoader(FilenameGenerator.class).getDefaultNativeService(), specifyPath);
    }

    public SpecifyPathFileUriGenerator(FilenameGenerator fileNameGenerator, String specifyPath) {
        super(fileNameGenerator);
        specifyPath = specifyPath.startsWith("/") ? StringUtils.removeStart(specifyPath, "/") : specifyPath;
        specifyPath = specifyPath.endsWith("/") ? StringUtils.chop(specifyPath) : specifyPath;
        this.specifyPath = specifyPath;
    }

    @Override
    protected String doGenerate(FileGeneratorContext context) {
        // eg. path/to
        return StringUtils.isBlank(specifyPath) ? DEFAULT_SPECIFY_PATH : specifyPath;
    }
}
