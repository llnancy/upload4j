package io.github.llnancy.upload4j.boot.autoconfigure.reactive.upyun;

import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.boot.autoconfigure.base.Upload4jBaseAutoConfiguration;
import io.github.llnancy.upload4j.boot.autoconfigure.reactive.upyun.property.ReactiveUpYunUpload4jProperties;
import io.github.llnancy.upload4j.reactive.api.ReactiveUploader;
import io.github.llnancy.upload4j.reactive.impl.upyun.ReactiveUpYunUploaderImpl;
import io.github.nativegroup.spi.NativeServiceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Optional;

/**
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/14
 */
@Configuration
@EnableConfigurationProperties({ReactiveUpYunUpload4jProperties.class})
public class ReactiveUpYunUpload4jAutoConfiguration extends Upload4jBaseAutoConfiguration {

    @Bean
    @ConditionalOnClass(ReactiveUpYunUpload4jProperties.class)
    @DependsOn("fileUriGenerator")
    public ReactiveUpYunUploaderImpl reactiveUpYunUploader(ReactiveUpYunUpload4jProperties properties, FileUriGenerator fileUriGenerator) {
        ReactiveUploader reactiveUploader = NativeServiceLoader.getNativeServiceLoader(ReactiveUploader.class)
                .getOrDefaultNativeService(
                        Optional.ofNullable(properties.getUploaderClassName())
                                .map(Class::getName)
                                .orElse(null)
                );
        reactiveUploader.setFileUriGenerator(fileUriGenerator);
        // how to make the config more elegant?
        reactiveUploader.setConfig(properties.getUpYun());
        reactiveUploader.init();
        return (ReactiveUpYunUploaderImpl) reactiveUploader;
    }
}
