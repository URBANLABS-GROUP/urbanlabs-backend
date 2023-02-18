package org.example.dto;

import java.util.Date;
import java.util.Objects;

public class AnalyticDto {

    private Date date;
    private Integer roomCount;
    private Integer rentCount;
    private Integer expectedIncome;
    private Integer realIncome;
    private Integer requestExpenses;
    private Integer powerConsumption;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

    public Integer getExpectedIncome() {
        return expectedIncome;
    }

    public void setExpectedIncome(Integer expectedIncome) {
        this.expectedIncome = expectedIncome;
    }

    public Integer getRealIncome() {
        return realIncome;
    }

    public void setRealIncome(Integer realIncome) {
        this.realIncome = realIncome;
    }

    public Integer getRequestExpenses() {
        return requestExpenses;
    }

    public void setRequestExpenses(Integer requestExpenses) {
        this.requestExpenses = requestExpenses;
    }

    public Integer getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(Integer powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalyticDto that = (AnalyticDto) o;
        return Objects.equals(date, that.date) && Objects.equals(roomCount, that.roomCount) && Objects.equals(rentCount, that.rentCount) && Objects.equals(expectedIncome, that.expectedIncome) && Objects.equals(realIncome, that.realIncome) && Objects.equals(requestExpenses, that.requestExpenses) && Objects.equals(powerConsumption, that.powerConsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, roomCount, rentCount, expectedIncome, realIncome, requestExpenses, powerConsumption);
    }

    @Override
    public String toString() {
        return "AnalyticDto{" +
            "date=" + date +
            ", roomCount=" + roomCount +
            ", rentCount=" + rentCount +
            ", expectedIncome=" + expectedIncome +
            ", realIncome=" + realIncome +
            ", requestExpenses=" + requestExpenses +
            ", powerConsumption=" + powerConsumption +
            '}';
    }
}
