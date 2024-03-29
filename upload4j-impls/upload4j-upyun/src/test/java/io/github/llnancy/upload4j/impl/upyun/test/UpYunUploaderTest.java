package io.github.llnancy.upload4j.impl.upyun.test;

import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.Uploader;
import io.github.llnancy.upload4j.core.fu.SpecifyPathFileUriGenerator;
import io.github.llnancy.upload4j.impl.upyun.UpYunUploaderImpl;
import io.github.llnancy.upload4j.impl.upyun.config.UpYunConfig;
import io.github.nativegroup.spi.NativeServiceLoader;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.nio.file.Files;

/**
 * Test {@link UpYunUploaderImpl}
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2023/1/4
 */
public class UpYunUploaderTest {

    @Test
    public void test() throws Exception {
        UpYunConfig properties = new UpYunConfig();
        properties.setServeDomain("https://images.lilu.org.cn");
        properties.setBucketName("llnancy-images");
        properties.setUserName("sunchaser");
        properties.setPassword("xxxxxx");
        SpecifyPathFileUriGenerator fileUriGenerator = (SpecifyPathFileUriGenerator) NativeServiceLoader.getNativeServiceLoader(FileUriGenerator.class).getNativeService("io.github.llnancy.uploader.core.fu.SpecifyPathFileUriGenerator");
        // fileUriGenerator.setSpecifyPath("/test");
        Uploader uploader = NativeServiceLoader.getNativeServiceLoader(Uploader.class).getNativeService("io.github.llnancy.uploader.impl.upyun.UpYunUploader");
        uploader.setFileUriGenerator(fileUriGenerator);
        uploader.setConfig(properties);
        uploader.init();
        File file = new File("/Users/llnancy/workspace/data/4d2fec59f254487bad04bca59816edf0.png");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getName());
        MockMultipartFile mockMultipartFile = new MockMultipartFile(file.getAbsolutePath(), file.getName(), MediaType.APPLICATION_OCTET_STREAM_VALUE, Files.newInputStream(file.toPath()));
        String upload = uploader.upload(mockMultipartFile, "top");
        System.out.println(upload);
    }

    @Test
    public void testDelete() {
        UpYunConfig properties = new UpYunConfig();
        properties.setServeDomain("https://images.lilu.org.cn");
        properties.setBucketName("llnancy-images");
        properties.setUserName("sunchaser");
        properties.setPassword("xxxxxx");
        SpecifyPathFileUriGenerator fileUriGenerator = (SpecifyPathFileUriGenerator) NativeServiceLoader.getNativeServiceLoader(FileUriGenerator.class)
                .getNativeService("io.github.llnancy.uploader.core.fu.SpecifyPathFileUriGenerator");
        // fileUriGenerator.setSpecifyPath("/test");
        Uploader uploader = NativeServiceLoader.getNativeServiceLoader(Uploader.class)
                .getNativeService("io.github.llnancy.uploader.impl.upyun.UpYunUploader");
        uploader.setFileUriGenerator(fileUriGenerator);
        uploader.setConfig(properties);
        uploader.init();
        boolean delete = uploader.delete("/test/tmksz59tJFIP066bcc6e69b32f7ae80f2b5ad18139ef.png");
        Assertions.assertTrue(delete);
    }
}
