package io.github.llnancy.upload4j.core;

import com.google.common.base.Preconditions;
import io.github.llnancy.upload4j.api.FileGeneratorContext;
import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.Uploader;
import io.github.llnancy.upload4j.api.exceptions.Upload4jException;
import io.github.llnancy.upload4j.api.util.FileUtils;
import io.github.llnancy.upload4j.core.fu.SpecifyPathFileUriGenerator;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Objects;

/**
 * abstract file uploader impl
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@Setter
public abstract class AbstractUploaderImpl extends AbstractUploader implements Uploader {

    public AbstractUploaderImpl() {
    }

    public AbstractUploaderImpl(FileUriGenerator fileUriGenerator) {
        super(fileUriGenerator);
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
    public boolean delete(String path) throws Upload4jException {
        try {
            return doDelete(path);
        } catch (Exception e) {
            throw new Upload4jException(e);
        }
    }

    protected abstract String doMultipartFileUpload(MultipartFile mf, String fileUri) throws Exception;

    protected abstract boolean doDelete(String path) throws Exception;
}
