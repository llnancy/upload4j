package io.github.llnancy.upload4j.core.fn;

import cn.hutool.core.text.StrPool;
import io.github.llnancy.mojian.base.util.IdUtils;
import io.github.llnancy.mojian.base.util.ImageUtils;
import io.github.llnancy.upload4j.api.FileGeneratorContext;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * FileName: height_width-UUID.fileSuffix
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class ExtensionImageFileNameGenerator extends AbstractFileNameGenerator {

    @SneakyThrows
    @Override
    protected String doGenerate(FileGeneratorContext context, String fileSuffix) {
        ImmutablePair<Integer, Integer> hw = ImageUtils.getImageHeightAndWidth(context.getMf());
        Integer height = hw.getLeft();
        Integer width = hw.getRight();
        return height + StrPool.UNDERLINE + width + StrPool.DASHED + IdUtils.simpleUUIDWithSuffix(fileSuffix);
    }
}
