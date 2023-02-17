package org.example.dto;

import java.util.Objects;

public class AnalyticDto {

    private Integer roomCount;
    private Integer rentCount;

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getRentCount() {
        return rentCount;
    }

    public void setRentCount(Integer rentCount) {
        this.rentCount = rentCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalyticDto that = (AnalyticDto) o;
        return Objects.equals(roomCount, that.roomCount) && Objects.equals(rentCount, that.rentCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomCount, rentCount);
    }

    @Override
    public String toString() {
        return "AnalyticDto{" +
                "roomCount=" + roomCount +
                ", rentCount=" + rentCount +
                '}';
    }
}
