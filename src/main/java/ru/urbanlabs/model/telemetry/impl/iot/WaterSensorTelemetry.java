package ru.urbanlabs.model.telemetry.impl.iot;

import ru.urbanlabs.model.telemetry.BaseTelemetry;

import javax.persistence.Entity;
import java.util.Objects;

@Entity(name = "water_socket_telemetry")
public class WaterSensorTelemetry extends BaseTelemetry {

    private int waterVolume;

    public int getWaterVolume() {
        return waterVolume;
    }

    public void setWaterVolume(int waterVolume) {
        this.waterVolume = waterVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterSensorTelemetry that = (WaterSensorTelemetry) o;
        return waterVolume == that.waterVolume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(waterVolume);
    }

    @Override
    public String toString() {
        return "WaterSensorTelemetry{" +
            "waterVolume=" + waterVolume +
            ", id=" + id +
            ", fixTime=" + fixTime +
            ", createTime=" + createTime +
            ", equipmentId=" + equipmentId +
            ", equipmentType=" + equipmentType +
            '}';
    }
}
