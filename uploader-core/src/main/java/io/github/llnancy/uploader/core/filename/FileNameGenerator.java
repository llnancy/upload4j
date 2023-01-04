package io.github.llnancy.uploader.core.filename;

import org.springframework.web.multipart.MultipartFile;

/**
 * 生成上传后的文件名
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public interface FileNameGenerator {

    String generateFileName(MultipartFile multipartFile);
}
