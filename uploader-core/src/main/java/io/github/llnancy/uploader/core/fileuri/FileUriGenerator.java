package io.github.llnancy.uploader.core.fileuri;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public interface FileUriGenerator {

    String generateFileUri(MultipartFile multipartFile);
}
