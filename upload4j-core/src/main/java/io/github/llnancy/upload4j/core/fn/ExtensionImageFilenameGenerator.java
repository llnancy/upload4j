package io.github.llnancy.upload4j.core.fn;

import cn.hutool.core.text.StrPool;
import io.github.llnancy.mojian.base.util.IdUtils;
import io.github.llnancy.upload4j.api.FileGeneratorContext;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * FileName: height_width-UUID.fileSuffix
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class ExtensionImageFilenameGenerator extends AbstractFilenameGenerator {

    @SneakyThrows
    @Override
    protected String doGenerate(FileGeneratorContext context, String fileSuffix) {
        BufferedImage bi = ImageIO.read(context.is());
        int width = bi.getWidth();
        int height = bi.getHeight();
        return height + StrPool.UNDERLINE + width + StrPool.DASHED + IdUtils.simpleUUIDWithSuffix(fileSuffix);
    }
}
