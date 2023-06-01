package io.github.llnancy.uploader.core;

import cn.hutool.core.io.FileTypeUtil;
import com.google.common.base.Preconditions;
import io.github.llnancy.uploader.api.FileUriGenerator;
import io.github.llnancy.uploader.api.Uploader;
import io.github.llnancy.uploader.api.config.UploaderConfig;
import io.github.llnancy.uploader.api.exceptions.UploaderException;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    public abstract UploaderConfig getConfig();

    public boolean supportFileType(String type) {
        return true;
    }

    @Override
    public String upload(MultipartFile mf) throws UploaderException {
        try {
            String type = FileTypeUtil.getType(mf.getInputStream(), mf.getOriginalFilename());
            Preconditions.checkArgument(supportFileType(type), "文件格式有误");
            String fileUri = this.fileUriGenerator.generate(mf);
            return doUpload(mf, new URL(this.getServeDomain()).getPath() + fileUri);
        } catch (Exception e) {
            throw new UploaderException(e);
        }
    }

    protected abstract String doUpload(MultipartFile mf, String fileUri) throws Exception;
}
