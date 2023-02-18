package org.example.dto;

import org.example.dto.alert.Alert;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AnalyticDto {

    private Date date;
    private Integer roomCount;
    private Integer rentCount;
    private Integer expectedIncome;
    private Integer realIncome;
    private Integer requestExpenses;
    private Integer checkExpenses;
    private Integer powerExpenses;
    private List<Alert> alerts;

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

    public Integer getCheckExpenses() {
        return checkExpenses;
    }

    public void setCheckExpenses(Integer checkExpenses) {
        this.checkExpenses = checkExpenses;
    }

    public Integer getPowerExpenses() {
        return powerExpenses;
    }

    public void setPowerExpenses(Integer powerExpenses) {
        this.powerExpenses = powerExpenses;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalyticDto that = (AnalyticDto) o;
        return Objects.equals(date, that.date) && Objects.equals(roomCount, that.roomCount) && Objects.equals(rentCount, that.rentCount) && Objects.equals(expectedIncome, that.expectedIncome) && Objects.equals(realIncome, that.realIncome) && Objects.equals(requestExpenses, that.requestExpenses) && Objects.equals(checkExpenses, that.checkExpenses) && Objects.equals(powerExpenses, that.powerExpenses) && Objects.equals(alerts, that.alerts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, roomCount, rentCount, expectedIncome, realIncome, requestExpenses, checkExpenses, powerExpenses, alerts);
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
            ", checkExpenses=" + checkExpenses +
            ", powerConsumption=" + powerExpenses +
            ", alerts=" + alerts +
            '}';
    }
}
