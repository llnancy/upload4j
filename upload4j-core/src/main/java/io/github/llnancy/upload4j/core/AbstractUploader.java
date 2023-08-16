package io.github.llnancy.upload4j.core;

import com.google.common.base.Preconditions;
import io.github.llnancy.upload4j.api.FileGeneratorContext;
import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.Uploader;
import io.github.llnancy.upload4j.api.config.Upload4jConfig;
import io.github.llnancy.upload4j.api.exceptions.Upload4jException;
import io.github.llnancy.upload4j.api.util.FileUtils;
import io.github.llnancy.upload4j.core.fu.SpecifyPathFileUriGenerator;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * 抽象文件上传器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@Setter
public abstract class AbstractUploader implements Uploader {

    private FileUriGenerator fileUriGenerator;

    public AbstractUploader() {
        this.fileUriGenerator = NativeServiceLoader.getNativeServiceLoader(FileUriGenerator.class).getDefaultNativeService();
    }

    public AbstractUploader(FileUriGenerator fileUriGenerator) {
        this.fileUriGenerator = fileUriGenerator;
    }

    @Override
    public void init() {
        initServeDomain();
        doInit();
    }

    protected void doInit() {
    }

    protected void initServeDomain() {
        Upload4jConfig config = getConfig();
        String serveDomain = config.getServeDomain();
        Preconditions.checkArgument(StringUtils.isNotBlank(serveDomain), "[uploader] - 文件上传器配置 - 服务域名 serveDomain 不能为空");
        config.setServeDomain(serveDomain.endsWith("/") ? StringUtils.chop(serveDomain) : serveDomain);
    }

    protected String getServeDomain() {
        return getConfig().getServeDomain();
    }

    protected String getProtocolHost() {
        try {
            URL url = new URL(getServeDomain());
            String protocol = url.getProtocol();
            String domain = url.getHost();
            return protocol + "://" + domain;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Upload4jConfig getConfig();

    public boolean supportFileType(String type) {
        return true;
    }

    @Override
    public String upload(MultipartFile mf) throws Upload4jException {
        return upload(mf, SpecifyPathFileUriGenerator.DEFAULT_SPECIFY_PATH);
    }

    @Override
    public String upload(MultipartFile mf, String basePath) throws Upload4jException {
        try {
            String type = FileUtils.getFileExtension(Objects.requireNonNull(mf.getOriginalFilename()));
            Preconditions.checkArgument(supportFileType(type), "文件格式有误");
            String fileUri = this.fileUriGenerator.generate(FileGeneratorContext.create(mf));
            if (StringUtils.isNotEmpty(basePath) && !StringUtils.startsWith(basePath, "/")) {
                basePath = "/" + basePath;
            }
            if (StringUtils.isNotEmpty(basePath) && !StringUtils.endsWith(basePath, "/")) {
                basePath = basePath + "/";
            }
            return doMultipartFileUpload(mf, new URL(this.getServeDomain()).getPath() + basePath + fileUri);
        } catch (Exception e) {
            throw new Upload4jException(e);
        }
    }

    @Override
    public Mono<String> upload(FilePart fp) throws Upload4jException {
        return upload(fp, SpecifyPathFileUriGenerator.DEFAULT_SPECIFY_PATH);
    }

    @Override
    public Mono<String> upload(FilePart fp, String basePath) throws Upload4jException {
        try {
            String type = FileUtils.getFileExtension(Objects.requireNonNull(fp.filename()));
            Preconditions.checkArgument(supportFileType(type), "文件格式有误");
            String fileUri = this.fileUriGenerator.generate(FileGeneratorContext.create(fp));
            if (StringUtils.isNotEmpty(basePath) && !StringUtils.startsWith(basePath, "/")) {
                basePath = "/" + basePath;
            }
            if (StringUtils.isNotEmpty(basePath) && !StringUtils.endsWith(basePath, "/")) {
                basePath = basePath + "/";
            }
            return doFilePartUpload(fp, new URL(this.getServeDomain()).getPath() + basePath + fileUri);
        } catch (Exception e) {
            throw new Upload4jException(e);
        }
    }

    @Override
    public boolean delete(String path) throws Upload4jException {
        try {
            return doDelete(path);
        } catch (Exception e) {
            throw new Upload4jException(e);
        }
    }

    protected abstract Mono<String> doFilePartUpload(FilePart fp, String fileUri) throws Exception;

    protected abstract String doMultipartFileUpload(MultipartFile mf, String fileUri) throws Exception;

    protected abstract boolean doDelete(String path) throws Exception;
}
