package ru.urbanlabs.model.iot.equipment.impl.powersocket;

import ru.urbanlabs.model.iot.equipment.IotEquipment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Entity(name = "power_socket_equipment")
public class PowerSocket extends IotEquipment {

    @Enumerated(EnumType.STRING)
    private PowerSocketModel model;

    public PowerSocketModel getModel() {
        return model;
    }

    public void setModel(PowerSocketModel model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowerSocket that = (PowerSocket) o;
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
