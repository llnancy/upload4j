package io.github.llnancy.upload4j.impl.local.config;

import io.github.llnancy.upload4j.api.config.Upload4jConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * local config
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2023/1/4
 */
@Getter
@Setter
public class LocalConfig extends Upload4jConfig {

    /**
     * 本地文件路径
     */
    private String localPath = "/uploader";
}
