package io.github.llnancy.uploader.core.fu;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * 按年月日
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class LocalDateFileUriGenerator extends AbstractFileUriGenerator {

    @Override
    protected String doGenerate(MultipartFile mf) {
        LocalDate now = LocalDate.now();
        String year = now.getYear() + "/";
        String month = now.getMonth().getValue() + "/";
        String day = String.valueOf(now.getDayOfMonth());
        // eg. 2021/10/22
        return year + month + day;
    }
}
