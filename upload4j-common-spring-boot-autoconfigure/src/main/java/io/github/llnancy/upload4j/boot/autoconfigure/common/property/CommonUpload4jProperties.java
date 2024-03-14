package io.github.llnancy.upload4j.boot.autoconfigure.common.property;

import io.github.llnancy.upload4j.api.Uploader;
import io.github.llnancy.upload4j.boot.autoconfigure.base.property.BaseUpload4jProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommonUpload4jProperties extends BaseUpload4jProperties {

    private Class<? extends Uploader> uploaderClassName;
}
