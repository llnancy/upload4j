package io.github.llnancy.upload4j.impl.upyun;

import cn.hutool.core.map.MapUtil;
import com.google.common.base.Preconditions;
import com.upyun.RestManager;
import com.upyun.UpYunUtils;
import io.github.llnancy.upload4j.api.FileUriGenerator;
import io.github.llnancy.upload4j.api.config.Upload4jConfig;
import io.github.llnancy.upload4j.api.exceptions.Upload4jException;
import io.github.llnancy.upload4j.core.AbstractUploader;
import io.github.llnancy.upload4j.impl.upyun.config.UpYunConfig;
import io.github.nativegroup.spi.NativeServiceLoader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 又拍云文件上传器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@Slf4j
public class UpYunUploader extends AbstractUploader {

    @Getter
    private UpYunConfig config;

    @Getter
    private RestManager restManager;

    public UpYunUploader() {
    }

    public UpYunUploader(UpYunConfig properties) {
        this(NativeServiceLoader.getNativeServiceLoader(FileUriGenerator.class).getDefaultNativeService(), properties);
    }

    public UpYunUploader(FileUriGenerator fileUriGenerator, UpYunConfig config) {
        super(fileUriGenerator);
        this.config = config;
        init();
    }

    @Override
    public void setConfig(Upload4jConfig config) {
        this.config = (UpYunConfig) config;
    }

    @Override
    protected void doInit() {
        String bucketName = this.config.getBucketName();
        String userName = this.config.getUserName();
        String password = this.config.getPassword();
        String apiDomain = this.config.getApiDomain().getApiDomain();
        Integer timeout = this.config.getTimeout();
        Preconditions.checkArgument(StringUtils.isNotBlank(bucketName), "[upyun] - 又拍云配置 - 空间名 bucketName 不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(userName), "[upyun] - 又拍云配置 - 操作员名 userName 不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(password), "[upyun] - 又拍云配置 - 操作员密码 password 不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(apiDomain), "[upyun] - 又拍云配置 - API 接口域名接入点 apiDomain 不能为空");
        Preconditions.checkArgument(Objects.nonNull(timeout), "[upyun] - 又拍云配置 - OkHttpClient 的超时时间 timeout 不能为空");
        this.restManager = new RestManager(bucketName, userName, password);
        this.restManager.setApiDomain(apiDomain);
        this.restManager.setTimeout(timeout);
    }

    @Override
    protected Mono<String> doFilePartUpload(FilePart fp, String fileUri) throws Exception {
        return Mono.fromCallable(() -> this.restManager.getFileInfo(fileUri))
                .flatMap(fileInfo -> {
                    Headers headers = fileInfo.headers();
                    String fileSize = headers.get(RestManager.PARAMS.X_UPYUN_FILE_SIZE.getValue());
                    return DataBufferUtils.join(fp.content())
                            .flatMap(dataBuffer -> {
                                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(bytes);
                                DataBufferUtils.release(dataBuffer);

                                String newMd5 = UpYunUtils.md5(bytes);
                                if (StringUtils.isNotBlank(fileSize) && newMd5.equals(headers.get(RestManager.PARAMS.CONTENT_MD5.getValue()))) {
                                    log.warn("[upyun] - 又拍云文件上传，文件名：{}，md5值相同，上传文件重复", fileUri);
                                    return Mono.just(this.getProtocolHost() + fileUri);
                                }
                                return Mono.fromCallable(() -> this.restManager.writeFile(fileUri, bytes, MapUtil.of(RestManager.PARAMS.CONTENT_MD5.getValue(), newMd5)))
                                        .flatMap(response -> {
                                            if (response.isSuccessful()) {
                                                return Mono.just(this.getProtocolHost() + fileUri);
                                            } else {
                                                log.error("[upyun] - 又拍云文件上传失败，response={}", response);
                                                return Mono.error(new Upload4jException("[upyun] - 又拍云文件上传失败，fileUri=" + fileUri));
                                            }
                                        });
                            });
                });
    }

    @Override
    protected String doMultipartFileUpload(MultipartFile mf, String fileUri) throws Exception {
        Response fileInfo = null;
        Response response = null;
        try {
            fileInfo = this.restManager.getFileInfo(fileUri);
            Headers headers = fileInfo.headers();
            String fileSize = headers.get(RestManager.PARAMS.X_UPYUN_FILE_SIZE.getValue());
            byte[] bytes = mf.getBytes();
            String newMd5 = UpYunUtils.md5(bytes);
            if (StringUtils.isNotBlank(fileSize) && newMd5.equals(headers.get(RestManager.PARAMS.CONTENT_MD5.getValue()))) {
                log.warn("[upyun] - 又拍云文件上传，文件名：{}，md5值相同，上传文件重复", fileUri);
                return this.getProtocolHost() + fileUri;
            }
            response = this.restManager.writeFile(fileUri, bytes, MapUtil.of(RestManager.PARAMS.CONTENT_MD5.getValue(), newMd5));
            if (response.isSuccessful()) {
                return this.getProtocolHost() + fileUri;
            }
            log.error("[upyun] - 又拍云文件上传失败，response={}", response);
            throw new Upload4jException("[upyun] - 又拍云文件上传失败，fileUri=" + fileUri);
        } finally {
            if (Objects.nonNull(fileInfo)) {
                fileInfo.close();
            }
            if (Objects.nonNull(response)) {
                response.close();
            }
        }
    }

    @Override
    protected boolean doDelete(String path) throws Exception {
        try (Response response = this.restManager.deleteFile(path, null)) {
            return response.isSuccessful();
        }
    }
}
