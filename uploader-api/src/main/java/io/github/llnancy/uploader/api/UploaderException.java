package io.github.llnancy.uploader.api;

/**
 * 自定义异常
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/27
 */
public class UploaderException extends RuntimeException {

    private static final long serialVersionUID = -3926466561427504553L;

    public UploaderException() {
    }

    public UploaderException(String message) {
        super(message);
    }

    public UploaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploaderException(Throwable cause) {
        super(cause);
    }

    public UploaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
