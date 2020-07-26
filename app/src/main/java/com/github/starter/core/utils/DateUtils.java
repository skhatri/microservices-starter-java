package com.github.starter.core.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private DateUtils() {
    }

    public static String safeDateSerialise(Object dateValue) {
        if (dateValue == null) {
            return "";
        }
        if (dateValue instanceof LocalDateTime) {
            LocalDateTime localDateTime = LocalDateTime.class.cast(dateValue);
            return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } else if (dateValue instanceof ZonedDateTime) {
            ZonedDateTime zonedDateTime = ZonedDateTime.class.cast(dateValue);
            return zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        } else {
            OffsetDateTime offsetDateTime = OffsetDateTime.class.cast(dateValue);
            return offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
    }
}
