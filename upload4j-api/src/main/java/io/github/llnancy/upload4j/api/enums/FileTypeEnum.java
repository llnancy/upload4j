package io.github.llnancy.upload4j.api.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件类型枚举
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@AllArgsConstructor
@Getter
public enum FileTypeEnum {

    IMAGE(Lists.newArrayList(
            FileExtNameEnum.JPEG.getExtName(),
            FileExtNameEnum.PNG.getExtName(),
            FileExtNameEnum.GIF.getExtName()
    )),

    ALL(Arrays.stream(FileExtNameEnum.values())
            .map(FileExtNameEnum::getExtName)
            .collect(Collectors.toList())
    );

    public void addFileExtName(String fileExtName) {
        this.fileExtNameList.add(fileExtName);
    }

    private final List<String> fileExtNameList;

    private static final Map<String, FileTypeEnum> ENUM_MAP = Maps.newHashMap();

    static {
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            ENUM_MAP.put(fileTypeEnum.name(), fileTypeEnum);
        }
    }

    public static FileTypeEnum match(String fileType) {
        return ENUM_MAP.get(fileType);
    }
}
