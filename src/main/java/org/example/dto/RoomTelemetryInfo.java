package org.example.dto;

import java.util.Objects;

public class RoomTelemetryInfo {

    private Integer curTemp;
    private Integer averageCurTemp;

    private Integer curDayPowerConsumption;
    private Integer averagePowerConsumption;

    private Integer curDayWaterConsumption;
    private Integer averageWaterConsumption;

    private Boolean isMove;
    //    private boolean isLightTurnOn;
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

    public RoomTelemetryInfo accumulate(RoomTelemetryInfo roomTelemetryInfo) {
        return roomTelemetryInfo;
    }

    public Boolean getMove() {
        return isMove;
    }

    public void setMove(Boolean move) {
        isMove = move;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomTelemetryInfo that = (RoomTelemetryInfo) o;
        return Objects.equals(curTemp, that.curTemp) && Objects.equals(averageCurTemp, that.averageCurTemp) && Objects.equals(curDayPowerConsumption, that.curDayPowerConsumption) && Objects.equals(averagePowerConsumption, that.averagePowerConsumption) && Objects.equals(curDayWaterConsumption, that.curDayWaterConsumption) && Objects.equals(averageWaterConsumption, that.averageWaterConsumption) && Objects.equals(isMove, that.isMove) && Objects.equals(rent, that.rent) && Objects.equals(expenses, that.expenses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curTemp, averageCurTemp, curDayPowerConsumption, averagePowerConsumption, curDayWaterConsumption, averageWaterConsumption, isMove, rent, expenses);
    }

    @Override
    public String toString() {
        return "RoomTelemetryInfo{" +
            "curTemp=" + curTemp +
            ", averageCurTemp=" + averageCurTemp +
            ", curDayPowerConsumption=" + curDayPowerConsumption +
            ", averagePowerConsumption=" + averagePowerConsumption +
            ", curDayWaterConsumption=" + curDayWaterConsumption +
            ", averageWaterConsumption=" + averageWaterConsumption +
            ", isMove=" + isMove +
            ", rent=" + rent +
            ", expenses=" + expenses +
            '}';
    }
}
