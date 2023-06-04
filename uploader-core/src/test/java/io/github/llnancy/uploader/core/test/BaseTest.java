package io.github.llnancy.uploader.core.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * test
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2023/6/1
 */
public class BaseTest {

    @Test
    public void test() {
        String basePath = "path/to";
        if (!StringUtils.startsWith(basePath, "/")) {
            basePath = "/" + basePath;
        }
        if (!StringUtils.endsWith(basePath, "/")) {
            basePath = basePath + "/";
        }
        Assertions.assertEquals(basePath, "/path/to/");
    }
}
