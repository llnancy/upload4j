package com.sunchaser.uploader.core.support;

import cn.hutool.core.text.StrPool;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * 按年月日
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class LocalDateFileUriGenerator implements FileUriGenerator {

    @Override
    public String generateFileUri(MultipartFile multipartFile, FileNameGenerator fileNameGenerator) throws Exception {
        LocalDate now = LocalDate.now();
        String year = now.getYear() + StrPool.SLASH;
        String month = now.getMonth().getValue() + StrPool.SLASH;
        String day = now.getDayOfMonth() + StrPool.SLASH;
        return year +
                month +
                day +
                fileNameGenerator.generateFileName(multipartFile);
    }

}
