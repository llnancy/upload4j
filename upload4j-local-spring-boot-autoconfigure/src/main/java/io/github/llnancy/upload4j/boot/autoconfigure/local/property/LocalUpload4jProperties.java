package io.github.llnancy.upload4j.boot.autoconfigure.local.property;

import io.github.llnancy.upload4j.boot.autoconfigure.common.property.CommonUpload4jProperties;
import io.github.llnancy.upload4j.impl.local.config.LocalConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 上传属性配置
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "llnancy.upload4j")
public class LocalUpload4jProperties extends CommonUpload4jProperties {

    @NestedConfigurationProperty
    private final LocalConfig local = new LocalConfig();
}
