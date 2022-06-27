package com.sunchaser.uploader.core.support;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * FileName: height_width-UUID.fileSuffix
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class ExtensionImageFileNameGenerator extends AbstractFileNameGenerator {

    @Override
    protected String doGenerateFileName(MultipartFile multipartFile, String fileSuffix) throws Exception {
        return null;
    }
    //
    // @Override
    // protected String doGenerateFileName(MultipartFile multipartFile, String fileSuffix) throws Exception {
    //     ImmutablePair<Integer, Integer> hw = ImageUtils.getImageHeightAndWidth(multipartFile);
    //     Integer height = hw.getLeft();
    //     Integer width = hw.getRight();
    //     return height + UNDERSCORE + width + DASH + IdUtils.simpleUUIDWithSuffix(fileSuffix);
    // }
}
