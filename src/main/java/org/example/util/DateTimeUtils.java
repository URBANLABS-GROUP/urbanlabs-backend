package org.example.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum DateTimeUtils {;

    public static List<Interval> splitByDays(final Interval interval,
                                             final ZoneId timeZone) {
        if (interval == null) {
            throw new IllegalArgumentException("Intervals list cannot be null or empty");
        }
        if (timeZone == null) {
            throw new IllegalArgumentException("TimeZone cannot be null");
        }

        final List<Interval> resultIntervals = new ArrayList<>();

        final LocalDate fromDate = interval.getFrom().atZone(timeZone).toLocalDate();
        final LocalDate toDate = interval.getTo().atZone(timeZone).toLocalDate();

        if (fromDate.equals(toDate)) {
            resultIntervals.add(Interval.of(interval.getFrom(), interval.getTo()));
        } else {
            Stream.iterate(fromDate, date -> date.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(fromDate, toDate) + 1)
                    .forEach(date -> {
                        final Instant startOfDay = date.atStartOfDay(timeZone).toInstant();
                        final Instant from = fromDate.equals(date) ? interval.getFrom() : startOfDay;
                        final Instant to = toDate.equals(date) ? interval.getTo() : startOfDay.plus(1, ChronoUnit.DAYS).minusNanos(1);
                        resultIntervals.add(Interval.of(from, to));
                    });
        }
        return resultIntervals;
    }

}
