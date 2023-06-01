package io.github.llnancy.uploader.autoconfigure;

import com.google.common.collect.Maps;
import io.github.llnancy.uploader.api.FileNameGenerator;
import io.github.llnancy.uploader.api.FileUriGenerator;
import io.github.llnancy.uploader.api.Uploader;
import io.github.llnancy.uploader.core.fu.SpecifyPathFileUriGenerator;
import io.github.llnancy.uploader.impl.local.LocalUploader;
import io.github.llnancy.uploader.impl.upyun.UpYunUploader;
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
 * uploader auto config
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@Configuration
@EnableConfigurationProperties({UploaderProperties.class})
public class UploaderAutoConfiguration {

    @Bean
    @ConditionalOnClass(UploaderProperties.class)
    public FileNameGenerator fileNameGenerator(UploaderProperties properties) {
        return NativeServiceLoader.getNativeServiceLoader(FileNameGenerator.class).getOrDefaultNativeService(properties.getFileNameGeneratorClassName().getName());
    }

    @Bean
    @ConditionalOnClass(UploaderProperties.class)
    @DependsOn("fileNameGenerator")
    public FileUriGenerator fileUriGenerator(UploaderProperties properties, FileNameGenerator fileNameGenerator) {
        FileUriGenerator fileUriGenerator = NativeServiceLoader.getNativeServiceLoader(FileUriGenerator.class).getOrDefaultNativeService(properties.getFileUriGeneratorClassName().getName());
        fileUriGenerator.setFileNameGenerator(fileNameGenerator);
        if (fileUriGenerator.getClass() == SpecifyPathFileUriGenerator.class) {
            SpecifyPathFileUriGenerator generator = (SpecifyPathFileUriGenerator) fileUriGenerator;
            generator.setSpecifyPath(properties.getSpecifyPath());
            return generator;
        }
        return fileUriGenerator;
    }

    @Bean
    @ConditionalOnClass(UploaderProperties.class)
    @DependsOn("fileUriGenerator")
    public Uploader uploader(UploaderProperties properties, FileUriGenerator fileUriGenerator) {
        Uploader uploader = NativeServiceLoader.getNativeServiceLoader(Uploader.class).getOrDefaultNativeService(properties.getUploaderClassName().getName());
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
            void doSetConfig(Uploader uploader, UploaderProperties properties) {
                uploader.setConfig(properties.getLocal());
            }
        },

        UP_YUN(UpYunUploader.class) {
            @Override
            void doSetConfig(Uploader uploader, UploaderProperties properties) {
                uploader.setConfig(properties.getUpYun());
            }
        };

        private final Class<?> clazz;

        abstract void doSetConfig(Uploader uploader, UploaderProperties properties);

        private static final Map<Class<?>, UploaderEnum> ENUM_MAP = Maps.newHashMap();

        static {
            for (UploaderEnum uploaderEnum : UploaderEnum.values()) {
                ENUM_MAP.put(uploaderEnum.clazz, uploaderEnum);
            }
        }

        public static void setConfig(Uploader uploader, UploaderProperties properties) {
            Optional.ofNullable(ENUM_MAP.get(uploader.getClass()))
                    .ifPresent(em -> em.doSetConfig(uploader, properties));
        }
    }
}
