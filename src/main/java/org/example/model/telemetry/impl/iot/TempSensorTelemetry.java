package org.example.model.telemetry.impl.iot;

import org.example.model.telemetry.BaseTelemetry;

import javax.persistence.Entity;
import java.util.Objects;

@Entity(name = "temp_socket_telemetry")
public class TempSensorTelemetry extends BaseTelemetry {

    private int temp;

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempSensorTelemetry that = (TempSensorTelemetry) o;
        return temp == that.temp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), temp);
    }

    @Override
    public String toString() {
        return "TempSensorTelemetry{" +
            "temp=" + temp +
            ", id=" + id +
            ", fixTime=" + fixTime +
            ", createTime=" + createTime +
            ", equipmentId=" + equipmentId +
            ", equipmentType=" + equipmentType +
            '}';
    }
}
