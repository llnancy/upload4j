package io.github.llnancy.uploader.impl.local.test;

import io.github.llnancy.uploader.api.Uploader;
import io.github.llnancy.uploader.impl.local.LocalUploader;
import io.github.llnancy.uploader.impl.local.config.LocalProperties;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * Test {@link LocalUploader}
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2023/1/4
 */
public class LocalUploaderTest {

    @Test
    public void test() throws Exception {
        LocalProperties properties = new LocalProperties();
        properties.setServeDomain("file://");
        properties.setLocalPath("/Users/sunchaser/workspace/data/test/");
        Uploader uploader = new LocalUploader(properties);
        File file = new File("/Users/sunchaser/workspace/data/1.txt");
        MockMultipartFile mockMultipartFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(), MediaType.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream(file));
        String upload = uploader.upload(mockMultipartFile);
        System.out.println(upload);
    }
}
