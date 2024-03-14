package io.github.llnancy.upload4j.api;

import io.github.llnancy.upload4j.api.exceptions.Upload4jException;
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
public interface Uploader extends BaseUploader {

    /**
     * upload {@link File}
     *
     * @param file {@link File}
     * @return server url
     * @throws Upload4jException ex
     */
    default String upload(File file) throws Upload4jException {
        try {
            MockMultipartFile mockFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(), MediaType.APPLICATION_OCTET_STREAM_VALUE, Files.newInputStream(file.toPath()));
            return upload(mockFile);
        } catch (IOException e) {
            throw new Upload4jException(e);
        }
    }

    /**
     * upload {@link File} with base path
     *
     * @param file     {@link File}
     * @param basePath base path
     * @return server url
     * @throws Upload4jException ex
     */
    default String upload(File file, String basePath) throws Upload4jException {
        try {
            MockMultipartFile mockFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(), MediaType.APPLICATION_OCTET_STREAM_VALUE, Files.newInputStream(file.toPath()));
            return upload(mockFile, basePath);
        } catch (IOException e) {
            throw new Upload4jException(e);
        }
    }

    /**
     * upload {@link MultipartFile}
     *
     * @param mf {@link MultipartFile}
     * @return server url
     * @throws Upload4jException ex
     */
    String upload(MultipartFile mf) throws Upload4jException;

    /**
     * upload {@link MultipartFile} with base path
     *
     * @param mf       {@link MultipartFile}
     * @param basePath base path
     * @return server url
     * @throws Upload4jException ex
     */
    String upload(MultipartFile mf, String basePath) throws Upload4jException;

    /**
     * delete file
     * example: new URL(urlString).getPath();
     *
     * @param path url path
     * @return success or fail
     * @throws Upload4jException ex
     */
    boolean delete(String path) throws Upload4jException;
}
