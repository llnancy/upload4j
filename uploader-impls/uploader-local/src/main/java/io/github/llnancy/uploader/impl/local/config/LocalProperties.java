package io.github.llnancy.uploader.impl.local.config;

import io.github.llnancy.uploader.core.config.UploaderProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Local Properties
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2023/1/4
 */
@Getter
@Setter
public class LocalProperties extends UploaderProperties {

    /**
     * 本地文件路径
     */
    private String localPath = "/uploader";
}
