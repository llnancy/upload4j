package io.github.llnancy.upload4j.boot.autoconfigure.base;

import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.FilenameGenerator;
import io.github.llnancy.upload4j.boot.autoconfigure.base.property.BaseUpload4jProperties;
import io.github.llnancy.upload4j.core.fu.SpecifyPathFileUriGenerator;
import io.github.nativegroup.spi.NativeServiceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.Optional;

/**
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/13
 */
public class Upload4jBaseAutoConfiguration {

    @Bean
    @ConditionalOnClass(BaseUpload4jProperties.class)
    public FilenameGenerator fileNameGenerator(BaseUpload4jProperties properties) {
        return NativeServiceLoader.getNativeServiceLoader(FilenameGenerator.class)
                .getOrDefaultNativeService(
                        Optional.ofNullable(properties.getFileNameGeneratorClassName())
                                .map(Class::getName)
                                .orElse(null)
                );
    }

    @Bean
    @ConditionalOnClass(BaseUpload4jProperties.class)
    @DependsOn("fileNameGenerator")
    public FileUriGenerator fileUriGenerator(BaseUpload4jProperties properties, FilenameGenerator fileNameGenerator) {
        FileUriGenerator fileUriGenerator = NativeServiceLoader.getNativeServiceLoader(FileUriGenerator.class)
                .getOrDefaultNativeService(
                        Optional.ofNullable(properties.getFileUriGeneratorClassName())
                                .map(Class::getName)
                                .orElse(null)
                );
        fileUriGenerator.setFileNameGenerator(fileNameGenerator);
        if (fileUriGenerator.getClass() == SpecifyPathFileUriGenerator.class) {
            SpecifyPathFileUriGenerator generator = (SpecifyPathFileUriGenerator) fileUriGenerator;
            generator.setSpecifyPath(properties.getSpecifyPath());
            return generator;
        }
        return fileUriGenerator;
    }
}
