package io.softwarebuilding.fusionplex.converter;

import io.softwarebuilding.fusionplex.enums.CronJob;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCronJobConverter implements Converter<String, CronJob> {

    @Override
    public CronJob convert(@NotNull final String source) {
        return CronJob.convertToCronJob(source);
    }
}
