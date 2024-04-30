package io.softwarebuilding.fusionplex.util;

import io.softwarebuilding.fusionplex.error.FusionPlexException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class FusionPlexUtil {

    private FusionPlexUtil() {
    }

    public static LocalDate convertStringToLocalDate(final String value, final String pattern) {

        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

        try {
            return LocalDate.parse(value, dtf);
        } catch (final Exception exception) {
            throw new FusionPlexException("Error parsing date value");
        }

    }
}
