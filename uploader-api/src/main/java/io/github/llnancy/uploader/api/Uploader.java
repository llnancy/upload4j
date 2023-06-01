package io.github.llnancy.uploader.api;

import io.github.llnancy.uploader.api.config.UploaderConfig;
import io.github.llnancy.uploader.api.exceptions.UploaderException;
import io.github.nativegroup.spi.SPI;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Uploader 接口
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@SPI
public interface Uploader {

    void init();

    default String upload(File file) throws UploaderException {
        try {
            FileInputStream fis = new FileInputStream(file);
            MockMultipartFile multipartFile = new MockMultipartFile(file.getName(), fis);
            return upload(multipartFile);
        } catch (IOException e) {
            throw new UploaderException(e);
        }
    }

    String upload(MultipartFile mf) throws UploaderException;

    void setConfig(UploaderConfig config);

    void setFileUriGenerator(FileUriGenerator fileUriGenerator);
}
