package io.github.llnancy.upload4j.impl.local;

import cn.hutool.core.io.FileUtil;
import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.config.Upload4jConfig;
import io.github.llnancy.upload4j.core.AbstractUploader;
import io.github.llnancy.upload4j.impl.local.config.LocalConfig;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.Getter;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

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
    protected Mono<String> doFilePartUpload(FilePart fp, String fileUri) throws Exception {
        String targetUri = this.config.getLocalPath() + fileUri;
        return fp.transferTo(Path.of(targetUri))
                .then(Mono.defer(() -> Mono.just(this.getProtocolHost() + targetUri)));
    }

    @Override
    protected String doMultipartFileUpload(MultipartFile mf, String fileUri) throws Exception {
        String targetUri = this.config.getLocalPath() + fileUri;
        FileUtil.writeFromStream(mf.getInputStream(), targetUri);
        return this.getProtocolHost() + targetUri;
    }

    @Override
    protected boolean doDelete(String path) throws Exception {
        return FileUtil.del(path);
    }

    @Override
    public void setConfig(Upload4jConfig config) {
        this.config = (LocalConfig) config;
    }
}
