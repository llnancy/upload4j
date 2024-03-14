package io.github.llnancy.upload4j.boot.autoconfigure.base.property;

import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.FilenameGenerator;
import io.github.llnancy.upload4j.core.fn.OriginalFilenameGenerator;
import io.github.llnancy.upload4j.core.fu.SpecifyPathFileUriGenerator;
import lombok.Data;

/**
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/13
 */
@Data
public class BaseUpload4jProperties {

    private Class<? extends FilenameGenerator> fileNameGeneratorClassName = OriginalFilenameGenerator.class;

    private Class<? extends FileUriGenerator> fileUriGeneratorClassName = SpecifyPathFileUriGenerator.class;

    private String specifyPath;
}
