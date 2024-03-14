package io.github.llnancy.upload4j.impl.upyun;

import com.google.common.base.Preconditions;
import com.upyun.RestManager;
import io.github.llnancy.upload4j.impl.upyun.config.UpYunConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * utils
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/12
 */
public class Utils {

    public static RestManager initRestManager(UpYunConfig config) {
        String bucketName = config.getBucketName();
        String userName = config.getUserName();
        String password = config.getPassword();
        String apiDomain = config.getApiDomain().getApiDomain();
        Integer timeout = config.getTimeout();
        Preconditions.checkArgument(StringUtils.isNotBlank(bucketName), "[upyun] - 又拍云配置 - 空间名 bucketName 不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(userName), "[upyun] - 又拍云配置 - 操作员名 userName 不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(password), "[upyun] - 又拍云配置 - 操作员密码 password 不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(apiDomain), "[upyun] - 又拍云配置 - API 接口域名接入点 apiDomain 不能为空");
        Preconditions.checkArgument(Objects.nonNull(timeout), "[upyun] - 又拍云配置 - OkHttpClient 的超时时间 timeout 不能为空");
        RestManager restManager = new RestManager(bucketName, userName, password);
        restManager.setApiDomain(apiDomain);
        restManager.setTimeout(timeout);
        return restManager;
    }
}
