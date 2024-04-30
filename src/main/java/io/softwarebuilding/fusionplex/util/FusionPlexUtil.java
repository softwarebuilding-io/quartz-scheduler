package io.softwarebuilding.fusionplex.util;

import io.softwarebuilding.fusionplex.error.FusionPlexException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public static String connvertLocalTimeToString(final LocalTime localTime, final String pattern) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

        return localTime.format(dtf);
    }

    public static LocalTime convertStringToLocalTime(final String value, final String pattern) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

        return LocalTime.parse(value, dtf);
    }

    public static Date localDateTimeToDate(final LocalDateTime startTime) {
        return Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date localTimeToDate(final LocalTime startTime) {
        final LocalDate currentDate = LocalDate.now();

        final LocalDateTime currentDateTime = LocalDateTime.of(currentDate, startTime);

        return Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
