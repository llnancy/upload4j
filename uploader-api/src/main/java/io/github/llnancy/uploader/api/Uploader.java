package io.github.llnancy.uploader.api;

import io.github.llnancy.uploader.api.config.UploaderConfig;
import io.github.llnancy.uploader.api.exceptions.UploaderException;
import io.github.nativegroup.spi.SPI;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

/**
 * Uploader 接口
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@SPI
public interface Uploader {

    /**
     * init
     */
    void init();

    /**
     * upload {@link File}
     *
     * @param file {@link File}
     * @return server url
     * @throws UploaderException ex
     */
    default String upload(File file) throws UploaderException {
        try {
            MockMultipartFile mockFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(), MediaType.APPLICATION_OCTET_STREAM_VALUE, Files.newInputStream(file.toPath()));
            return upload(mockFile);
        } catch (IOException e) {
            throw new UploaderException(e);
        }
    }

    /**
     * upload {@link File} with base path
     *
     * @param file     {@link File}
     * @param basePath base path
     * @return server url
     * @throws UploaderException ex
     */
    default String upload(File file, String basePath) throws UploaderException {
        try {
            MockMultipartFile mockFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(), MediaType.APPLICATION_OCTET_STREAM_VALUE, Files.newInputStream(file.toPath()));
            return upload(mockFile, basePath);
        } catch (IOException e) {
            throw new UploaderException(e);
        }
    }

    /**
     * upload {@link MultipartFile}
     *
     * @param mf {@link MultipartFile}
     * @return server url
     * @throws UploaderException ex
     */
    String upload(MultipartFile mf) throws UploaderException;

    /**
     * upload {@link MultipartFile} with base path
     *
     * @param mf       {@link MultipartFile}
     * @param basePath base path
     * @return server url
     * @throws UploaderException ex
     */
    String upload(MultipartFile mf, String basePath) throws UploaderException;

    /**
     * delete file
     * example: new URL(urlString).getPath();
     *
     * @param path url path
     * @return success or fail
     * @throws UploaderException ex
     */
    boolean delete(String path) throws UploaderException;

    default String getPath(String url) {
        try {
            return new URL(url).getPath();
        } catch (MalformedURLException e) {
            throw new UploaderException(e);
        }
    }

    /**
     * set config
     *
     * @param config {@link UploaderConfig}
     */
    void setConfig(UploaderConfig config);

    /**
     * set {@link FileUriGenerator}
     *
     * @param fileUriGenerator {@link FileUriGenerator}
     */
    void setFileUriGenerator(FileUriGenerator fileUriGenerator);
}
