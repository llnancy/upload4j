package io.github.llnancy.upload4j.core.fn;

import cn.hutool.core.text.StrPool;
import io.github.llnancy.mojian.base.util.IdUtils;
import io.github.llnancy.mojian.base.util.ImageUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileName: height_width-UUID.fileSuffix
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class ExtensionImageFileNameGenerator extends AbstractFileNameGenerator {

    @SneakyThrows
    @Override
    protected String doGenerate(MultipartFile mf, String fileSuffix) {
        ImmutablePair<Integer, Integer> hw = ImageUtils.getImageHeightAndWidth(mf);
        Integer height = hw.getLeft();
        Integer width = hw.getRight();
        return height + StrPool.UNDERLINE + width + StrPool.DASHED + IdUtils.simpleUUIDWithSuffix(fileSuffix);
    }
}
