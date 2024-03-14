package io.github.llnancy.upload4j.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * file generate context
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/8/17
 */
public record FileGeneratorContext(String filename, InputStream is) {

    public static FileGeneratorContext create(MultipartFile mf) throws IOException {
        return new FileGeneratorContext(mf.getOriginalFilename(), mf.getInputStream());
    }
}
