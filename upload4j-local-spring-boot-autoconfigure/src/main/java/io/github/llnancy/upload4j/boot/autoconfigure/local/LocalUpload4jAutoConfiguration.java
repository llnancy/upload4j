package io.github.llnancy.upload4j.boot.autoconfigure.local;

import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.Uploader;
import io.github.llnancy.upload4j.boot.autoconfigure.base.Upload4jBaseAutoConfiguration;
import io.github.llnancy.upload4j.boot.autoconfigure.local.property.LocalUpload4jProperties;
import io.github.llnancy.upload4j.impl.local.LocalUploaderImpl;
import io.github.nativegroup.spi.NativeServiceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Optional;

/**
 * upload4j auto config
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@Configuration
@EnableConfigurationProperties({LocalUpload4jProperties.class})
public class LocalUpload4jAutoConfiguration extends Upload4jBaseAutoConfiguration {

    @Bean
    @ConditionalOnClass(LocalUpload4jProperties.class)
    @DependsOn("fileUriGenerator")
    public LocalUploaderImpl localUploader(LocalUpload4jProperties properties, FileUriGenerator fileUriGenerator) {
        Uploader uploader = NativeServiceLoader.getNativeServiceLoader(Uploader.class)
                .getOrDefaultNativeService(
                        Optional.ofNullable(properties.getUploaderClassName())
                                .map(Class::getName)
                                .orElse(null)
                );
        uploader.setFileUriGenerator(fileUriGenerator);
        // how to make the config more elegant?
        uploader.setConfig(properties.getLocal());
        uploader.init();
        return (LocalUploaderImpl) uploader;
    }
}
