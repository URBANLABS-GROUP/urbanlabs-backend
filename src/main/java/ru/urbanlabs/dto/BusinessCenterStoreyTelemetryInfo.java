package ru.urbanlabs.dto;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

public class BusinessCenterStoreyTelemetryInfo {

    private Integer curTemp;
    private Integer averageCurTemp;

    private Integer curDayPowerConsumption;
    private Integer averagePowerConsumption;

    private Integer curDayWaterConsumption;
    private Integer averageWaterConsumption;

    private Integer rent;
    private Integer expenses;

    public Integer getCurTemp() {
        return curTemp;
    }

    public void setCurTemp(Integer curTemp) {
        this.curTemp = curTemp;
    }

    public Integer getAverageCurTemp() {
        return averageCurTemp;
    }

    public void setAverageCurTemp(Integer averageCurTemp) {
        this.averageCurTemp = averageCurTemp;
    }

    public Integer getCurDayPowerConsumption() {
        return curDayPowerConsumption;
    }

    public void setCurDayPowerConsumption(Integer curDayPowerConsumption) {
        this.curDayPowerConsumption = curDayPowerConsumption;
    }

    public Integer getAveragePowerConsumption() {
        return averagePowerConsumption;
    }

    public void setAveragePowerConsumption(Integer averagePowerConsumption) {
        this.averagePowerConsumption = averagePowerConsumption;
    }

    public Integer getCurDayWaterConsumption() {
        return curDayWaterConsumption;
    }

    public void setCurDayWaterConsumption(Integer curDayWaterConsumption) {
        this.curDayWaterConsumption = curDayWaterConsumption;
    }

    public Integer getAverageWaterConsumption() {
        return averageWaterConsumption;
    }

    public void setAverageWaterConsumption(Integer averageWaterConsumption) {
        this.averageWaterConsumption = averageWaterConsumption;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Integer getExpenses() {
        return expenses;
    }

    public void setExpenses(Integer expenses) {
        this.expenses = expenses;
    }

    public static BusinessCenterStoreyTelemetryInfo of(List<RoomTelemetryInfo> roomTelemetryInfoList) {
        final BusinessCenterStoreyTelemetryInfo businessCenterStoreyTelemetryInfo = new BusinessCenterStoreyTelemetryInfo();

        OptionalDouble averageCurTemp = roomTelemetryInfoList.stream()
            .map(RoomTelemetryInfo::getCurTemp)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .average();
        if (averageCurTemp.isPresent()) {
            businessCenterStoreyTelemetryInfo.setCurTemp((int) averageCurTemp.getAsDouble());
        }

        OptionalDouble averageTemp = roomTelemetryInfoList.stream()
            .map(RoomTelemetryInfo::getAverageCurTemp)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .average();
        if (averageTemp.isPresent()) {
            businessCenterStoreyTelemetryInfo.setAverageCurTemp((int) averageTemp.getAsDouble());
        }

        businessCenterStoreyTelemetryInfo.setCurDayPowerConsumption(roomTelemetryInfoList.stream()
            .map(RoomTelemetryInfo::getCurDayPowerConsumption)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());
        businessCenterStoreyTelemetryInfo.setAveragePowerConsumption(roomTelemetryInfoList.stream()
            .map(RoomTelemetryInfo::getAveragePowerConsumption)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());

        businessCenterStoreyTelemetryInfo.setCurDayWaterConsumption(roomTelemetryInfoList.stream()
            .map(RoomTelemetryInfo::getCurDayWaterConsumption)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());
        businessCenterStoreyTelemetryInfo.setAverageWaterConsumption(roomTelemetryInfoList.stream()
            .map(RoomTelemetryInfo::getAverageWaterConsumption)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());

        businessCenterStoreyTelemetryInfo.setExpenses(roomTelemetryInfoList.stream()
            .map(RoomTelemetryInfo::getExpenses)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());
        businessCenterStoreyTelemetryInfo.setRent(roomTelemetryInfoList.stream()
            .map(RoomTelemetryInfo::getRent)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());

        return businessCenterStoreyTelemetryInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessCenterStoreyTelemetryInfo that = (BusinessCenterStoreyTelemetryInfo) o;
        return Objects.equals(curTemp, that.curTemp) && Objects.equals(averageCurTemp, that.averageCurTemp) && Objects.equals(curDayPowerConsumption, that.curDayPowerConsumption) && Objects.equals(averagePowerConsumption, that.averagePowerConsumption) && Objects.equals(curDayWaterConsumption, that.curDayWaterConsumption) && Objects.equals(averageWaterConsumption, that.averageWaterConsumption) && Objects.equals(rent, that.rent) && Objects.equals(expenses, that.expenses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curTemp, averageCurTemp, curDayPowerConsumption, averagePowerConsumption, curDayWaterConsumption, averageWaterConsumption, rent, expenses);
    }

    @Override
    public String toString() {
        return "BusinessCenterStoreyTelemetryInfo{" +
            "curTemp=" + curTemp +
            ", averageCurTemp=" + averageCurTemp +
            ", curDayPowerConsumption=" + curDayPowerConsumption +
            ", averagePowerConsumption=" + averagePowerConsumption +
            ", curDayWaterConsumption=" + curDayWaterConsumption +
            ", averageWaterConsumption=" + averageWaterConsumption +
            ", rent=" + rent +
            ", expenses=" + expenses +
            '}';
    }
}
