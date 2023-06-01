package io.github.llnancy.uploader.impl.upyun.config;

import com.google.common.collect.Maps;
import com.upyun.RestManager;
import io.github.llnancy.uploader.api.config.UploaderConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

/**
 * UpYun config
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2023/1/4
 */
@Getter
@Setter
public class UpYunConfig extends UploaderConfig {

    /**
     * 空间名
     */
    private String bucketName;

    /**
     * 操作员名
     */
    private String userName;

    /**
     * 操作员密码
     */
    private String password;

    /**
     * API 接口域名接入点，默认根据网络条件自动选择。
     */
    private ApiDomainEnum apiDomain = ApiDomainEnum.ED_AUTO;

    /**
     * 超时时间，默认 30s.
     */
    private Integer timeout = 30;

    @AllArgsConstructor
    @Getter
    public enum ApiDomainEnum {

        /**
         * 根据网络条件自动选择接入点:v0.api.upyun.com
         */
        ED_AUTO(RestManager.ED_AUTO),

        /**
         * 电信接入点:v1.api.upyun.com
         */
        ED_TELECOM(RestManager.ED_TELECOM),

        /**
         * 联通网通接入点:v2.api.upyun.com
         */
        ED_CNC(RestManager.ED_CNC),

        /**
         * 移动铁通接入点:v3.api.upyun.com
         */
        ED_CTT(RestManager.ED_CTT),
        ;

        private final String apiDomain;

        private static final Map<String, ApiDomainEnum> ENUM_MAP = Maps.newHashMap();

        static {
            for (ApiDomainEnum apiDomainEnum : ApiDomainEnum.values()) {
                ENUM_MAP.put(apiDomainEnum.toString(), apiDomainEnum);
            }
        }

        public static ApiDomainEnum match(String key) {
            return Optional.ofNullable(ENUM_MAP.get(key)).orElse(ED_AUTO);
        }
    }
}
