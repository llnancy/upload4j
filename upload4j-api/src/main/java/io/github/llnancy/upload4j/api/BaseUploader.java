package io.github.llnancy.upload4j.api;

import io.github.llnancy.upload4j.api.config.Upload4jConfig;
import io.github.llnancy.upload4j.api.exceptions.Upload4jException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * base uploader
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/12
 */
public interface BaseUploader {

    /**
     * init
     */
    void init();

    default String getPath(String url) {
        try {
            return new URL(url).getPath();
        } catch (MalformedURLException e) {
            throw new Upload4jException(e);
        }
    }

    /**
     * set config
     *
     * @param config {@link Upload4jConfig}
     */
    void setConfig(Upload4jConfig config);

    /**
     * set {@link FileUriGenerator}
     *
     * @param fileUriGenerator {@link FileUriGenerator}
     */
    void setFileUriGenerator(FileUriGenerator fileUriGenerator);
}
