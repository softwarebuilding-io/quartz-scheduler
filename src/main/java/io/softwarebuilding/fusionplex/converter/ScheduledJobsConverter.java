package io.softwarebuilding.fusionplex.converter;

import io.softwarebuilding.fusionplex.enums.ScheduledJobs;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJobsConverter implements Converter<String, ScheduledJobs> {

    @Override
    public ScheduledJobs convert(@NotNull final String value) {
        return ScheduledJobs.valueOfJobName(value);
    }
}
