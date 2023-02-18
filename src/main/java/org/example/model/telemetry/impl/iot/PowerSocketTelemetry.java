package org.example.model.telemetry.impl.iot;

import org.example.model.iot.equipment.EquipmentType;
import org.example.model.telemetry.BaseTelemetry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name = "power_socket_telemetry")
public class PowerSocketTelemetry extends BaseTelemetry {

    public PowerSocketTelemetry() {
        equipmentType = EquipmentType.POWER_SOCKET;
    }

    private int vatt; // милливольты

    public int getVatt() {
        return vatt;
    }

    public void setVatt(int vatt) {
        this.vatt = vatt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowerSocketTelemetry that = (PowerSocketTelemetry) o;
        return vatt == that.vatt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vatt);
    }

    @Override
    public String toString() {
        return "PowerSocketTelemetry{" +
            "vatt=" + vatt +
            ", id=" + id +
            ", fixTime=" + fixTime +
            ", createTime=" + createTime +
            ", equipmentId=" + equipmentId +
            ", equipmentType=" + equipmentType +
            '}';
    }
}
