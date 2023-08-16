package io.github.llnancy.upload4j.api;

import io.github.nativegroup.spi.SPI;

/**
 * 文件名生成器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@SPI("io.github.llnancy.upload4j.core.fn.OriginalFileNameGenerator")
public interface FileNameGenerator {

    String generate(FileGeneratorContext context);
}
