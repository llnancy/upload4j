package io.github.llnancy.upload4j.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件后缀名枚举
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@Getter
@AllArgsConstructor
public enum FileExtNameEnum {

    ANY("", ""),

    JPG("jpg", "JPEG (jpg)"),
    JPEG("jpeg", "JPEG (jpg)"),
    PNG("png", "PNG (png)"),
    GIF("gif", "GIF (gif)"),

    TIFF("tif", "TIFF (tif)"),
    BMP("bmp", "16色位图(bmp)/24色位图(bmp)/256色位图(bmp)"),
    DWG("dwg", "CAD (dwg)"),
    ;

    private final String extName;

    private final String desc;
}
