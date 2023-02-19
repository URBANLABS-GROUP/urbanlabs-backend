package ru.urbanlabs.model.iot.equipment.impl.smoke;

import ru.urbanlabs.model.iot.equipment.IotEquipment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Entity(name = "smoke_sensor_equipment")
public class SmokeSensor extends IotEquipment {

    @Enumerated(EnumType.STRING)
    private SmokeSensorType model;

    public SmokeSensorType getModel() {
        return model;
    }

    public void setModel(SmokeSensorType model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmokeSensor that = (SmokeSensor) o;
        return model == that.model;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }

    @Override
    public String toString() {
        return "SmokeSensor{" +
            "model=" + model +
            ", id=" + id +
            ", roomId=" + roomId +
            '}';
    }
}
