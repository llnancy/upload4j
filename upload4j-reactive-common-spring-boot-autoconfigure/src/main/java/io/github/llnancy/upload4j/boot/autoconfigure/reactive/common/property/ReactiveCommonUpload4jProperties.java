package io.github.llnancy.upload4j.boot.autoconfigure.reactive.common.property;

import io.github.llnancy.upload4j.boot.autoconfigure.base.property.BaseUpload4jProperties;
import io.github.llnancy.upload4j.reactive.api.ReactiveUploader;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReactiveCommonUpload4jProperties extends BaseUpload4jProperties {

    private Class<? extends ReactiveUploader> uploaderClassName;
}
