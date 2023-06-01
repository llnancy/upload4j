package io.github.llnancy.uploader.impl.local.config;

import io.github.llnancy.uploader.api.config.UploaderConfig;
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
public class LocalConfig extends UploaderConfig {

    /**
     * 本地文件路径
     */
    private String localPath = "/uploader";
}
