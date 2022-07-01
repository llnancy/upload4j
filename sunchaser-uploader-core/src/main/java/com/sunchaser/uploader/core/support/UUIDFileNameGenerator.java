package com.sunchaser.uploader.core.support;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class UUIDFileNameGenerator extends AbstractFileNameGenerator {

    @Override
    protected String doGenerateFileName(MultipartFile multipartFile, String fileSuffix) {
        return UUID.randomUUID().toString().replaceAll(StrPool.DASHED, CharSequenceUtil.EMPTY);
    }
}
