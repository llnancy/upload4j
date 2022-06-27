package com.sunchaser.uploader.autoconfigure;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.sunchaser.uploader.core.Uploader;
import com.sunchaser.uploader.core.impl.LocalUploader;
import com.sunchaser.uploader.core.impl.upyun.UpYunUploader;
import com.sunchaser.uploader.core.support.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@Configuration
@EnableConfigurationProperties({UploaderProperties.class})
public class UploaderAutoConfiguration {

    @Bean
    @ConditionalOnClass(UploaderProperties.class)
    public FileNameGenerator fileNameGenerator(UploaderProperties properties) {
        return FileNameGeneratorEnum.match(properties.getFileNameGeneratorType());
    }

    @Bean
    @ConditionalOnClass(UploaderProperties.class)
    public FileUriGenerator fileUriGenerator(UploaderProperties properties) {
        return FileUriGeneratorEnum.match(properties);
    }

    @Bean
    public Uploader uploader(UploaderProperties properties) {
        return UploaderEnum.match(properties);
    }

    @AllArgsConstructor
    @Getter
    enum FileNameGeneratorEnum {

        DEFAULT("UUID", DefaultFileNameGenerator::new)
        ;

        private final String key;

        private final Supplier<FileNameGenerator> supplier;

        private static final Map<String, FileNameGeneratorEnum> enumMap = Maps.newHashMap();

        static {
            for (FileNameGeneratorEnum fileNameGeneratorEnum : FileNameGeneratorEnum.values()) {
                enumMap.put(fileNameGeneratorEnum.key, fileNameGeneratorEnum);
            }
        }

        public static FileNameGenerator match(String key) {
            return Optional.ofNullable(enumMap.get(key)).orElse(DEFAULT).supplier.get();
        }
    }

    @AllArgsConstructor
    @Getter
    enum FileUriGeneratorEnum {

        LOCAL_DATE("LOCAL_DATE") {

            @Override
            FileUriGenerator impl(UploaderProperties properties) {
                return new LocalDateFileUriGenerator();
            }
        },

        SPECIFY_PATH("SPECIFY_PATH") {

            @Override
            FileUriGenerator impl(UploaderProperties properties) {
                return new SpecifyPathFileUriGenerator(properties.getSpecifyPath());
            }
        },
        ;

        private final String key;

        abstract FileUriGenerator impl(UploaderProperties properties);

        private static final Map<String, FileUriGeneratorEnum> enumMap = Maps.newHashMap();

        static {
            for (FileUriGeneratorEnum fileUriGeneratorEnum : FileUriGeneratorEnum.values()) {
                enumMap.put(fileUriGeneratorEnum.key, fileUriGeneratorEnum);
            }
        }

        public static FileUriGenerator match(UploaderProperties properties) {
            return Optional.ofNullable(enumMap.get(properties.getFileUriGeneratorType()))
                    .orElse(LOCAL_DATE)
                    .impl(properties);
        }
    }

    @AllArgsConstructor
    @Getter
    enum UploaderEnum {

        LOCAL("LOCAL") {

            @Override
            Uploader impl(UploaderProperties properties) {
                return new LocalUploader(properties.getLocal().getLocalPath());
            }
        },

        UP_YUN("UP_YUN") {

            @Override
            Uploader impl(UploaderProperties properties) {
                UploaderProperties.UpYun upYun = properties.getUpYun();
                String bucketName = upYun.getBucketName();
                String userName = upYun.getUserName();
                String password = upYun.getPassword();
                UpYunUploader upYunUploader = new UpYunUploader(bucketName, userName, password);
                Integer timeout = upYun.getTimeout();
                String speedDomain = upYun.getSpeedDomain();
                String apiDomain = upYun.getApiDomain();
                if (Objects.nonNull(timeout)) {
                    upYunUploader.setTimeout(timeout);
                }
                if (StrUtil.isNotBlank(speedDomain)) {
                    upYunUploader.setSpeedDomain(speedDomain);
                }
                if (StrUtil.isNotBlank(apiDomain)) {
                    upYunUploader.setApiDomain(apiDomain);
                }
                return upYunUploader;
            }
        }

        ;
        private final String key;

        abstract Uploader impl(UploaderProperties properties);

        private static final Map<String, UploaderEnum> enumMap = Maps.newHashMap();

        static {
            for (UploaderEnum uploaderEnum : UploaderEnum.values()) {
                enumMap.put(uploaderEnum.key, uploaderEnum);
            }
        }

        public static Uploader match(UploaderProperties properties) {
            return Optional.ofNullable(enumMap.get(properties.getUploaderType()))
                    .orElse(LOCAL)
                    .impl(properties);
        }
    }
}
