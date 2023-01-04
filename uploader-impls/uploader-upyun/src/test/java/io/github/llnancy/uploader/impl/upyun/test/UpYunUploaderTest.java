package io.github.llnancy.uploader.impl.upyun.test;

import io.github.llnancy.uploader.api.Uploader;
import io.github.llnancy.uploader.core.filename.impl.OriginalFileNameGenerator;
import io.github.llnancy.uploader.core.fileuri.impl.SpecifyPathFileUriGenerator;
import io.github.llnancy.uploader.impl.upyun.UpYunUploader;
import io.github.llnancy.uploader.impl.upyun.config.UpYunProperties;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * Test {@link UpYunUploader}
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2023/1/4
 */
public class UpYunUploaderTest {

    @Test
    public void test() throws Exception {
        UpYunProperties properties = new UpYunProperties();
        properties.setServeDomain("https://images.lilu.org.cn");
        properties.setBucketName("llnancy-images");
        properties.setUserName("sunchaser");
        properties.setPassword("xxxxxxxxx");
        OriginalFileNameGenerator fileNameGenerator = new OriginalFileNameGenerator();
        SpecifyPathFileUriGenerator fileUriGenerator = new SpecifyPathFileUriGenerator(fileNameGenerator, "acgn");
        Uploader uploader = new UpYunUploader(fileUriGenerator, properties);
        File file = new File("/Users/sunchaser/workspace/data/cartoon/0be8fbdb4837491f954c9e08af7815a5.png");
        MockMultipartFile mockMultipartFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(), MediaType.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream(file));
        String upload = uploader.upload(mockMultipartFile);
        System.out.println(upload);
    }
}
