package io.github.llnancy.uploader.api;

import io.github.nativegroup.spi.SPI;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件名生成器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@SPI("io.github.llnancy.uploader.core.fn.OriginalFileNameGenerator")
public interface FileNameGenerator {

    String generate(MultipartFile mf);
}
