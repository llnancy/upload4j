package io.github.llnancy.uploader.api;

import io.github.llnancy.uploader.api.config.UploaderConfig;
import io.github.llnancy.uploader.api.exceptions.UploaderException;
import io.github.nativegroup.spi.SPI;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
            MockMultipartFile mockFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(), MediaType.APPLICATION_OCTET_STREAM_VALUE, Files.newInputStream(file.toPath()));
            return upload(mockFile);
        } catch (IOException e) {
            throw new UploaderException(e);
        }
    }

    default String upload(File file, String basePath) throws UploaderException {
        try {
            MockMultipartFile mockFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(), MediaType.APPLICATION_OCTET_STREAM_VALUE, Files.newInputStream(file.toPath()));
            return upload(mockFile, basePath);
        } catch (IOException e) {
            throw new UploaderException(e);
        }
    }

    String upload(MultipartFile mf) throws UploaderException;

    String upload(MultipartFile mf, String basePath) throws UploaderException;

    void setConfig(UploaderConfig config);

    void setFileUriGenerator(FileUriGenerator fileUriGenerator);
}
