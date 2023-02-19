package ru.urbanlabs.dto.alert.impl;

import ru.urbanlabs.dto.alert.Alert;
import ru.urbanlabs.dto.alert.AlertType;

import java.util.Objects;

public class TooMuchPowerConsumptionAlert extends Alert {

    private int roomId;
    private int currentPowerConsumption;
    private int requiredPowerConsumption;

    public TooMuchPowerConsumptionAlert() {
        this.alertType = AlertType.TOO_MUCH_POWER_CONSUMING;
    }

    public TooMuchPowerConsumptionAlert(int roomId, int currentPowerConsumption, int allowablePowerConsumption) {
        this.roomId = roomId;
        this.currentPowerConsumption = currentPowerConsumption;
        this.requiredPowerConsumption = allowablePowerConsumption;
        this.alertType = AlertType.TOO_MUCH_POWER_CONSUMING;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCurrentPowerConsumption() {
        return currentPowerConsumption;
    }

    public void setCurrentPowerConsumption(int currentPowerConsumption) {
        this.currentPowerConsumption = currentPowerConsumption;
    }

    public int getRequiredPowerConsumption() {
        return requiredPowerConsumption;
    }

    public void setRequiredPowerConsumption(int requiredPowerConsumption) {
        this.requiredPowerConsumption = requiredPowerConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TooMuchPowerConsumptionAlert that = (TooMuchPowerConsumptionAlert) o;
        return roomId == that.roomId && currentPowerConsumption == that.currentPowerConsumption && requiredPowerConsumption == that.requiredPowerConsumption;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roomId, currentPowerConsumption, requiredPowerConsumption);
    }

    @Override
    public String toString() {
        return "TooMuchPowerConsumptionAlert{" +
            "roomId=" + roomId +
            ", currentPowerConsumption=" + currentPowerConsumption +
            ", requiredPowerConsumption=" + requiredPowerConsumption +
            ", alertType=" + alertType +
            '}';
    }
}
