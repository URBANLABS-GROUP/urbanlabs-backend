package ru.urbanlabs.model.iot.equipment.impl.water;

import ru.urbanlabs.model.iot.equipment.IotEquipment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Entity(name = "water_socket_equipment")
public class WaterSensor extends IotEquipment {

    @Enumerated(EnumType.STRING)
    private WaterSensorModel model;
    @Enumerated(EnumType.STRING)
    private WaterTemp waterTemp;

    public WaterSensorModel getModel() {
        return model;
    }

    public void setModel(WaterSensorModel model) {
        this.model = model;
    }

    public WaterTemp getWaterTemp() {
        return waterTemp;
    }

    public void setWaterTemp(WaterTemp waterTemp) {
        this.waterTemp = waterTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterSensor that = (WaterSensor) o;
        return model == that.model && waterTemp == that.waterTemp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, waterTemp);
    }

    @Override
    public String toString() {
        return "WaterSensor{" +
            "model=" + model +
            ", waterTemp=" + waterTemp +
            ", id=" + id +
            ", roomId=" + roomId +
            '}';
    }
}
