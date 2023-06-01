package io.github.llnancy.uploader.impl.local;

import cn.hutool.core.io.FileUtil;
import io.github.llnancy.uploader.api.FileUriGenerator;
import io.github.llnancy.uploader.api.config.UploaderConfig;
import io.github.llnancy.uploader.core.AbstractUploader;
import io.github.llnancy.uploader.impl.local.config.LocalConfig;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 本地文件上传器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
public class LocalUploader extends AbstractUploader {

    @Getter
    private LocalConfig config;

    public LocalUploader() {
    }

    public LocalUploader(LocalConfig properties) {
        this(NativeServiceLoader.getNativeServiceLoader(FileUriGenerator.class).getDefaultNativeService(), properties);
    }

    public LocalUploader(FileUriGenerator fileUriGenerator, LocalConfig config) {
        super(fileUriGenerator);
        this.config = config;
        init();
    }

    @Override
    protected String doUpload(MultipartFile mf, String fileUri) throws Exception {
        String targetUri = this.config.getLocalPath() + fileUri;
        FileUtil.writeFromStream(mf.getInputStream(), targetUri);
        return this.getServeDomain() + targetUri;
    }

    @Override
    public void setConfig(UploaderConfig config) {
        this.config = (LocalConfig) config;
    }
}
