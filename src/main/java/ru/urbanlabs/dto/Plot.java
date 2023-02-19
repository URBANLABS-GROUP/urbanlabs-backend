package ru.urbanlabs.dto;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class Plot<T> {

    private String name;
    private List<Point<T>> points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Point<T>> getPoints() {
        return points;
    }

    public void setPoints(List<Point<T>> points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plot<?> plot = (Plot<?>) o;
        return Objects.equals(name, plot.name) && Objects.equals(points, plot.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, points);
    }

    @Override
    public String toString() {
        return "Plot{" +
            "name='" + name + '\'' +
            ", points=" + points +
            '}';
    }

    public static class Point<T> {
        private Instant x;
        private T y;

        public Instant getX() {
            return x;
        }

        public void setX(Instant x) {
            this.x = x;
        }

        public T getY() {
            return y;
        }

        public void setY(T y) {
            this.y = y;
        }

        public static Point<Integer> of(Instant x, Integer y) {
            Point<Integer> integerPoint = new Point<>();
            integerPoint.setX(x);
            integerPoint.setY(y);

            return integerPoint;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point<?> point = (Point<?>) o;
            return Objects.equals(x, point.x) && Objects.equals(y, point.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
        }
    }
}
