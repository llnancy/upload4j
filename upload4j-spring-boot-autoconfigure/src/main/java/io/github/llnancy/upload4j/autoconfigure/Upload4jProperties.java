package io.github.llnancy.upload4j.autoconfigure;

import io.github.llnancy.upload4j.api.FileNameGenerator;
import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.Uploader;
import io.github.llnancy.upload4j.core.fn.OriginalFileNameGenerator;
import io.github.llnancy.upload4j.core.fu.SpecifyPathFileUriGenerator;
import io.github.llnancy.upload4j.impl.local.config.LocalConfig;
import io.github.llnancy.upload4j.impl.upyun.config.UpYunConfig;
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
@ConfigurationProperties(prefix = "llnancy.upload4j")
public class Upload4jProperties {

    private Class<? extends Uploader> uploaderClassName;

    private Class<? extends FileNameGenerator> fileNameGeneratorClassName = OriginalFileNameGenerator.class;

    private Class<? extends FileUriGenerator> fileUriGeneratorClassName = SpecifyPathFileUriGenerator.class;

    private String specifyPath;

    @NestedConfigurationProperty
    private final LocalConfig local = new LocalConfig();

    @NestedConfigurationProperty
    private final UpYunConfig upYun = new UpYunConfig();
}
