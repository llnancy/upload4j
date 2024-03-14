package io.github.llnancy.upload4j.api.exceptions;

import java.io.Serial;

/**
 * 自定义异常
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
public class Upload4jException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3926466561427504553L;

    public Upload4jException() {
    }

    public Upload4jException(String message) {
        super(message);
    }

    public Upload4jException(String message, Throwable cause) {
        super(message, cause);
    }

    public Upload4jException(Throwable cause) {
        super(cause);
    }

    public Upload4jException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
