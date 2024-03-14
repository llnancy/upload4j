package io.github.llnancy.upload4j.boot.autoconfigure.upyun;

import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.Uploader;
import io.github.llnancy.upload4j.boot.autoconfigure.base.Upload4jBaseAutoConfiguration;
import io.github.llnancy.upload4j.boot.autoconfigure.upyun.property.UpYunUpload4jProperties;
import io.github.llnancy.upload4j.impl.upyun.UpYunUploaderImpl;
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
@EnableConfigurationProperties({UpYunUpload4jProperties.class})
public class UpYunUpload4jAutoConfiguration extends Upload4jBaseAutoConfiguration {

    @Bean
    @ConditionalOnClass(UpYunUpload4jProperties.class)
    @DependsOn("fileUriGenerator")
    public UpYunUploaderImpl localUploader(UpYunUpload4jProperties properties, FileUriGenerator fileUriGenerator) {
        Uploader uploader = NativeServiceLoader.getNativeServiceLoader(Uploader.class)
                .getOrDefaultNativeService(
                        Optional.ofNullable(properties.getUploaderClassName())
                                .map(Class::getName)
                                .orElse(null)
                );
        uploader.setFileUriGenerator(fileUriGenerator);
        // how to make the config more elegant?
        uploader.setConfig(properties.getUpYun());
        uploader.init();
        return (UpYunUploaderImpl) uploader;
    }
}
