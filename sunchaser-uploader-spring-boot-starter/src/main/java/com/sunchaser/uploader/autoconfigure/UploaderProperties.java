package com.sunchaser.uploader.autoconfigure;

import com.google.common.collect.Maps;
import com.upyun.RestManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Optional;

/**
 * 上传属性配置
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
@Data
@ConfigurationProperties(prefix = "sunchaser.uploader")
public class UploaderProperties {

    private String uploaderType;

    private String fileNameGeneratorType;

    private String fileUriGeneratorType;

    private String specifyPath;

    private final Local local = new Local();

    private final UpYun upYun = new UpYun();

    @Data
    public static class Local {

        private String localPath;
    }

    @Data
    public static class UpYun {

        private Integer timeout;

        private String bucketName;

        private String userName;

        private String password;

        private String apiDomain;

        private String speedDomain = Strings.EMPTY;

        @AllArgsConstructor
        @Getter
        enum ApiDomainEnum {

            AUTO("ED_AUTO", RestManager.ED_AUTO),
            TELECOM("ED_TELECOM", RestManager.ED_TELECOM),
            CNC("ED_CNC", RestManager.ED_CNC),
            CTT("ED_CTT", RestManager.ED_CTT),
            ;

            private final String key;

            private final String val;

            private static final Map<String, ApiDomainEnum> enumMap = Maps.newHashMap();

            static {
                for (ApiDomainEnum apiDomainEnum : ApiDomainEnum.values()) {
                    enumMap.put(apiDomainEnum.key, apiDomainEnum);
                }
            }

            public static ApiDomainEnum match(String key) {
                return Optional.ofNullable(enumMap.get(key)).orElse(AUTO);
            }
        }
    }
}
