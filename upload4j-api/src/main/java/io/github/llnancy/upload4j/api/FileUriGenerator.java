package io.github.llnancy.upload4j.api;

import io.github.nativegroup.spi.SPI;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件路径生成器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@SPI("io.github.llnancy.upload4j.core.fu.SpecifyPathFileUriGenerator")
public interface FileUriGenerator {

    String generate(MultipartFile mf);

    void setFileNameGenerator(FileNameGenerator fileNameGenerator);
}
