package ru.urbanlabs.model.telemetry.impl.iot;

import ru.urbanlabs.model.telemetry.BaseTelemetry;

import javax.persistence.Entity;
import java.util.Objects;

@Entity(name = "smoke_socket_telemetry")
public class SmokeSensorTelemetry extends BaseTelemetry {

    private boolean isFire;

    public boolean isFire() {
        return isFire;
    }

    public void setFire(boolean fire) {
        isFire = fire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmokeSensorTelemetry that = (SmokeSensorTelemetry) o;
        return isFire == that.isFire;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFire);
    }

    @Override
    public String toString() {
        return "SmokeSensorTelemetry{" +
            "isFire=" + isFire +
            ", id=" + id +
            ", fixTime=" + fixTime +
            ", createTime=" + createTime +
            ", equipmentId=" + equipmentId +
            ", equipmentType=" + equipmentType +
            '}';
    }
}
