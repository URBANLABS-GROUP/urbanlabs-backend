package org.example.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeUtilsTest {

    @Test
    void splitByMonth() {
        Instant from = Instant.parse("2023-02-01T00:00:00Z");
        Instant to = Instant.parse("2023-08-01T00:00:00Z");

        List<Interval> intervals = DateTimeUtils.splitByMonth(
            LocalDateTime.ofInstant(from, ZoneId.of("UTC")),
            LocalDateTime.ofInstant(to, ZoneId.of("UTC"))
        );

        assertEquals(Instant.parse("2023-02-01T00:00:00Z"), Instant.parse("2023-02-28T00:00:00Z"));

    }
}
