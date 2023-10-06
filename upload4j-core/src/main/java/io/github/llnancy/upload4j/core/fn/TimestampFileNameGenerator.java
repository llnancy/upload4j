package io.github.llnancy.upload4j.core.fn;

import io.github.llnancy.upload4j.api.FileGeneratorContext;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

/**
 * timestamp with four random
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/8/17
 */
public class TimestampFileNameGenerator extends AbstractFileNameGenerator {

    @Override
    protected String doGenerate(FileGeneratorContext context, String fileSuffix) {
        return Instant.now().toEpochMilli() + String.format("%04d", ThreadLocalRandom.current().nextInt(10000)) + fileSuffix;
    }
}
