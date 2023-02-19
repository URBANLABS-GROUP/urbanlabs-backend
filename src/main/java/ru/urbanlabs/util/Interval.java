package ru.urbanlabs.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Interval {

    private Instant from;
    private Instant to;

    private Interval() {
    }

    public Interval(final Instant from, final Instant to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Instant cannot be null");
        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From instant " + from + " must be before To instant " + to);
        }

        this.from = from;
        this.to = to;
    }

    public Instant getFrom() {
        return from;
    }

    public void setFrom(final Instant from) {
        this.from = from;
    }

    public Instant getTo() {
        return to;
    }

    public void setTo(final Instant to) {
        this.to = to;
    }

    public boolean isInclude(final Instant time) {
        if (this.getFrom().isAfter(time)) {
            return false;
        }
        return !this.getTo().isBefore(time);
    }

    public static Interval of(final Instant from, final Instant to) {
        return new Interval(from, to);
    }

    public long calcDuration() {
        return from.until(to, ChronoUnit.SECONDS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Objects.equals(from, interval.from) && Objects.equals(to, interval.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Interval{" +
            "from=" + from +
            ", to=" + to +
            '}';
    }
}
