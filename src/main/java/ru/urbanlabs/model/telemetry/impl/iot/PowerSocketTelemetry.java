package ru.urbanlabs.model.telemetry.impl.iot;

import ru.urbanlabs.model.iot.equipment.EquipmentType;
import ru.urbanlabs.model.telemetry.BaseTelemetry;

import javax.persistence.Entity;
import java.util.Objects;

@Entity(name = "power_socket_telemetry")
public class PowerSocketTelemetry extends BaseTelemetry {

    public PowerSocketTelemetry() {
        equipmentType = EquipmentType.POWER_SOCKET;
    }

    private int watt; // милливольты

    public int getWatt() {
        return watt;
    }

    public void setWatt(int watt) {
        this.watt = watt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowerSocketTelemetry that = (PowerSocketTelemetry) o;
        return watt == that.watt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(watt);
    }

    @Override
    public String toString() {
        return "PowerSocketTelemetry{" +
            "watt=" + watt +
            ", id=" + id +
            ", fixTime=" + fixTime +
            ", createTime=" + createTime +
            ", equipmentId=" + equipmentId +
            ", equipmentType=" + equipmentType +
            '}';
    }
}
