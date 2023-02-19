package ru.urbanlabs.dto.alert.impl;

import ru.urbanlabs.dto.alert.Alert;
import ru.urbanlabs.dto.alert.AlertType;

import java.util.Objects;

public class TooHotTempAlert extends Alert {

    private int roomId;
    private int currentTemp;
    private int requiredTemp;

    public TooHotTempAlert() {
        this.alertType = AlertType.TOO_HOT_TEMP;
    }

    public TooHotTempAlert(int roomId, int currentTemp, int requiredTemp) {
        this.roomId = roomId;
        this.currentTemp = currentTemp;
        this.requiredTemp = requiredTemp;
        this.alertType = AlertType.TOO_HOT_TEMP;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public int getRequiredTemp() {
        return requiredTemp;
    }

    public void setRequiredTemp(int requiredTemp) {
        this.requiredTemp = requiredTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TooHotTempAlert that = (TooHotTempAlert) o;
        return roomId == that.roomId && currentTemp == that.currentTemp && requiredTemp == that.requiredTemp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roomId, currentTemp, requiredTemp);
    }

    @Override
    public String toString() {
        return "TooHotTemp{" +
            "roomId=" + roomId +
            ", currentTemp=" + currentTemp +
            ", requiredTemp=" + requiredTemp +
            ", alertType=" + alertType +
            '}';
    }
}
