package io.softwarebuilding.fusionplex.converter;

import io.softwarebuilding.fusionplex.util.FusionPlexUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class LocalTimeToStringConverter implements Converter<LocalTime, String> {

    @Override
    public String convert(@NotNull final LocalTime localTime) {
        return FusionPlexUtil.connvertLocalTimeToString(localTime, "HH:mm");
    }
}
