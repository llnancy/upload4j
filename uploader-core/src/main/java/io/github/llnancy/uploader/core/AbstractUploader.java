package io.github.llnancy.uploader.core;

import cn.hutool.core.io.FileTypeUtil;
import com.google.common.base.Preconditions;
import io.github.llnancy.uploader.api.Uploader;
import io.github.llnancy.uploader.api.UploaderException;
import io.github.llnancy.uploader.core.config.UploaderProperties;
import io.github.llnancy.uploader.core.fileuri.FileUriGenerator;
import io.github.llnancy.uploader.core.fileuri.impl.SpecifyPathFileUriGenerator;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
        this.fileUriGenerator = new SpecifyPathFileUriGenerator();
    }

    public AbstractUploader(FileUriGenerator fileUriGenerator) {
        this.fileUriGenerator = fileUriGenerator;
    }

    public abstract UploaderProperties getProperties();

    public boolean supportFileType(String type) {
        return true;
    }

    protected void initServeDomain() {
        UploaderProperties properties = getProperties();
        String serveDomain = properties.getServeDomain();
        Preconditions.checkArgument(StringUtils.isNotBlank(serveDomain), "[uploader] - 文件上传器配置 - 服务域名 serveDomain 不能为空");
        properties.setServeDomain(serveDomain.endsWith("/") ? serveDomain : serveDomain + "/");
    }

    protected String getServeDomain() {
        return getProperties().getServeDomain();
    }

    @Override
    public String upload(MultipartFile multipartFile) throws UploaderException {
        try {
            String type = FileTypeUtil.getType(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            Preconditions.checkArgument(supportFileType(type), "文件格式有误");
            String fileUri = this.fileUriGenerator.generateFileUri(multipartFile);
            return doUpload(multipartFile, fileUri);
        } catch (Exception e) {
            throw new UploaderException(e);
        }
    }

    protected abstract String doUpload(MultipartFile multipartFile, String fileUri) throws Exception;
}
