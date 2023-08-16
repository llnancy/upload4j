package io.github.llnancy.upload4j.api.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * file util
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/8/17
 */
@UtilityClass
public class FileUtils {

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return StringUtils.EMPTY;
    }
}
