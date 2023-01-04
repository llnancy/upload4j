package com.sunchaser.uploader.autoconfigure;

import io.github.llnancy.uploader.impl.local.config.LocalProperties;
import io.github.llnancy.uploader.impl.upyun.config.UpYunProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 上传属性配置
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@Data
@ConfigurationProperties(prefix = "llnancy.uploader")
public class UploaderProperties {

    private String uploaderType;

    private String fileNameGeneratorType;

    private String fileUriGeneratorType;

    private String specifyPath;

    @NestedConfigurationProperty
    private final LocalProperties local = new LocalProperties();

    @NestedConfigurationProperty
    private final UpYunProperties upYun = new UpYunProperties();
}
