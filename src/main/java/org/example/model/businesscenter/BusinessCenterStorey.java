package org.example.model.businesscenter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity(name = "business_center_storey")
public class BusinessCenterStorey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int businessCenterId;
    private byte[] map;
    private String name;

    @OneToMany(mappedBy = "businessCenterStoreyId")
    private List<Room> rooms;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBusinessCenterId() {
        return businessCenterId;
    }

    public void setBusinessCenterId(int businessCenterId) {
        this.businessCenterId = businessCenterId;
    }

    public byte[] getMap() {
        return map;
    }

    public void setMap(byte[] map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> roomIds) {
        this.rooms = roomIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessCenterStorey that = (BusinessCenterStorey) o;
        return id == that.id && businessCenterId == that.businessCenterId && Arrays.equals(map, that.map) && Objects.equals(name, that.name) && Objects.equals(rooms, that.rooms);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, businessCenterId, name, rooms);
        result = 31 * result + Arrays.hashCode(map);
        return result;
    }

    @Override
    public String toString() {
        return "BusinessCenterStorey{" +
                "id=" + id +
                ", businessCenterId=" + businessCenterId +
                ", map=" + Arrays.toString(map) +
                ", name='" + name + '\'' +
                ", roomIds=" + rooms +
                '}';
    }
}
