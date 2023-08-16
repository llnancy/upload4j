package io.github.llnancy.upload4j.autoconfigure;

import com.google.common.collect.Maps;
import io.github.llnancy.upload4j.api.FileNameGenerator;
import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.Uploader;
import io.github.llnancy.upload4j.core.fu.SpecifyPathFileUriGenerator;
import io.github.llnancy.upload4j.impl.local.LocalUploader;
import io.github.llnancy.upload4j.impl.upyun.UpYunUploader;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Map;
import java.util.Optional;

/**
 * upload4j auto config
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@Configuration
@EnableConfigurationProperties({Upload4jProperties.class})
public class Upload4jAutoConfiguration {

    @Bean
    @ConditionalOnClass(Upload4jProperties.class)
    public FileNameGenerator fileNameGenerator(Upload4jProperties properties) {
        return NativeServiceLoader.getNativeServiceLoader(FileNameGenerator.class)
                .getOrDefaultNativeService(
                        Optional.ofNullable(properties.getFileNameGeneratorClassName())
                                .map(Class::getName)
                                .orElse(null)
                );
    }

    @Bean
    @ConditionalOnClass(Upload4jProperties.class)
    @DependsOn("fileNameGenerator")
    public FileUriGenerator fileUriGenerator(Upload4jProperties properties, FileNameGenerator fileNameGenerator) {
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

    @Bean
    @ConditionalOnClass(Upload4jProperties.class)
    @DependsOn("fileUriGenerator")
    public Uploader uploader(Upload4jProperties properties, FileUriGenerator fileUriGenerator) {
        Uploader uploader = NativeServiceLoader.getNativeServiceLoader(Uploader.class)
                .getOrDefaultNativeService(
                        Optional.ofNullable(properties.getUploaderClassName())
                                .map(Class::getName)
                                .orElse(null)
                );
        uploader.setFileUriGenerator(fileUriGenerator);
        // how to make the config more elegant?
        UploaderEnum.setConfig(uploader, properties);
        uploader.init();
        return uploader;
    }

    @AllArgsConstructor
    enum UploaderEnum {

        LOCAL(LocalUploader.class) {
            @Override
            void doSetConfig(Uploader uploader, Upload4jProperties properties) {
                uploader.setConfig(properties.getLocal());
            }
        },

        UP_YUN(UpYunUploader.class) {
            @Override
            void doSetConfig(Uploader uploader, Upload4jProperties properties) {
                uploader.setConfig(properties.getUpYun());
            }
        };

        private final Class<?> clazz;

        abstract void doSetConfig(Uploader uploader, Upload4jProperties properties);

        private static final Map<Class<?>, UploaderEnum> ENUM_MAP = Maps.newHashMap();

        static {
            for (UploaderEnum uploaderEnum : UploaderEnum.values()) {
                ENUM_MAP.put(uploaderEnum.clazz, uploaderEnum);
            }
        }

        public static void setConfig(Uploader uploader, Upload4jProperties properties) {
            Optional.ofNullable(ENUM_MAP.get(uploader.getClass()))
                    .ifPresent(em -> em.doSetConfig(uploader, properties));
        }
    }
}
