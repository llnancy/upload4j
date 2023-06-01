package io.github.llnancy.uploader.autoconfigure;

import io.github.llnancy.uploader.api.FileNameGenerator;
import io.github.llnancy.uploader.api.FileUriGenerator;
import io.github.llnancy.uploader.api.Uploader;
import io.github.llnancy.uploader.impl.local.config.LocalConfig;
import io.github.llnancy.uploader.impl.upyun.config.UpYunConfig;
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

    private Class<? extends Uploader> uploaderClassName;

    private Class<? extends FileNameGenerator> fileNameGeneratorClassName;

    private Class<? extends FileUriGenerator> fileUriGeneratorClassName;

    private String specifyPath;

    @NestedConfigurationProperty
    private final LocalConfig local = new LocalConfig();

    @NestedConfigurationProperty
    private final UpYunConfig upYun = new UpYunConfig();
}
