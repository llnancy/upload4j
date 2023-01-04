package io.github.llnancy.uploader.impl.local;

import cn.hutool.core.io.FileUtil;
import io.github.llnancy.uploader.core.AbstractUploader;
import io.github.llnancy.uploader.core.fileuri.FileUriGenerator;
import io.github.llnancy.uploader.core.fileuri.impl.SpecifyPathFileUriGenerator;
import io.github.llnancy.uploader.impl.local.config.LocalProperties;
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
    private final LocalProperties properties;

    public LocalUploader(LocalProperties properties) {
        this(new SpecifyPathFileUriGenerator(), properties);
    }

    public LocalUploader(FileUriGenerator fileUriGenerator, LocalProperties properties) {
        super(fileUriGenerator);
        this.properties = properties;
        initServeDomain();
    }

    @Override
    protected String doUpload(MultipartFile multipartFile, String fileUri) throws Exception {
        String targetUri = this.properties.getLocalPath() + fileUri;
        FileUtil.writeFromStream(multipartFile.getInputStream(), targetUri);
        return this.getServeDomain() + targetUri;
    }
}
