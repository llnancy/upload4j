package com.sunchaser.uploader.core.impl.upyun;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.sunchaser.uploader.core.UploaderException;
import com.sunchaser.uploader.core.impl.AbstractUploader;
import com.upyun.RestManager;
import com.upyun.UpYunUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Response;
import org.springframework.web.multipart.MultipartFile;

import java.net.Proxy;

/**
 * 又拍云文件上传器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@Slf4j
public class UpYunUploader extends AbstractUploader {

    private RestManager restManager;

    private Integer timeout;

    private final String bucketName;

    private final String userName;

    private final String password;

    private Proxy proxy;

    private String apiDomain;

    private String speedDomain = CharSequenceUtil.EMPTY;

    public UpYunUploader(String bucketName, String userName, String password) {
        this.bucketName = bucketName;
        this.userName = userName;
        this.password = password;
        this.restManager = new RestManager(bucketName, userName, password);
        this.apiDomain = RestManager.ED_AUTO;
        this.restManager.setApiDomain(this.apiDomain);
        this.timeout = 30;
    }

    public UpYunUploader(String bucketName, String userName, String password, String apiDomain, Integer timeout) {
        this.timeout = timeout;
        this.bucketName = bucketName;
        this.userName = userName;
        this.password = password;
        this.apiDomain = apiDomain;
        this.restManager = new RestManager(bucketName, userName, password);
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setSpeedDomain(String speedDomain) {
        this.speedDomain = speedDomain;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void setApiDomain(String apiDomain) {
        this.apiDomain = apiDomain;
        this.restManager = new RestManager(bucketName, userName, password);
    }

    @Override
    protected String doUpload(MultipartFile multipartFile, String fileUri) throws Exception {
        Response fileInfo = this.restManager.getFileInfo(fileUri);
        Headers headers = fileInfo.headers();
        String oldMd5 = headers.get(RestManager.PARAMS.CONTENT_MD5.getValue());
        String fileSize = headers.get(RestManager.PARAMS.X_UPYUN_FILE_SIZE.getValue());
        String newMd5 = UpYunUtils.md5(multipartFile.getBytes());
        if (StrUtil.isNotBlank(fileSize)) {
            if (newMd5.equals(oldMd5)) {
                log.warn("[upyun] - 又拍云文件上传，文件名：{}，md5值相同，请勿重复上传相同文件", fileUri);
                return speedDomain + fileUri;
            }
        }
        Response response = this.restManager.writeFile(fileUri, multipartFile.getInputStream(), MapUtil.of(RestManager.PARAMS.CONTENT_MD5.getValue(), newMd5));
        if (response.isSuccessful()) {
            return speedDomain + fileUri;
        }
        log.error("[upyun] - 又拍云文件上传失败，response={}", response);
        throw new UploaderException(StrUtil.format("[upyun] - 又拍云文件上传失败，fileUri={}", fileUri));
    }
}
