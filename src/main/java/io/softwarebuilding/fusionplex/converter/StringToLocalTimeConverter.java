package io.softwarebuilding.fusionplex.converter;

import io.softwarebuilding.fusionplex.util.FusionPlexUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {


    @Override
    public LocalTime convert(@NotNull final String value) {

        if (value.isEmpty()) {
            return null;
        }

        return FusionPlexUtil.convertStringToLocalTime(value, "HH:mm");
    }
}
