package io.github.llnancy.upload4j.api;

import lombok.Getter;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

/**
 * file generate context
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/8/17
 */
@Getter
public class FileGeneratorContext {

    private final String filename;

    private MultipartFile mf;

    private FilePart fp;

    public FileGeneratorContext(String filename, MultipartFile mf) {
        this.filename = filename;
        this.mf = mf;
    }

    public FileGeneratorContext(String filename, FilePart fp) {
        this.filename = filename;
        this.fp = fp;
    }

    public static FileGeneratorContext create(MultipartFile mf) {
        return new FileGeneratorContext(mf.getOriginalFilename(), mf);
    }

    public static FileGeneratorContext create(FilePart fp) {
        return new FileGeneratorContext(fp.filename(), fp);
    }
}
