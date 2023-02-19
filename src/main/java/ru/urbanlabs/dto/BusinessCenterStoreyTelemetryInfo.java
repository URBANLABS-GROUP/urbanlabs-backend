package ru.urbanlabs.dto;

import ru.urbanlabs.dao.DaoFactory;
import ru.urbanlabs.model.businesscenter.Room;

import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class BusinessCenterStoreyTelemetryInfo {

    private Integer curTemp;
    private Integer averageCurTemp;

    private Integer curDayPowerConsumption;
    private Integer averagePowerConsumption;

    private Integer curDayWaterConsumption;
    private Integer averageWaterConsumption;

    private Integer rent;
    private Integer expenses;

    private Double area;

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

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public static BusinessCenterStoreyTelemetryInfo of(final DaoFactory daoFactory,
                                                       final List<RoomTelemetryInfo> roomInfos) {
        final BusinessCenterStoreyTelemetryInfo businessCenterStoreyTelemetryInfo = new BusinessCenterStoreyTelemetryInfo();

        OptionalDouble averageCurTemp = roomInfos.stream()
            .map(RoomTelemetryInfo::getCurTemp)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .average();
        if (averageCurTemp.isPresent()) {
            businessCenterStoreyTelemetryInfo.setCurTemp((int) averageCurTemp.getAsDouble());
        }

        OptionalDouble averageTemp = roomInfos.stream()
            .map(RoomTelemetryInfo::getAverageCurTemp)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .average();
        if (averageTemp.isPresent()) {
            businessCenterStoreyTelemetryInfo.setAverageCurTemp((int) averageTemp.getAsDouble());
        }

        businessCenterStoreyTelemetryInfo.setCurDayPowerConsumption(roomInfos.stream()
            .map(RoomTelemetryInfo::getCurDayPowerConsumption)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());
        businessCenterStoreyTelemetryInfo.setAveragePowerConsumption(roomInfos.stream()
            .map(RoomTelemetryInfo::getAveragePowerConsumption)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());

        businessCenterStoreyTelemetryInfo.setCurDayWaterConsumption(roomInfos.stream()
            .map(RoomTelemetryInfo::getCurDayWaterConsumption)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());
        businessCenterStoreyTelemetryInfo.setAverageWaterConsumption(roomInfos.stream()
            .map(RoomTelemetryInfo::getAverageWaterConsumption)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());

        businessCenterStoreyTelemetryInfo.setExpenses(roomInfos.stream()
            .map(RoomTelemetryInfo::getExpenses)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());
        businessCenterStoreyTelemetryInfo.setRent(roomInfos.stream()
            .map(RoomTelemetryInfo::getRent)
            .filter(Objects::nonNull)
            .mapToInt(Integer::intValue)
            .sum());

        List<Room> rooms = daoFactory.getRoomRepository().findAllById(roomInfos.stream()
            .map(RoomTelemetryInfo::getRoomId)
            .collect(Collectors.toList()));
        businessCenterStoreyTelemetryInfo.setArea(rooms.stream()
            .map(Room::getArea)
            .filter(Objects::nonNull)
            .mapToDouble(Double::doubleValue)
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
