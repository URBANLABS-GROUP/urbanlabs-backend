package ru.urbanlabs.model.iot.equipment.impl.move;

import ru.urbanlabs.model.iot.equipment.IotEquipment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Entity(name = "move_socket_equipment")
public class MoveSensor extends IotEquipment {

    @Enumerated(EnumType.STRING)
    private MoveSensorModel model;

    public MoveSensorModel getModel() {
        return model;
    }

    public void setModel(MoveSensorModel model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveSensor that = (MoveSensor) o;
        return model == that.model;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }

    @Override
    public String toString() {
        return "MoveSensor{" +
            "model=" + model +
            ", id=" + id +
            ", roomId=" + roomId +
            '}';
    }
}
