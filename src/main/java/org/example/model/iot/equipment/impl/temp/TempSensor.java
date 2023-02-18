package org.example.model.iot.equipment.impl.temp;

import org.example.model.iot.equipment.IotEquipment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Entity(name = "temp_socket_equipment")
public class TempSensor extends IotEquipment {

    @Enumerated(EnumType.STRING)
    private TempSensorModel model;

    public TempSensorModel getModel() {
        return model;
    }

    public void setModel(TempSensorModel model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempSensor that = (TempSensor) o;
        return model == that.model;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }

    @Override
    public String toString() {
        return "PowerSocket{" +
            "model=" + model +
            ", id=" + id +
            ", roomId=" + roomId +
            '}';
    }
}
