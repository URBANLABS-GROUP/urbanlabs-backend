package org.example.model.iot.equipment;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "equipment")
public abstract class IotEquipment {

    @Id
    private int id;
    protected int roomId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
