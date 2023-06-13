package io.github.llnancy.uploader.core;

import cn.hutool.core.io.FileTypeUtil;
import com.google.common.base.Preconditions;
import io.github.llnancy.uploader.api.FileUriGenerator;
import io.github.llnancy.uploader.api.Uploader;
import io.github.llnancy.uploader.api.config.UploaderConfig;
import io.github.llnancy.uploader.api.exceptions.UploaderException;
import io.github.llnancy.uploader.core.fu.SpecifyPathFileUriGenerator;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;

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
        UploaderConfig config = getConfig();
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

    public abstract UploaderConfig getConfig();

    public boolean supportFileType(String type) {
        return true;
    }

    @Override
    public String upload(MultipartFile mf) throws UploaderException {
        return upload(mf, SpecifyPathFileUriGenerator.DEFAULT_SPECIFY_PATH);
    }

    @Override
    public String upload(MultipartFile mf, String basePath) throws UploaderException {
        try {
            String type = FileTypeUtil.getType(mf.getInputStream(), mf.getOriginalFilename());
            Preconditions.checkArgument(supportFileType(type), "文件格式有误");
            String fileUri = this.fileUriGenerator.generate(mf);
            if (StringUtils.isNotEmpty(basePath) && !StringUtils.startsWith(basePath, "/")) {
                basePath = "/" + basePath;
            }
            if (StringUtils.isNotEmpty(basePath) && !StringUtils.endsWith(basePath, "/")) {
                basePath = basePath + "/";
            }
            return doUpload(mf, new URL(this.getServeDomain()).getPath() + basePath + fileUri);
        } catch (Exception e) {
            throw new UploaderException(e);
        }
    }

    @Override
    public boolean delete(String path) throws UploaderException {
        try {
            return doDelete(path);
        } catch (Exception e) {
            throw new UploaderException(e);
        }
    }

    protected abstract String doUpload(MultipartFile mf, String fileUri) throws Exception;

    protected abstract boolean doDelete(String path) throws Exception;
}
